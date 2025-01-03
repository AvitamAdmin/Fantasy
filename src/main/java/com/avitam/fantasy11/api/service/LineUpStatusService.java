package com.avitam.fantasy11.api.service;


import com.avitam.fantasy11.api.dto.LineUpStatusWsDto;
import com.avitam.fantasy11.model.LineUpStatus;

public interface LineUpStatusService {

    LineUpStatus findByRecordId(String recordId);

    LineUpStatusWsDto handleEdit(LineUpStatusWsDto request);

    void deleteByRecordId(String recordId);

    void updateByRecordId(String recordId);
}
