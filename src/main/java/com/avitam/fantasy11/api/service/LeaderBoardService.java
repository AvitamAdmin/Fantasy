package com.avitam.fantasy11.api.service;

import com.avitam.fantasy11.api.dto.LeaderBoardWsDto;
import com.avitam.fantasy11.model.LeaderBoard;


public interface LeaderBoardService {
    LeaderBoard findByRecordId(String userId);

    void deleteByRecordId(String recordId);

    LeaderBoardWsDto handleEdit(LeaderBoardWsDto request);

    void updateByRecordId(String recordId);

}
