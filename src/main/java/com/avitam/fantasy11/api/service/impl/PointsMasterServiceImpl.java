package com.avitam.fantasy11.api.service.impl;

import com.avitam.fantasy11.api.service.PointsMasterService;
import com.avitam.fantasy11.model.PointsMaster;
import com.avitam.fantasy11.model.PointsMasterRepository;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PointsMasterServiceImpl implements PointsMasterService {
    @Autowired
    private PointsMasterRepository pointsMasterRepository;
    @Override
    public Optional<PointsMaster> findByRecordId(String recordId) {
        return pointsMasterRepository.findByRecordId(recordId);
    }

    @Override
    public void deleteByRecordId(String recordId) {
        pointsMasterRepository.deleteByRecordId(recordId);
    }

    @Override
    public void updateByRecordId(String recordId) {
        Optional<PointsMaster> pointsMasterOptional=pointsMasterRepository.findByRecordId(recordId);
        pointsMasterOptional.ifPresent(pointsMaster -> pointsMasterRepository.save(pointsMaster));
    }
}
