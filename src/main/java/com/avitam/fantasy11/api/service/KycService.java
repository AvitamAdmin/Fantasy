package com.avitam.fantasy11.api.service;

import com.avitam.fantasy11.model.KYC;

public interface KycService {


    KYC findByUserId(String userId);
    void save(KYC kyc);
    KYC deleteById(KYC kyc);
    KYC updateKyc(String userId);
}
