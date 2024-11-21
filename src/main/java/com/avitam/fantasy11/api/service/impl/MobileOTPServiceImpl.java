package com.avitam.fantasy11.api.service.impl;

import com.avitam.fantasy11.api.dto.UserDto;
import com.avitam.fantasy11.api.service.MobileOTPService;
import com.avitam.fantasy11.model.User;
import com.avitam.fantasy11.repository.UserRepository;
import com.avitam.fantasy11.tokenGeneration.JWTUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
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

    private final RestTemplate restTemplate = new RestTemplate();
    private final ConcurrentHashMap<String, String> otpMap = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, LocalDateTime> otpExpirationMap = new ConcurrentHashMap<>();

    @Override
    public UserDto sendOtp(UserDto userDto) {
        String mobileNumber = userDto.getMobileNumber();

        if (mobileNumber == null || mobileNumber.isEmpty()) {
            userDto.setSuccess(false);
            userDto.setMessage("Mobile number is required.");
            return userDto;
        }

        String otp = generateOtp();
        otpMap.put(mobileNumber, otp);
        otpExpirationMap.put(mobileNumber, LocalDateTime.now().plusMinutes(otpExpirationMinutes));

        // Call MSG91 API to send OTP
        String url = "https://api.msg91.com/api/v5/otp?authkey=" + apiKey
                + "&mobile=" + mobileNumber
                + "&sender=" + senderId
                + "&otp=" + otp;

        try {
            restTemplate.getForObject(url, String.class);
            userDto.setSuccess(true);
            userDto.setMessage("OTP sent successfully.");
        } catch (Exception e) {
            userDto.setSuccess(false);
            userDto.setMessage("Failed to send OTP: " + e.getMessage());
        }

        return userDto;
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
    public UserDto validateOtp(UserDto userDto) {
        String mobileNumber = userDto.getMobileNumber();
        String otp = userDto.getOtp();

        if (mobileNumber == null || otp == null || mobileNumber.isEmpty() || otp.isEmpty()) {
            userDto.setSuccess(false);
            userDto.setMessage("Mobile number and OTP are required.");
            return userDto;
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
            userDto.setSuccess(true);
            userDto.setMessage("OTP validated successfully.");
        } else {
            userDto.setSuccess(false);
            userDto.setMessage("OTP is invalid or has expired.");
        }

        return userDto;
    }

    @Override
    public UserDto saveUsername(UserDto userDto){
        String mobileNumber = userDto.getMobileNumber();
        String username = userDto.getUserName();

        if (mobileNumber == null || mobileNumber.isEmpty() || username == null || username.isEmpty()) {
            userDto.setSuccess(false);
            userDto.setMessage("Mobile number and Username are required.");
            return userDto;
        }

        User existingUser = userRepository.findByMobileNumber(mobileNumber);
        if (existingUser == null) {
            userDto.setSuccess(false);
            userDto.setMessage("User not found. Please validate OTP first.");
            return userDto;
        }

        existingUser.setUsername(username);
        userRepository.save(existingUser);

        userDto.setUser(existingUser);
        userDto.setSuccess(true);
        userDto.setMessage("Username saved successfully.");
        return userDto;
    }

}
