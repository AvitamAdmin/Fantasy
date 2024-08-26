package com.avitam.fantasy11.api.service;

import com.avitam.fantasy11.model.Language;

import java.util.Optional;

public interface LanguageService {

    public Optional<Language> findByRecordId(String recordId);

    public void deleteByRecordId(String recordId);



    public void updateByRecordId(String recordId);
}
