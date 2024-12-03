package com.avitam.fantasy11.api.service;

import com.avitam.fantasy11.api.dto.UserDto;
import com.avitam.fantasy11.api.dto.UserWsDto;
import jakarta.mail.MessagingException;

public interface EmailOTPService {

    public UserWsDto sendOtp(UserWsDto userWsDto) throws MessagingException;

    public UserWsDto validateOtp(UserWsDto userWsDto);

    public UserWsDto saveUsername(UserWsDto userWsDto);
}
