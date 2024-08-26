package com.avitam.fantasy11.api.service.impl;

import com.avitam.fantasy11.api.service.LanguageService;
import com.avitam.fantasy11.model.Language;
import com.avitam.fantasy11.model.LanguageRepository;
import org.apache.poi.sl.draw.geom.GuideIf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LanguageServiceImpl implements LanguageService {

    @Autowired
    private LanguageRepository languageRepository;

    @Override
    public Optional<Language> findByRecordId(String recordId) {
        return languageRepository.findByRecordId(recordId);
    }

    @Override
    public void deleteByRecordId(String recordId) {
        languageRepository.deleteByRecordId(recordId);
    }

    @Override
    public void updateByRecordId(String recordId) {
        Optional<Language> languageOptional=languageRepository.findByRecordId(recordId);
        languageOptional.ifPresent(language -> languageRepository.save((language)));
    }
}
