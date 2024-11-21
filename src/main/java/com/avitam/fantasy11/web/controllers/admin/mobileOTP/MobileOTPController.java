package com.avitam.fantasy11.web.controllers.admin.mobileOTP;

import com.avitam.fantasy11.api.dto.UserDto;
import com.avitam.fantasy11.api.service.MobileOTPService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/mobile")
public class MobileOTPController {

    @Autowired
    private MobileOTPService mobileOTPService;

    @PostMapping("/send-otp")
    public UserDto sendOtp(@RequestBody UserDto userDto) {

        return mobileOTPService.sendOtp(userDto);
    }

    @PostMapping("/validate-otp")
    public UserDto validateOtp(@RequestBody UserDto userDto) {

        return mobileOTPService.validateOtp(userDto);
    }

    @PostMapping("/save-username")
    public UserDto saveUsername(@RequestBody  UserDto userDto){
        return mobileOTPService.saveUsername(userDto);
    }
}
