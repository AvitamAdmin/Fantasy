package com.avitam.fantasy11.api.service;

import com.avitam.fantasy11.api.dto.GatewaysAutomaticWsDto;
import com.avitam.fantasy11.model.GatewaysAutomatic;

public interface GatewaysAutomaticService {

    GatewaysAutomatic findByRecordId(String recordId);

    void deleteByRecordId(String recordId) ;

    GatewaysAutomaticWsDto handleEdit(GatewaysAutomaticWsDto gatewaysAutomaticWsDto);

    void updateByRecordId(GatewaysAutomaticWsDto recordId);
}
