package com.avitam.fantasy11.api.service;

import com.avitam.fantasy11.api.dto.GatewaysManualDto;
import com.avitam.fantasy11.model.GatewaysManual;

public interface GatewaysManualService {

    GatewaysManual findByRecordId(String recordId);

    void deleteByRecordId(String recordId) ;

    GatewaysManualDto handleEdit(GatewaysManualDto request);

    void updateByRecordId(String recordId);
}
