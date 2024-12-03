package com.avitam.fantasy11.api.service;

import com.avitam.fantasy11.api.dto.SportTypeDto;
import com.avitam.fantasy11.api.dto.SportTypeWsDto;
import com.avitam.fantasy11.model.SportType;

public interface SportTypeService {

    SportType findByRecordId(String recordId);

    SportTypeWsDto handleEdit(SportTypeWsDto request);

    void deleteByRecordId(String recordId);

    void updateByRecordId(String recordId);
}
