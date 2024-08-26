package com.avitam.fantasy11.api.service.impl;

import com.avitam.fantasy11.api.service.MainContestService;
import com.avitam.fantasy11.model.MainContest;
import com.avitam.fantasy11.model.MainContestRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class MainContestServiceImpl implements MainContestService {

    @Autowired
    private MainContestRepository mainContestRepository;
    @Override
    public Optional<MainContest> findByRecordId(String recordId) {
        return mainContestRepository.findByRecordId(recordId);
    }

    @Override
    public void deleteByRecordId(String recordId) {
        mainContestRepository.deleteByRecordId(recordId);
    }

    @Override
    public void updateByRecordId(String recordId) {
        Optional<MainContest> mainContestOptional=mainContestRepository.findByRecordId(recordId);
        mainContestOptional.ifPresent(mainContest -> mainContestRepository.save(mainContest));
    }
}
