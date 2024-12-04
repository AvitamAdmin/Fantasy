package com.avitam.fantasy11.api.service;

import com.avitam.fantasy11.api.dto.PendingWithdrawalWsDto;
import com.avitam.fantasy11.model.PendingWithdrawal;

public interface PendingWithdrawalService {

    PendingWithdrawal findByRecordId(String recordId);

    void deleteByRecordId(String recordId) ;

    PendingWithdrawalWsDto handleEdit(PendingWithdrawalWsDto request);

    void updateByRecordId(String recordId);
}
