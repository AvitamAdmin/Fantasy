package com.avitam.fantasy11.api.service;

import com.avitam.fantasy11.api.dto.WithdrawalMethodsDto;
import com.avitam.fantasy11.model.WithdrawalMethods;

public interface WithdrawalMethodsService {

    WithdrawalMethods findByRecordId(String recordId);

    void deleteByRecordId(String recordId) ;

    WithdrawalMethodsDto handleEdit(WithdrawalMethodsDto request);

    void updateByRecordId(String recordId);
}
