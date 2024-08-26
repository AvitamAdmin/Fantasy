package com.avitam.fantasy11.api.service.impl;

import com.avitam.fantasy11.api.service.TeamLineUpService;
import com.avitam.fantasy11.model.MatchType;
import com.avitam.fantasy11.model.TeamLineup;
import com.avitam.fantasy11.model.TeamLineupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TeamLineUpServiceImpl implements TeamLineUpService {

    @Autowired
    private TeamLineupRepository teamLineupRepository;

    @Override
    public Optional<TeamLineup> findByRecordId(String recordId) {
        return teamLineupRepository.findByRecordId(recordId);
    }

    @Override
    public void deleteByRecordId(String recordId) {
        teamLineupRepository.deleteByRecordId(recordId);
    }

    @Override
    public void updateByRecordId(String recordId) {
        Optional<TeamLineup> teamLineupOptional=teamLineupRepository.findByRecordId(recordId);
        teamLineupOptional.ifPresent(teamLineup -> teamLineupRepository.save(teamLineup));
    }
}
