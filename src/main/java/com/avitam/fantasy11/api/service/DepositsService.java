package com.avitam.fantasy11.api.service;

import com.avitam.fantasy11.api.dto.DepositsDto;
import com.avitam.fantasy11.model.Deposits;
import jakarta.mail.Flags;

public interface DepositsService {

    Deposits findByRecordId(String recordId);

    void deleteByRecordId(String recordId);

    DepositsDto handleEdit(DepositsDto request,int flag);

    void updateByRecordId(String recordId);

}