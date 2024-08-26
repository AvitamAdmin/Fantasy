package com.avitam.fantasy11.api.service;

import com.avitam.fantasy11.model.LeaderBoard;

import java.util.Optional;

public interface LeaderBoardService {
        Optional<LeaderBoard> findByRecordId(String userId);

        void deleteByRecordId(String recordId);




       void updateByRecordId(String recordId);
}
