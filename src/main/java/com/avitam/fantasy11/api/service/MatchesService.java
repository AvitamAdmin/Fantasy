package com.avitam.fantasy11.api.service;

import com.avitam.fantasy11.api.dto.MatchesWsDto;
import com.avitam.fantasy11.model.Matches;


public interface MatchesService {

    Matches findByRecordId(String recordId);

    void deleteByRecordId(String recordId);

    void updateByRecordId(String recordId);

    MatchesWsDto handleEdit(MatchesWsDto request);
}
