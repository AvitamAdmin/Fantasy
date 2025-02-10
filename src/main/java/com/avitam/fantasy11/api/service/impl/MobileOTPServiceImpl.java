package com.avitam.fantasy11.api.service.impl;

import com.avitam.fantasy11.api.dto.UserDto;
import com.avitam.fantasy11.api.dto.UserWsDto;
import com.avitam.fantasy11.api.service.MobileOTPService;
import com.avitam.fantasy11.model.OTP;
import com.avitam.fantasy11.model.User;
import com.avitam.fantasy11.repository.OtpRepository;
import com.avitam.fantasy11.repository.UserRepository;
import com.avitam.fantasy11.tokenGeneration.JWTUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class MobileOTPServiceImpl implements MobileOTPService {

    @Value("${msg91.api.key}")
    private String apiKey;

    @Value("${msg91.sender.id}")
    private String senderId;

    @Value("${msg91.otp.expiration.minutes}")
    private int otpExpirationMinutes;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JWTUtility jwtUtility;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private OtpRepository otpRepository;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ConcurrentHashMap<String, String> otpMap = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, LocalDateTime> otpExpirationMap = new ConcurrentHashMap<>();
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int LENGTH = 6;

    @Override
    public UserWsDto sendOtp(UserWsDto userWsDto) {
        for (UserDto userDto: userWsDto.getUserDtoList()) {
            String mobileNumber = userDto.getMobileNumber();
            if (mobileNumber == null || mobileNumber.isEmpty()) {
                userWsDto.setSuccess(false);
                userWsDto.setMessage("Mobile number is required.");
                return userWsDto;
            }
            String otp = generateOtp();
            otpMap.put(mobileNumber, otp);
            userWsDto.setOtp(otp);
            otpExpirationMap.put(mobileNumber, LocalDateTime.now().plusMinutes(otpExpirationMinutes));

            OTP otp1 = new OTP();
            otp1.setUserId(mobileNumber);
            otp1.setMobileOtp(otp);
            otp1.setCreationTime(new Date());
            otpRepository.save(otp1);
            // Call MSG91 API to send OTP
            String url = "https://api.msg91.com/api/v5/otp?authkey=" + apiKey
                    + "&mobile=" + mobileNumber
                    + "&sender=" + senderId
                    + "&otp=" + otp;

            try {
                restTemplate.getForObject(url, String.class);
                userWsDto.setSuccess(true);
                userWsDto.setMessage("OTP sent successfully.");
            } catch (Exception e) {
                userWsDto.setSuccess(false);
                userWsDto.setMessage("Failed to send OTP: " + e.getMessage());
            }
        }

        return userWsDto;
    }

    private String generateOtp() {
        Random random = new Random();
        StringBuilder otp = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            otp.append(random.nextInt(10));
        }
        return otp.toString();
    }

    @Override
    public UserWsDto validateOtp(UserWsDto userWsDto) {
        for(UserDto userDto: userWsDto.getUserDtoList()) {
            String mobileNumber = userDto.getMobileNumber();
            String otp = userDto.getOtp();

            if (mobileNumber == null || otp == null || mobileNumber.isEmpty() || otp.isEmpty()) {
                userWsDto.setSuccess(false);
                userWsDto.setMessage("Mobile number and OTP are required.");
                return userWsDto;
            }

            boolean isOtpValid = otpMap.containsKey(mobileNumber) &&
                    otpMap.get(mobileNumber).equals(otp) &&
                    otpExpirationMap.containsKey(mobileNumber) &&
                    LocalDateTime.now().isBefore(otpExpirationMap.get(mobileNumber));

            if (isOtpValid) {
                // Clear OTP after successful validation
                otpMap.remove(mobileNumber);
                otpExpirationMap.remove(mobileNumber);
                User existingUser = userRepository.findByMobileNumber(mobileNumber);
                if (existingUser == null) {
                    // Save the email in the database if not present
                    User newUser = new User();
                    newUser.setMobileNumber(mobileNumber);
                    newUser.setStatus(true);
                    userRepository.save(newUser);
                }
                UserDetails userDetails = userDetailsService.loadUserByUsername(mobileNumber);
                userDto.setToken(jwtUtility.generateToken(userDetails));
                userWsDto.setSuccess(true);
                userWsDto.setMessage("OTP validated successfully.");
            } else {
                userWsDto.setSuccess(false);
                userWsDto.setMessage("OTP is invalid or has expired.");
            }
        }
        return userWsDto;
    }

    @Override
    public UserWsDto saveUsername(UserWsDto userWsDto){
        for (UserDto userDto : userWsDto.getUserDtoList()) {

            String mobileNumber = userDto.getMobileNumber();
            String username = userDto.getUsername();

            if (mobileNumber == null || mobileNumber.isEmpty() || username == null || username.isEmpty()) {
                userWsDto.setSuccess(false);
                userWsDto.setMessage("Mobile number and Username are required.");
                return userWsDto;
            }

            User existingUser = userRepository.findByMobileNumber(mobileNumber);
            if (existingUser == null) {
                userWsDto.setSuccess(false);
                userWsDto.setMessage("User not found. Please validate OTP first.");
                return userWsDto;
            }
            SecureRandom RANDOM = new SecureRandom();

            StringBuilder referralCode = new StringBuilder(LENGTH);

            for (int i = 0; i < LENGTH; i++) {
                int index = RANDOM.nextInt(CHARACTERS.length());
                referralCode.append(CHARACTERS.charAt(index)).toString();
            }

            existingUser.setUsername(username);
            existingUser.setReferralCode(String.valueOf(referralCode));
            existingUser.setCreationTime(new Date());
            userRepository.save(existingUser);
        }
        userWsDto.setSuccess(true);
        userWsDto.setMessage("Username saved successfully.");
        return userWsDto;
    }

}
