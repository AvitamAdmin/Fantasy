package com.avitam.fantasy11.api.service;

import com.avitam.fantasy11.api.dto.PointsUpdateDto;
import com.avitam.fantasy11.model.PointsUpdate;


public interface PointsUpdateService {

    PointsUpdate findByRecordId(String recordId);

    void deleteByRecordId(String recordId);

    PointsUpdateDto handleEdit(PointsUpdateDto request);

    void updateByRecordId(String recordId);


}
