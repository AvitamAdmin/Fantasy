package com.avitam.fantasy11.api.service.impl;

import com.avitam.fantasy11.api.dto.UserDto;
import com.avitam.fantasy11.api.service.EmailOTPService;
import com.avitam.fantasy11.model.User;
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

import java.time.LocalDateTime;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class EmailOtpServiceImpl implements EmailOTPService {

    @Value("${spring.mail.username}")
    private String username;
    @Value("${spring.mail.password}")
    private String password;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private JWTUtility jwtUtility;

    @Autowired
    private UserDetailsService userDetailsService;

    private final ConcurrentHashMap<String, String> otpMap = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, LocalDateTime> otpExpirationMap = new ConcurrentHashMap<>();

    private static final int OTP_LENGTH = 6;
    private static final int OTP_EXPIRATION_MINUTES = 5;

    @Override
    public UserDto sendOtp(UserDto userDto) throws MessagingException {

        String email = userDto.getEmail();

        if (email == null || email.isEmpty()) {
            userDto.setSuccess(false);
            userDto.setMessage("Email is required.");
            return userDto;
        }

        String otp = generateOtp();
        otpMap.put(email, otp);
        otpExpirationMap.put(email, LocalDateTime.now().plusMinutes(OTP_EXPIRATION_MINUTES));

        sendEmail(email, otp);
        userDto.setOtp(otp);
        userDto.setMessage("Otp sent successfully");
        return userDto;
    }


    private void sendEmail(String recipientEmail, String otp) throws MessagingException {


            Properties properties = new Properties();
            properties.put("mail.transport.protocol", "smtp");  // Protocol (SMTP)
            properties.put("mail.smtp.auth", "true");           // Enable authentication
            properties.put("mail.smtp.starttls.enable", "true"); // Use TLS
            properties.put("mail.smtp.host", "smtp.gmail.com");  // SMTP Host
            properties.put("mail.smtp.port", "587");             // SMTP Port

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
    public UserDto validateOtp(UserDto userDto) {
        String email = userDto.getEmail();
        String otp = userDto.getOtp();

        if (email == null || otp == null || email.isEmpty() || otp.isEmpty()) {
            userDto.setSuccess(false);
            userDto.setMessage("Email and OTP are required.");
            return userDto;
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
                User newUser = new User();
                newUser.setEmail(email);
                newUser.setStatus(true);
                userRepository.save(newUser);
            }

            UserDetails userDetails = userDetailsService.loadUserByUsername(email);
            userDto.setToken(jwtUtility.generateToken(userDetails));
            userDto.setSuccess(true);
            userDto.setMessage("OTP validated successfully.");
        } else {
            userDto.setSuccess(false);
            userDto.setMessage("OTP is invalid or has expired.");
        }

        return userDto;
    }

    public UserDto saveUsername(UserDto userDto) {
        String email = userDto.getEmail();
        String username = userDto.getUsername();

        if (email == null || email.isEmpty() || username == null || username.isEmpty()) {
            userDto.setSuccess(false);
            userDto.setMessage("Email and Username are required.");
            return userDto;
        }

        User existingUser = userRepository.findByEmail(email);
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
