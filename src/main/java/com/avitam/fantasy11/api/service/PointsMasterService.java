package com.avitam.fantasy11.api.service;

import com.avitam.fantasy11.api.dto.PointsMasterWsDto;
import com.avitam.fantasy11.model.PointsMaster;

public interface PointsMasterService {

    PointsMaster findByRecordId(String recordId);

    void deleteByRecordId(String recordId);

    void updateByRecordId(String recordId);

    PointsMasterWsDto handleEdit(PointsMasterWsDto request);
}
