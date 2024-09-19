package com.avitam.fantasy11.api.service;

import com.avitam.fantasy11.api.dto.AddressDto;
import com.avitam.fantasy11.api.dto.PendingWithdrawalDto;
import com.avitam.fantasy11.model.Address;
import com.avitam.fantasy11.model.PendingWithdrawal;

public interface PendingWithdrawalService {

    PendingWithdrawal findByRecordId(String recordId);

    void deleteByRecordId(String recordId) ;

    PendingWithdrawalDto handleEdit(PendingWithdrawalDto request);

    void updateByRecordId(String recordId);
}
