package com.avitam.fantasy11.api.service.impl;

import com.avitam.fantasy11.api.service.MatchesService;
import com.avitam.fantasy11.model.Matches;
import com.avitam.fantasy11.model.MatchesRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class MatchesServiceImpl implements MatchesService {

    @Autowired
    private MatchesRepository matchesRepository;
    @Override
    public Optional<Matches> findByRecordId(String recordId) {
        return matchesRepository.findByRecordId(recordId);
    }

    @Override
    public void deleteByRecordId(String recordId) {
        matchesRepository.deleteByRecordId(recordId);
    }

    @Override
    public void updateByRecordId(String recordId) {
        Optional<Matches> matchesOptional=matchesRepository.findByRecordId(recordId);
        matchesOptional.ifPresent(matches -> matchesRepository.save(matches));
    }
}
