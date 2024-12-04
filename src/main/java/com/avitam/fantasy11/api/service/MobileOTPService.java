package com.avitam.fantasy11.api.service;

import com.avitam.fantasy11.api.dto.UserDto;
import com.avitam.fantasy11.api.dto.UserWsDto;

public interface MobileOTPService {

    public UserWsDto sendOtp(UserWsDto userwsDto);

    public UserWsDto validateOtp(UserWsDto userWsDto);

    public UserWsDto saveUsername(UserWsDto userWsDto);
}
