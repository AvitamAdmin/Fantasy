package com.avitam.fantasy11.api.service;

import com.avitam.fantasy11.api.dto.ContestDto;
import com.avitam.fantasy11.model.Contest;


public interface ContestService {
    Contest findByRecordId(String recordId);

    void deleteByRecordId(String recordId);

    ContestDto handleEdit(ContestDto request);

    void updateByRecordId(String recordId);

}
