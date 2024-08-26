package com.avitam.fantasy11.api.service;

import com.avitam.fantasy11.model.PointsUpdate;

import java.util.Optional;

public interface PointsUpdateService {

    Optional<PointsUpdate> findByRecordId(String recordId);

    void deleteByRecordId(String recordId);

    void updateByRecordId(String recordId);


}
