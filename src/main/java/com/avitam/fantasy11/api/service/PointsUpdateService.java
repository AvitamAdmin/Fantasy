package com.avitam.fantasy11.api.service;

import com.avitam.fantasy11.api.dto.PointsUpdateDto;
import com.avitam.fantasy11.api.dto.PointsUpdateWsDto;
import com.avitam.fantasy11.model.PointsUpdate;


public interface PointsUpdateService {

    PointsUpdate findByRecordId(String recordId);

    void deleteByRecordId(String recordId);

    PointsUpdateWsDto handleEdit(PointsUpdateWsDto request);

    void updateByRecordId(String recordId);


}
