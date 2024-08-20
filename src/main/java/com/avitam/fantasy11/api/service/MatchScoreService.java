package com.avitam.fantasy11.api.service;

import com.avitam.fantasy11.model.MatchScore;

public interface MatchScoreService {
        MatchScore findByMatchId(String matchId);

        MatchScore deleteByMatchId(String matchId);

        void save(MatchScore matchScore);

        MatchScore updateByMatchId(String matchId);
}
