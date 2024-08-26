package com.avitam.fantasy11.api.service.impl;

import com.avitam.fantasy11.api.service.ContestJoinedService;
import com.avitam.fantasy11.model.Contest;
import com.avitam.fantasy11.model.ContestJoined;
import com.avitam.fantasy11.model.ContestJoinedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ContestJoinedServiceImpl implements ContestJoinedService {

    @Autowired
    private ContestJoinedRepository contestJoinedRepository;

    @Override
    public Optional<ContestJoined> findByRecordId(String recordId) {
        return contestJoinedRepository.findByRecordId(recordId);
    }

    @Override
    public void deleteByRecordId(String recordId) {
        contestJoinedRepository.deleteByRecordId(recordId);
    }

    @Override
    public void updateByRecordId(String recordId) {

        Optional<ContestJoined> contestJoinedOptional=contestJoinedRepository.findByRecordId(recordId);

        contestJoinedOptional.ifPresent(contestJoined -> contestJoinedRepository.save(contestJoined));


    }


}
