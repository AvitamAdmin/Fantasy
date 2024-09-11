package com.avitam.fantasy11.api.service;

import com.avitam.fantasy11.api.dto.ContestJoinedDto;
import com.avitam.fantasy11.model.ContestJoined;

public interface ContestJoinedService {

     ContestJoined findByRecordId(String recordId);

     ContestJoinedDto handleEdit(ContestJoinedDto request);

     void deleteByRecordId(String recordId);

     void updateByRecordId(String recordId);
}
