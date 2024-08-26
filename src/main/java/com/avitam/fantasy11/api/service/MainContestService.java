package com.avitam.fantasy11.api.service;

import com.avitam.fantasy11.model.MainContest;

import java.util.Optional;

public interface MainContestService {

    public Optional<MainContest> findByRecordId(String recordId);

    public void deleteByRecordId(String recordId);

    public void updateByRecordId(String recordId);

}
