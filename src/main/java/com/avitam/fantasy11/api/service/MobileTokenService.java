package com.avitam.fantasy11.api.service;

import com.avitam.fantasy11.model.MobileToken;

import java.util.Optional;

public interface MobileTokenService {

    Optional<MobileToken> findByRecordId(String recordId);

    void deleteByRecordId(String recordId);

    void updateByRecordId(String recordId);

}
