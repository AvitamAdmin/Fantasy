package com.avitam.fantasy11.api.service;

import com.avitam.fantasy11.api.dto.WithdrawalMethodsWsDto;
import com.avitam.fantasy11.model.WithdrawalMethods;

public interface WithdrawalMethodsService {

    WithdrawalMethods findByRecordId(String recordId);

    void deleteByRecordId(String recordId);

    WithdrawalMethodsWsDto handleEdit(WithdrawalMethodsWsDto request);

    void updateByRecordId(String recordId);
}
