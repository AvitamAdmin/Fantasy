package com.avitam.fantasy11.api.service;

import com.avitam.fantasy11.api.dto.UserDto;

public interface MobileOTPService {

    public UserDto sendOtp(UserDto userDto);

    public UserDto validateOtp(UserDto userDto);
}
