package com.avitam.fantasy11.api.service;

import com.avitam.fantasy11.api.dto.TournamentDto;
import com.avitam.fantasy11.api.dto.TournamentWsDto;
import com.avitam.fantasy11.model.Tournament;

public interface TournamentService {

    Tournament findByRecordId(String recordId);

    TournamentWsDto handleEdit(TournamentWsDto request);

    void deleteByRecordId(String recordId);

    void updateByRecordId(String recordId);
}
