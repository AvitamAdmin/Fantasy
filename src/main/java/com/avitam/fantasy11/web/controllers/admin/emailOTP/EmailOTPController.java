package com.avitam.fantasy11.web.controllers.admin.emailOTP;

import com.avitam.fantasy11.api.dto.UserWsDto;
import com.avitam.fantasy11.api.service.EmailOTPService;
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
    public UserWsDto sendOtp(@RequestBody UserWsDto userWsDto) throws MessagingException {

        return emailOTPService.sendOtp(userWsDto);
    }

    // Endpoint to validate OTP
    @PostMapping("/validate-otp")
    public UserWsDto validateOtp(@RequestBody UserWsDto userWsDto) {

        return emailOTPService.validateOtp(userWsDto);
    }

    @PostMapping("/save-userName")
    public UserWsDto saveUsername(@RequestBody UserWsDto userWsDto) {
        return emailOTPService.saveUsername(userWsDto);
    }

}
