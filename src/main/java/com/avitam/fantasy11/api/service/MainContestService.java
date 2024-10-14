package com.avitam.fantasy11.api.service;

import com.avitam.fantasy11.model.MainContest;
import com.avitam.fantasy11.api.dto.MainContestDto;

public interface MainContestService {

    public MainContest findByRecordId(String recordId);

    public void deleteByRecordId(String recordId);

    public void updateByRecordId(String recordId);

    MainContestDto handleEdit(MainContestDto request);

}
