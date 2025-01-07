package com.avitam.fantasy11.api.service.impl;

import com.avitam.fantasy11.api.dto.LanguageDto;
import com.avitam.fantasy11.api.dto.LanguageWsDto;
import com.avitam.fantasy11.api.service.BaseService;
import com.avitam.fantasy11.api.service.LanguageService;
import com.avitam.fantasy11.model.Language;
import com.avitam.fantasy11.repository.EntityConstants;
import com.avitam.fantasy11.repository.LanguageRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LanguageServiceImpl implements LanguageService {

    @Autowired
    private LanguageRepository languageRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private BaseService baseService;

    public static final String ADMIN_LANGUAGE = "/admin/language";

    @Override
    public Language findByRecordId(String recordId) {
        return languageRepository.findByRecordId(recordId);
    }

    @Override
    public void deleteByRecordId(String recordId) {
        languageRepository.deleteByRecordId(recordId);
    }

    @Override
    public void updateByRecordId(String recordId) {
        Language languageOptional = languageRepository.findByRecordId(recordId);
        if (languageOptional != null) {
            languageRepository.save((languageOptional));
        }
    }


    @Override
    public LanguageWsDto handleEdit(LanguageWsDto request) {
        Language languageData = null;
        List<LanguageDto> languageDtos = request.getLanguageDtoList();
        List<Language> languageList = new ArrayList<>();

        for (LanguageDto languageDto1 : languageDtos) {
            if (languageDto1.getRecordId() != null) {
                languageData = languageRepository.findByRecordId(languageDto1.getRecordId());
                modelMapper.map(languageDto1, languageData);
                languageRepository.save(languageData);
                request.setMessage("Data updated Successfully");
            } else {
                if (baseService.validateIdentifier(EntityConstants.LANGUAGE, languageDto1.getIdentifier()) != null) {
                    request.setSuccess(false);
                    request.setMessage("Identifier already present");
                    return request;
                }
                languageData = modelMapper.map(languageDto1, Language.class);
            }
            baseService.populateCommonData(languageData);
            languageList.add(languageData);
            languageData.setStatus(true);
            languageRepository.save(languageData);
            if (languageData.getRecordId() == null) {
                languageData.setRecordId(String.valueOf(languageData.getId().getTimestamp()));
                request.setMessage("Data added successfully");
            }
            languageRepository.save(languageData);
            languageList.add(languageData);
        }
        request.setBaseUrl(ADMIN_LANGUAGE);
        request.setLanguageDtoList(modelMapper.map(languageList, List.class));
        return request;
    }
}
