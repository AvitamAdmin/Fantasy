package com.avitam.fantasy11.api.service;

import com.avitam.fantasy11.api.dto.KYCWsDto;
import com.avitam.fantasy11.model.KYC;


public interface KycService {

    KYC findByRecordId(String recordId);

    KYCWsDto handleEdit(KYCWsDto request);

    void deleteByRecordId(String recordId);

    void updateByRecordId(String recordId);
}
