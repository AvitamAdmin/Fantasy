package com.avitam.fantasy11.api.service;

import com.avitam.fantasy11.api.dto.WithdrawalDetailsWsDto;
import com.avitam.fantasy11.model.WithdrawalDetails;

public interface WithdrawalDetailsService {

    WithdrawalDetails findByRecordId(String recordId);

    void deleteByRecordId(String recordId);

    WithdrawalDetailsWsDto handleEdit(WithdrawalDetailsWsDto request);

    void updateByRecordId(String recordId);
}
