package com.avitam.fantasy11.api.service;

import com.avitam.fantasy11.api.dto.MatchScoreWsDto;
import com.avitam.fantasy11.model.MatchScore;

public interface MatchScoreService {
    MatchScore findByRecordId(String recordId);

    void deleteByRecordId(String recordId);

    void updateByRecordId(String recordId);

    MatchScoreWsDto handleEdit(MatchScoreWsDto request);
}
