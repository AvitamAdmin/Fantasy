package com.avitam.fantasy11.api.service.impl;

import com.avitam.fantasy11.api.service.SportTypeService;
import com.avitam.fantasy11.model.Contest;
import com.avitam.fantasy11.model.SportType;
import com.avitam.fantasy11.model.SportTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SportTypeServiceImpl implements SportTypeService {
   @Autowired
   private SportTypeRepository sportTypeRepository;
    @Override
    public Optional<SportType> findByRecordId(String recordId) {
        return sportTypeRepository.findByRecordId(recordId);
    }

    @Override
    public void deleteByRecordId(String recordId) {
        sportTypeRepository.findByRecordId(recordId);
    }

    @Override
    public void updateByRecordId(String recordId) {
        Optional<SportType> sportTypeOptional=sportTypeRepository.findByRecordId(recordId);
        sportTypeOptional.ifPresent(sportType -> sportTypeRepository.save(sportType));
    }
}
