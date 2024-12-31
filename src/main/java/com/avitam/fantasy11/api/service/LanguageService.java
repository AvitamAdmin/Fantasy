package com.avitam.fantasy11.api.service;

import com.avitam.fantasy11.api.dto.LanguageWsDto;
import com.avitam.fantasy11.model.Language;


public interface LanguageService {

    Language findByRecordId(String recordId);

    void deleteByRecordId(String recordId);

    void updateByRecordId(String recordId);

    LanguageWsDto handleEdit(LanguageWsDto request);
}
