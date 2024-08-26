package com.avitam.fantasy11.api.service.impl;

import com.avitam.fantasy11.api.service.LineUpStatusService;
import com.avitam.fantasy11.model.LineUpStatus;
import com.avitam.fantasy11.model.LineUpStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LineUpStatusImpl implements LineUpStatusService {

    @Autowired
    private LineUpStatusRepository lineUpStatusRepository;

    @Override
    public Optional<LineUpStatus> findByRecordId(String recordId) {
        return lineUpStatusRepository.findByRecordId(recordId);
    }

    @Override
    public void deleteByRecordId(String recordId) {
        lineUpStatusRepository.deleteByRecordId(recordId);
    }

    @Override
    public void updateByRecordId(String recordId) {
        Optional<LineUpStatus> lineUpStatusOptional=lineUpStatusRepository.findByRecordId(recordId);
        lineUpStatusOptional.ifPresent(lineUpStatus -> lineUpStatusRepository.save(lineUpStatus));
    }
}
