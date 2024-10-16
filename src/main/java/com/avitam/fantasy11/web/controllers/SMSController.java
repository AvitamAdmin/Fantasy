 package com.avitam.fantasy11.web.controllers;

import com.avitam.fantasy11.sms.OtpResponseDto;
import com.avitam.fantasy11.sms.OtpValidationRequest;
import com.avitam.fantasy11.sms.SmsService;
import com.avitam.fantasy11.model.MobileToken;
import com.avitam.fantasy11.sms.OtpRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/sms")
@Slf4j
public class SMSController {

    @Autowired
    SmsService smsService;
    @GetMapping("/process")
    public String processSms(){

        return "SMS sent";
    }

    @PostMapping("/send-otp")
    public OtpResponseDto sendOtp(@RequestBody OtpRequest otpRequest){
        log.info("insideOtp::"+otpRequest.getUserName());
        return smsService.sendSMS(otpRequest);
    }

    @PostMapping("/validate")
    public String validateOtp(@RequestBody OtpValidationRequest otpValidRequest){
        log.info("inside validateOtp ::"+otpValidRequest);
        return smsService.validateOtp(otpValidRequest);
    }


    @PostMapping("/mobileNo")
        public String sendOtp(@RequestBody MobileToken sms){
            try{

            }
            catch(Exception e){
               e.printStackTrace();
            }
              return "OTP sent successfully";
        }
}
