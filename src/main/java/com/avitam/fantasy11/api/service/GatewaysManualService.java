package com.avitam.fantasy11.api.service;

import com.avitam.fantasy11.api.dto.GatewaysManualWsDto;
import com.avitam.fantasy11.model.GatewaysManual;

public interface GatewaysManualService {

    GatewaysManual findByRecordId(String recordId);

    void deleteByRecordId(String recordId) ;

    GatewaysManualWsDto handleEdit(GatewaysManualWsDto gatewaysManualWsDto);

    void updateByRecordId(String recordId);
}
