package com.avitam.fantasy11.api.service.impl;

import com.avitam.fantasy11.api.dto.UserDto;
import com.avitam.fantasy11.api.service.MobileOTPService;
import com.avitam.fantasy11.model.User;
import com.avitam.fantasy11.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    private final RestTemplate restTemplate = new RestTemplate();
    private final ConcurrentHashMap<String, LocalDateTime> otpExpirationMap = new ConcurrentHashMap<>();

    @Override
    public UserDto sendOtp(UserDto userDto) {
        User user = userDto.getUser();
        String mobileNumber = user.getMobileNumber();

        // Fetch user by mobile number
        User existingUser = userRepository.findByMobileNumber(mobileNumber);

        if (existingUser == null) {
            userDto.setMessage("Mobile number not registered.");
            userDto.setSuccess(false);
            return userDto;
        }

        String otp = generateOtp();

        // Save OTP in the database and map
        existingUser.setMobileOTP(otp);
        userRepository.save(existingUser);
        otpExpirationMap.put(mobileNumber, LocalDateTime.now().plusMinutes(otpExpirationMinutes));

        // Call MSG91 API to send OTP
        String url = "https://api.msg91.com/api/v5/otp?authkey=" + apiKey
                + "&mobile=" + mobileNumber
                + "&sender=" + senderId
                + "&otp=" + otp;

        try {
            restTemplate.getForObject(url, String.class);
            userDto.setMessage("OTP sent successfully.");
        } catch (Exception e) {
            userDto.setSuccess(false);
            userDto.setMessage("Failed to send OTP: " + e.getMessage());
        }

        userDto.setUser(existingUser);
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
        User user = userDto.getUser();
        String mobileNumber = user.getMobileNumber();
        String otp = user.getMobileOTP();

        // Fetch user by mobile number
        User existingUser = userRepository.findByMobileNumber(mobileNumber);

        if (existingUser == null || existingUser.getMobileOTP() == null) {
            userDto.setMessage("Invalid mobile number or OTP not found.");
            userDto.setSuccess(false);
            return userDto;
        }

        boolean isOtpValid = existingUser.getMobileOTP().equals(otp) &&
                otpExpirationMap.containsKey(mobileNumber) &&
                LocalDateTime.now().isBefore(otpExpirationMap.get(mobileNumber));

        if (isOtpValid) {
            // Clear OTP after successful validation
            existingUser.setMobileOTP(null);
            userRepository.save(existingUser);
            otpExpirationMap.remove(mobileNumber);

            userDto.setMessage("OTP validated successfully.");
        } else {
            userDto.setSuccess(false);
            userDto.setMessage("OTP is invalid or has expired.");
        }

        userDto.setUser(existingUser);
        return userDto;
    }
}
