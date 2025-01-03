package com.avitam.fantasy11.api.service;

import com.avitam.fantasy11.api.dto.UserWsDto;
import jakarta.mail.MessagingException;

public interface EmailOTPService {

    UserWsDto sendOtp(UserWsDto userWsDto) throws MessagingException;

    UserWsDto validateOtp(UserWsDto userWsDto);

    UserWsDto saveUsername(UserWsDto userWsDto);
}
