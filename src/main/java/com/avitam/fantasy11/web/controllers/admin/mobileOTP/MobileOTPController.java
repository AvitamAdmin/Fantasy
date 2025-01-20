package com.avitam.fantasy11.web.controllers.admin.mobileOTP;

import com.avitam.fantasy11.api.dto.UserWsDto;
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
    public UserWsDto sendOtp(@RequestBody UserWsDto userWsDto) {

        return mobileOTPService.sendOtp(userWsDto);
    }

    @PostMapping("/validate-otp")
    public UserWsDto validateOtp(@RequestBody UserWsDto userWsDto) {

        return mobileOTPService.validateOtp(userWsDto);
    }

    @PostMapping("/save-username")
    public UserWsDto saveUsername(@RequestBody  UserWsDto userWsDto){
        return mobileOTPService.saveUsername(userWsDto);
    }
}
