package com.avitam.fantasy11.api.service.impl;

import com.avitam.fantasy11.api.service.KycService;
import com.avitam.fantasy11.model.KYC;
import com.avitam.fantasy11.model.KYCRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class KycServiceImpl implements KycService {


    @Autowired
    private KYCRepository kycRepository;

    @Override
    public Optional<KYC> findByRecordId(String recordId) {
        return kycRepository.findByRecordId(recordId) ;
    }

    @Override
    public void deleteByRecordId(String recordId) {
        kycRepository.deleteByRecordId(recordId);
    }

    @Override
    public void updateByRecordId(String recordId) {
       Optional<KYC> kycOptional= kycRepository.findByRecordId(recordId) ;
        kycOptional.ifPresent(kyc -> kycRepository.save(kyc));
    }


}
