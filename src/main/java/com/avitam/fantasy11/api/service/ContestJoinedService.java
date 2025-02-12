package com.avitam.fantasy11.api.service;

import com.avitam.fantasy11.api.dto.ContestJoinedWsDto;
import com.avitam.fantasy11.model.ContestJoined;

public interface ContestJoinedService {

    ContestJoined findByRecordId(String recordId);

    ContestJoinedWsDto handleEdit(ContestJoinedWsDto request);

    void deleteByRecordId(String recordId);

    void updateByRecordId(String recordId);
}
