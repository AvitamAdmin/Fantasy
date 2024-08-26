package com.avitam.fantasy11.api.service.impl;

import com.avitam.fantasy11.api.service.MatchTypeService;
import com.avitam.fantasy11.model.MatchScore;
import com.avitam.fantasy11.model.MatchType;
import com.avitam.fantasy11.model.MatchTypeRepository;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MatchTypeServiceImpl implements MatchTypeService {

    @Autowired
    private MatchTypeRepository matchTypeRepository;

    @Override
    public Optional<MatchType> findByRecordId(String recordId) {
        return matchTypeRepository.findByRecordId(recordId);
    }

    @Override
    public void deleteByRecordId(String recordId) {
        matchTypeRepository.deleteByRecordId(recordId);

    }

    @Override
    public void updateByRecordId(String recordId) {
        Optional<MatchType> matchTypeOptional=matchTypeRepository.findByRecordId(recordId);
        matchTypeOptional.ifPresent(matchType -> matchTypeRepository.save(matchType));
    }

}
