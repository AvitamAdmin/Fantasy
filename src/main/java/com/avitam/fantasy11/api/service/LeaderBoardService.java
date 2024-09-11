package com.avitam.fantasy11.api.service;

import com.avitam.fantasy11.api.dto.LeaderBoardDto;
import com.avitam.fantasy11.model.LeaderBoard;


public interface LeaderBoardService {
        LeaderBoard findByRecordId(String userId);

        void deleteByRecordId(String recordId);

        LeaderBoardDto handleEdit(LeaderBoardDto request);

       void updateByRecordId(String recordId);

}
