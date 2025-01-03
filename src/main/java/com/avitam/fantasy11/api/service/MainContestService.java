package com.avitam.fantasy11.api.service;

import com.avitam.fantasy11.api.dto.MainContestWsDto;
import com.avitam.fantasy11.model.MainContest;

public interface MainContestService {

    MainContest findByRecordId(String recordId);

    void deleteByRecordId(String recordId);

    void updateByRecordId(String recordId);

    MainContestWsDto handleEdit(MainContestWsDto request);

}
