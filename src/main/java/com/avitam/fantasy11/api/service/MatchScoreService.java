package com.avitam.fantasy11.api.service;

import com.avitam.fantasy11.api.dto.MatchScoreDto;
import com.avitam.fantasy11.model.MatchScore;

public interface MatchScoreService {
        MatchScore findByRecordId(String recordId);

        void deleteByRecordId(String recordId);

        void updateByRecordId(String recordId);

        MatchScoreDto handleEdit(MatchScoreDto request);
}
