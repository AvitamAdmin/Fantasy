package com.avitam.fantasy11.api.service;

import com.avitam.fantasy11.api.dto.LanguageDto;
import com.avitam.fantasy11.model.Language;


public interface LanguageService {

    public Language findByRecordId(String recordId);

    public void deleteByRecordId(String recordId);

    public void updateByRecordId(String recordId);

    public LanguageDto handleEdit(LanguageDto request);
}
