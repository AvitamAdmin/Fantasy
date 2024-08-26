package com.avitam.fantasy11.api.service.impl;

import com.avitam.fantasy11.api.service.UserTeamsService;
import com.avitam.fantasy11.model.UserTeams;
import com.avitam.fantasy11.model.UserTeamsRepository;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserTeamsServiceImpl implements UserTeamsService {

    @Autowired
    private UserTeamsRepository userTeamsRepository;

    @Override
    public Optional<UserTeams> findByRecordId(String recordId) {
        return userTeamsRepository.findByRecordId(recordId);
    }

    @Override
    public void deleteByRecordId(String recordId) {
       userTeamsRepository.deleteByRecordId(recordId);
    }

    @Override
    public void updateByRecordId(String recordId) {
        Optional<UserTeams> userTeamsOptional=userTeamsRepository.findByRecordId(recordId);
        userTeamsOptional.ifPresent(userTeams -> userTeamsRepository.save(userTeams));
    }


}
