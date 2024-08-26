package com.avitam.fantasy11.api.service.impl;

import com.avitam.fantasy11.api.service.PointsUpdateService;
import com.avitam.fantasy11.model.PointsUpdate;
import com.avitam.fantasy11.model.PointsUpdateRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class PointsUpdateServiceImpl implements PointsUpdateService {
    @Autowired
    private PointsUpdateRepository pointsUpdateRepository;

    @Override
    public Optional<PointsUpdate> findByRecordId(String recordId) {
        return pointsUpdateRepository.findByRecordId(recordId);
    }

    @Override
    public void deleteByRecordId(String recordId) {
        pointsUpdateRepository.deleteByRecordId(recordId);
    }

    @Override
    public void updateByRecordId(String recordId) {

        Optional<PointsUpdate> pointsUpdateOptional=pointsUpdateRepository.findByRecordId(recordId);
        pointsUpdateOptional.ifPresent(pointsUpdate -> pointsUpdateRepository.save(pointsUpdate));

    }
}
