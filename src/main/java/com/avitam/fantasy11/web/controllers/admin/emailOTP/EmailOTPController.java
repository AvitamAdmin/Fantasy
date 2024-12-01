package com.avitam.fantasy11.web.controllers.admin.emailOTP;

import com.avitam.fantasy11.api.dto.UserDto;
import com.avitam.fantasy11.api.service.EmailOTPService;
import com.avitam.fantasy11.model.User;
import jakarta.mail.MessagingException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/email")
public class EmailOTPController {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private EmailOTPService emailOTPService;

    // Endpoint to send OTP
    @PostMapping("/send-otp")
    public UserDto sendOtp(@RequestBody UserDto userDto) throws MessagingException {

        return emailOTPService.sendOtp(userDto);
    }

    // Endpoint to validate OTP
    @PostMapping("/validate-otp")
    public UserDto validateOtp(@RequestBody UserDto userDto) {
        return emailOTPService.validateOtp(userDto);
    }

    @PostMapping("/saveUserName")
    public UserDto saveUsername(@RequestBody User user) {
        UserDto userDto = modelMapper.map(user, UserDto.class);
        return emailOTPService.saveUsername(userDto);
    }

}
