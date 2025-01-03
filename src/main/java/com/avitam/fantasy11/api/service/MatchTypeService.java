package com.avitam.fantasy11.api.service;

import com.avitam.fantasy11.api.dto.MatchTypeWsDto;
import com.avitam.fantasy11.model.MatchType;

public interface MatchTypeService {

    MatchType findByRecordId(String recordId);

    MatchTypeWsDto handleEdit(MatchTypeWsDto request);

    void deleteByRecordId(String recordId);

    void updateByRecordId(String recordId);
}
