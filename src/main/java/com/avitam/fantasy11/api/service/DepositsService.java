package com.avitam.fantasy11.api.service;

import com.avitam.fantasy11.api.dto.DepositsDto;
import com.avitam.fantasy11.model.Deposits;

public interface DepositsService {

    Deposits findByRecordId(String recordId);

    void deleteByRecordId(String recordId);

    DepositsDto handleEdit(DepositsDto request);

    void updateByRecordId(String recordId);

}
