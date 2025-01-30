package com.avitam.fantasy11.repository;

import com.avitam.fantasy11.model.OTP;
import com.avitam.fantasy11.repository.generic.GenericImportRepository;

public interface OtpRepository extends GenericImportRepository<OTP> {
    Object findByStatusOrderByIdentifier(boolean b);

    OTP findByRecordId(String recordId);

    void deleteByRecordId(String recordId);
}
