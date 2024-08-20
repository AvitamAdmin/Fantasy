package com.avitam.fantasy11.api.service;

import com.avitam.fantasy11.model.LeaderBoard;

public interface LeaderBoardService {
        LeaderBoard findByUserId(String userId);

        LeaderBoard deleteByUserId(String userId);

        void save(LeaderBoard leaderBoard);

        LeaderBoard updateByUserId(String userId);
}
