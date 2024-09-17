package com.avitam.fantasy11.api.service;


import com.avitam.fantasy11.model.LineUpStatus;
import  com.avitam.fantasy11.api.dto.LineUpStatusDto;

public interface LineUpStatusService {

    LineUpStatus findByRecordId(String recordId);

    LineUpStatusDto handleEdit(LineUpStatusDto request);

    void deleteByRecordId(String recordId);

    void updateByRecordId(String recordId);
}
