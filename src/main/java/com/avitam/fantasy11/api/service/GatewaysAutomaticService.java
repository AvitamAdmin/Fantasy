package com.avitam.fantasy11.api.service;

import com.avitam.fantasy11.api.dto.GatewaysAutomaticDto;
import com.avitam.fantasy11.model.GatewaysAutomatic;

public interface GatewaysAutomaticService {

    GatewaysAutomatic findByRecordId(String recordId);

    void deleteByRecordId(String recordId) ;

    GatewaysAutomaticDto handleEdit(GatewaysAutomaticDto request);

    void updateByRecordId(String recordId);
}
