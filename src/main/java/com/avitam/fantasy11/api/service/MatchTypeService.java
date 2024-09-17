package com.avitam.fantasy11.api.service;

import com.avitam.fantasy11.api.dto.MatchTypeDto;
import com.avitam.fantasy11.model.MatchType;

public interface MatchTypeService {


    MatchType findByRecordId(String recordId);

    MatchTypeDto handleEdit(MatchTypeDto request);

    void deleteByRecordId(String recordId);


    void updateByRecordId(String recordId);
}
