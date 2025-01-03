package com.avitam.fantasy11.api.service;

import com.avitam.fantasy11.api.dto.MobileTokenWsDto;
import com.avitam.fantasy11.model.MobileToken;


public interface MobileTokenService {

    MobileToken findByRecordId(String recordId);

    void deleteByRecordId(String recordId);

    MobileTokenWsDto handleEdit(MobileTokenWsDto request);

    void updateByRecordId(String recordId);

    String generateOtpForUser(String email);

}
