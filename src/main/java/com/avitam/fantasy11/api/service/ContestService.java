package com.avitam.fantasy11.api.service;

import com.avitam.fantasy11.model.Contest;

import java.util.Optional;

public interface ContestService {
    Optional<Contest> findByRecordId(String recordId);

    void deleteByRecordId(String recordId);

    void updateByRecordId(String recordId);
}
