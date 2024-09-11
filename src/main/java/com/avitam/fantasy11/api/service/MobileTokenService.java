package com.avitam.fantasy11.api.service;

import com.avitam.fantasy11.api.dto.MobileTokenDto;
import com.avitam.fantasy11.model.MobileToken;


public interface MobileTokenService {

    MobileToken findByRecordId(String recordId);

    void deleteByRecordId(String recordId);

    MobileTokenDto handleEdit(MobileTokenDto request);

    void updateByRecordId(String recordId);

}
