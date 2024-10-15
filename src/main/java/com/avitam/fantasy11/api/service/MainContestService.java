package com.avitam.fantasy11.api.service;

import com.avitam.fantasy11.api.dto.MainContestDto;
import com.avitam.fantasy11.model.MainContest;

public interface MainContestService {

    public MainContest findByRecordId(String recordId);

    public void deleteByRecordId(String recordId);

    public void updateByRecordId(String recordId);

    MainContestDto handleEdit(MainContestDto request);

}
