package com.avitam.fantasy11.api.service.impl;

import com.avitam.fantasy11.api.service.TeamService;
import com.avitam.fantasy11.model.Team;
import com.avitam.fantasy11.model.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class TeamServiceImpl implements TeamService {
    @Autowired
    private TeamRepository teamRepository;
    @Override
    public Optional<Team> findByRecordId(String recordId) {
        return teamRepository.findByRecordId(recordId);
    }

    @Override
    public void deleteByRecordId(String recordId) {
        teamRepository.deleteByRecordId(recordId);
    }

    @Override
    public void updateByRecordId(String recordId) {
        Optional<Team> teamOptional=teamRepository.findByRecordId(recordId);
        teamOptional.ifPresent(team -> teamRepository.save(team));
    }
}
