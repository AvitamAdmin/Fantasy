package com.avitam.fantasy11.api.service.impl;

import com.avitam.fantasy11.api.service.MatchScoreService;
import com.avitam.fantasy11.model.MatchScore;
import com.avitam.fantasy11.model.MatchScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class MatchScoreServiceImpl implements MatchScoreService {
   @Autowired
   private MatchScoreRepository matchScoreRepository;
    @Override
    public Optional<MatchScore> findByRecordId(String recordId) {
        return matchScoreRepository.findByRecordId(recordId);
    }

    @Override
    public void deleteByRecordId(String recordId) {
        matchScoreRepository.deleteByRecordId(recordId);
    }

    @Override
    public void updateByRecordId(String recordId) {
        Optional<MatchScore> matchScoreOptional=matchScoreRepository.findByRecordId(recordId);
        matchScoreOptional.ifPresent(matchScore -> matchScoreRepository.save(matchScore));
    }
}
