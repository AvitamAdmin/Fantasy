package com.avitam.fantasy11.api.service.impl;

import com.avitam.fantasy11.api.dto.RoleDto;
import com.avitam.fantasy11.api.dto.UserDto;
import com.avitam.fantasy11.api.dto.UserWsDto;
import com.avitam.fantasy11.api.service.EmailOTPService;
import com.avitam.fantasy11.api.service.OtpService;
import com.avitam.fantasy11.core.service.UserService;
import com.avitam.fantasy11.model.OTP;
import com.avitam.fantasy11.model.User;
import com.avitam.fantasy11.repository.OtpRepository;
import com.avitam.fantasy11.repository.UserRepository;
import com.avitam.fantasy11.tokenGeneration.JWTUtility;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class EmailOtpServiceImpl implements EmailOTPService {

    private static final int OTP_LENGTH = 6;
    private static final int OTP_EXPIRATION_MINUTES = 5;
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int LENGTH = 6;
    private final ConcurrentHashMap<String, String> otpMap = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, LocalDateTime> otpExpirationMap = new ConcurrentHashMap<>();
    @Value("${spring.mail.username}")
    private String username;
    @Value("${spring.mail.password}")
    private String password;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private JWTUtility jwtUtility;
    @Autowired
    private OtpService otpService;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private OtpRepository otpRepository;

    @Override
    public UserWsDto sendOtp(UserWsDto userWsDto) throws MessagingException {
        for (UserDto userDto : userWsDto.getUserDtoList()) {
            String email = userDto.getEmail();
            if (email == null || email.isEmpty()) {
                userWsDto.setSuccess(false);
                userWsDto.setMessage("Email is required.");
            }
            String otp = generateOtp();
            otpMap.put(email, otp);
            otpExpirationMap.put(email, LocalDateTime.now().plusMinutes(OTP_EXPIRATION_MINUTES));

            sendEmail(email, otp);
            userWsDto.setOtp(otp);
            OTP otp1 = new OTP();
            otp1.setUserId(email);
            otp1.setEmailOtp(otp);
            otp1.setCreationTime(new Date());
            otpRepository.save(otp1);
            userWsDto.setMessage("Otp sent successfully");
        }
        return userWsDto;
    }


    private void sendEmail(String recipientEmail, String otp) throws MessagingException {


        Properties properties = new Properties();
        properties.put("mail.transport.protocol", "smtp");  // Protocol (SMTP)
        properties.put("mail.smtp.auth", "true");           // Enable authentication
        properties.put("mail.smtp.starttls.enable", "true"); // Use TLS
        properties.put("mail.smtp.host", "smtp.gmail.com");  // SMTP Host
        properties.put("mail.smtp.port", "587");             // SMTP Port
        properties.put("mail.smtp.ssl.trust","*");
        // Create a MimeMessage
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);  // Use your email and app password
            }
        });

        // Create the message
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(username));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
        message.setSubject("Your OTP Code");
        message.setText("Your OTP code is: " + otp + "\nIt will expire in 5 minutes.");

        // Send the email
        Transport.send(message);
    }

    private String generateOtp() {
        Random random = new Random();
        StringBuilder otp = new StringBuilder();
        for (int i = 0; i < OTP_LENGTH; i++) {
            otp.append(random.nextInt(10));
        }
        return otp.toString();
    }

    @Override
    public UserWsDto validateOtp(UserWsDto userWsDto) {
        for (UserDto userDto : userWsDto.getUserDtoList()) {
            String email = userDto.getEmail();
            String otp = userDto.getEmailOTP();

            if (email == null || otp == null || email.isEmpty() || otp.isEmpty()) {
                userWsDto.setSuccess(false);
                userWsDto.setMessage("Email and OTP are required.");
                return userWsDto;
            }

            boolean isOtpValid = otpMap.containsKey(email) &&
                    otpMap.get(email).equals(otp) &&
                    otpExpirationMap.containsKey(email) &&
                    LocalDateTime.now().isBefore(otpExpirationMap.get(email));

            if (isOtpValid) {
                // Remove OTP after successful validation
                otpMap.remove(email);
                otpExpirationMap.remove(email);

                User existingUser = userRepository.findByEmail(email);
                if (existingUser == null) {
                    // Save the email in the database if not present
                    UserDto newUser = new UserDto();
                    newUser.setEmail(email);
                    newUser.setStatus(true);

                    newUser.setRoles(Set.of("2")); // Assign role to user
                    userWsDto.setUserDtoList(List.of(newUser));
                    userService.save(userWsDto);
                }

                UserDetails userDetails = userDetailsService.loadUserByUsername(email);
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

    public UserWsDto saveUsername(UserWsDto userWsDto) {

        for (UserDto userDto : userWsDto.getUserDtoList()) {

            String email = userDto.getEmail();
            String username = userDto.getUsername();

            if (email == null || email.isEmpty() || username == null || username.isEmpty()) {
                userWsDto.setSuccess(false);
                userWsDto.setMessage("Email and Username are required.");





















                return userWsDto;
            }

            User existingUser = userRepository.findByEmail(email);
            if (existingUser == null) {
                userWsDto.setSuccess(false);
                userWsDto.setMessage("User not found. Please validate OTP first.");
                return userWsDto;
            }
            existingUser.setUsername(username);
            if(existingUser.getReferralCode()==null){
            SecureRandom RANDOM = new SecureRandom();

            StringBuilder referralCode = new StringBuilder(LENGTH);

            for (int i = 0; i < LENGTH; i++) {
                int index = RANDOM.nextInt(CHARACTERS.length());
                referralCode.append(CHARACTERS.charAt(index)).toString();
            }
            existingUser.setReferralCode(String.valueOf(referralCode));
        }
            existingUser.setCreationTime(new Date());
            userRepository.save(existingUser);

            userWsDto.setSuccess(true);
            userWsDto.setMessage("Username saved successfully.");
        }
        return userWsDto;
    }

}
