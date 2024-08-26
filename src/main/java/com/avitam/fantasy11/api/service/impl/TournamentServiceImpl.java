package com.avitam.fantasy11.api.service.impl;

import com.avitam.fantasy11.api.service.TournamentService;
import com.avitam.fantasy11.model.Tournament;
import com.avitam.fantasy11.model.TournamentRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class TournamentServiceImpl implements TournamentService {

    @Autowired
    private TournamentRepository tournamentRepository;
    @Override
    public Optional<Tournament> findByRecordId(String recordId) {
        return tournamentRepository.findByRecordId(recordId);
    }

    @Override
    public void deleteByRecordId(String recordId) {
        tournamentRepository.deleteByRecordId(recordId);
    }

    @Override
    public void updateByRecordId(String recordId) {
        Optional<Tournament> tournamentOptional=tournamentRepository.findByRecordId(recordId);
        tournamentOptional.ifPresent(tournament -> tournamentRepository.save(tournament));
    }
}
