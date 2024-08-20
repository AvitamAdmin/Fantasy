package com.avitam.fantasy11.api.service;

import com.avitam.fantasy11.model.MobileToken;

public interface MobileTokenService {

    MobileToken findByMobileNumber(String mobileNumber);

    void save(MobileToken mobile);

    MobileToken deleteById(String id);

    MobileToken updateMobileNumber(String mobileNumber);

}
