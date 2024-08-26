package com.avitam.fantasy11.api.service;

import com.avitam.fantasy11.model.Address;
import com.avitam.fantasy11.model.KYC;

import java.util.Optional;

public interface KycService {
       Optional<KYC> findByRecordId(String recordId);

        void deleteByRecordId(String recordId);




        void updateByRecordId(String recordId);
}
