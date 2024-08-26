package com.avitam.fantasy11.api.service.impl;

import com.avitam.fantasy11.api.service.MobileTokenService;
import com.avitam.fantasy11.model.MobileToken;
import com.avitam.fantasy11.model.MobileTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MobileTokenServiceImpl implements MobileTokenService {

    @Autowired
    private MobileTokenRepository mobileTokenRepository;

    @Override
    public Optional<MobileToken> findByRecordId(String recordId) {
        return mobileTokenRepository.findByRecordId(recordId);
    }

    @Override
    public void deleteByRecordId(String recordId) {
        mobileTokenRepository.deleteByRecordId(recordId);
    }

    @Override
    public void updateByRecordId(String recordId) {
        Optional<MobileToken> mobileTokenOptional=mobileTokenRepository.findByRecordId(recordId);
        mobileTokenOptional.ifPresent(mobileToken -> mobileTokenRepository.save(mobileToken));
    }
}
