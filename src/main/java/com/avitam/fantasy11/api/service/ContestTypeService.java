package com.avitam.fantasy11.api.service;

import com.avitam.fantasy11.api.dto.ContestTypeWsDto;
import com.avitam.fantasy11.model.ContestType;

public interface ContestTypeService {

    ContestType findByRecordId(String recordId);

    void deleteByRecordId(String recordId);

    void updateByRecordId(String recordId);

    ContestTypeWsDto handleEdit(ContestTypeWsDto request);

}
