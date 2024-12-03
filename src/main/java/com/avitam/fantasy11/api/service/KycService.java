package com.avitam.fantasy11.api.service;

import com.avitam.fantasy11.api.dto.KYCDto;
import com.avitam.fantasy11.api.dto.KYCWsDto;
import com.avitam.fantasy11.model.KYC;


public interface KycService {
       KYC findByRecordId(String recordId);

    KYCWsDto handleEdit(KYCWsDto request);

    void deleteByRecordId(String recordId);

        KYCDto  handleEdit(KYCDto request);

        void updateByRecordId(String recordId);
}
