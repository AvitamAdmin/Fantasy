package com.avitam.fantasy11.api.service;

import com.avitam.fantasy11.model.PointsUpdate;
import com.avitam.fantasy11.model.Tournament;

import java.util.Optional;

public interface TournamentService {

    Optional<Tournament> findByRecordId(String recordId);

    void deleteByRecordId(String recordId);

    void updateByRecordId(String recordId);
}
