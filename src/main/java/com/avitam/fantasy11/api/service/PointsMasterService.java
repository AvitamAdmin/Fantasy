package com.avitam.fantasy11.api.service;

import com.avitam.fantasy11.api.dto.PointsMasterDto;
import com.avitam.fantasy11.api.dto.PointsMasterWsDto;
import com.avitam.fantasy11.model.PointsMaster;

public interface PointsMasterService {

    public PointsMaster findByRecordId(String recordId);

    public void deleteByRecordId(String recordId);

    public  void updateByRecordId(String recordId);

    PointsMasterWsDto handleEdit(PointsMasterWsDto request);
}
