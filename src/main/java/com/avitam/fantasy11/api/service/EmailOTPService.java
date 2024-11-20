package com.avitam.fantasy11.api.service;

import com.avitam.fantasy11.api.dto.UserDto;
import jakarta.mail.MessagingException;

public interface EmailOTPService {

    public UserDto sendOtp(UserDto userDto) throws MessagingException;

    public UserDto validateOtp(UserDto userDto);
}
