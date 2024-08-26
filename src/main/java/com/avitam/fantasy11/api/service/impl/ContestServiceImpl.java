package com.avitam.fantasy11.api.service.impl;

import com.avitam.fantasy11.api.service.ContestService;
import com.avitam.fantasy11.model.Contest;
import com.avitam.fantasy11.model.ContestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ContestServiceImpl implements ContestService {

    @Autowired
    private ContestRepository contestRepository;

    @Override
    public Optional<Contest> findByRecordId(String recordId) {
        return contestRepository.findByRecordId(recordId);
    }


    @Override
    public void deleteByRecordId(String recordId) {
        contestRepository.deleteByRecordId(recordId);
    }

    @Override
    public void updateByRecordId(String recordId) {
       Optional<Contest> contestOptional=contestRepository.findByRecordId(recordId);

       if(contestOptional.isPresent())
       {
           contestRepository.save(contestOptional.get());
       }
    }


}
