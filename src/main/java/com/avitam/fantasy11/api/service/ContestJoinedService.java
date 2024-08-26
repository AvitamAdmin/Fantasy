package com.avitam.fantasy11.api.service;

import com.avitam.fantasy11.model.ContestJoined;

import java.util.Optional;

public interface ContestJoinedService {

    public Optional<ContestJoined> findByRecordId(String recordId);

    void deleteByRecordId(String recordId);

    public void updateByRecordId(String recordId);
}
