package com.avitam.fantasy11.api.service;

import com.avitam.fantasy11.api.dto.WithdrawalDetailsDto;
import com.avitam.fantasy11.api.dto.WithdrawalMethodsDto;
import com.avitam.fantasy11.model.WithdrawalDetails;
import com.avitam.fantasy11.model.WithdrawalMethods;

public interface WithdrawalDetailsService {

    WithdrawalDetails findByRecordId(String recordId);

    void deleteByRecordId(String recordId) ;

    WithdrawalDetailsDto handleEdit(WithdrawalDetailsDto request);

    void updateByRecordId(String recordId);
}
