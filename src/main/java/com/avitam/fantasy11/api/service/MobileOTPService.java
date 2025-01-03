package com.avitam.fantasy11.api.service;

import com.avitam.fantasy11.api.dto.UserWsDto;

public interface MobileOTPService {

    UserWsDto sendOtp(UserWsDto userwsDto);

    UserWsDto validateOtp(UserWsDto userWsDto);

    UserWsDto saveUsername(UserWsDto userWsDto);
}
