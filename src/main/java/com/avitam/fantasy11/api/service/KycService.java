package com.avitam.fantasy11.api.service;

import com.avitam.fantasy11.model.Address;
import com.avitam.fantasy11.model.KYC;

public interface KycService {
        KYC findByUserId(String userId);

        KYC deleteByUserId(String userId);


        void save(KYC kyc);

        KYC updateByUserId(String userId);
}
