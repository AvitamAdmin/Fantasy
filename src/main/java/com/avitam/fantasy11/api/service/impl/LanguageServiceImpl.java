package com.avitam.fantasy11.api.service.impl;

import com.avitam.fantasy11.api.dto.KYCDto;
import com.avitam.fantasy11.api.dto.KYCWsDto;
import com.avitam.fantasy11.api.dto.LanguageDto;
import com.avitam.fantasy11.api.dto.LanguageWsDto;
import com.avitam.fantasy11.api.service.BaseService;
import com.avitam.fantasy11.api.service.LanguageService;
import com.avitam.fantasy11.core.service.CoreService;
import com.avitam.fantasy11.model.KYC;
import com.avitam.fantasy11.model.Language;
import com.avitam.fantasy11.repository.EntityConstants;
import com.avitam.fantasy11.repository.LanguageRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class LanguageServiceImpl implements LanguageService {

    @Autowired
    private LanguageRepository languageRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CoreService coreService;

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
        Language languageOptional=languageRepository.findByRecordId(recordId);
        if(languageOptional!=null)
        {
            languageRepository.save((languageOptional));
        }

    }


@Override
public LanguageWsDto handleEdit(LanguageWsDto request) {
    LanguageWsDto languageWsDto = new LanguageWsDto();
    Language languageData = null;
    List<LanguageDto> languageDtos = request.getLanguageDtoList();
    List<Language> languageList = new ArrayList<>();
    LanguageDto languageDto = new LanguageDto();
    for (LanguageDto languageDto1 : languageDtos) {
        if (languageDto1.getRecordId() != null) {
            languageData = languageRepository.findByRecordId(languageDto1.getRecordId());
            modelMapper.map(languageDto1, languageData);
            languageRepository.save(languageData);
        } else {
            if (baseService.validateIdentifier(EntityConstants.LANGUAGE, languageDto1.getIdentifier()) != null) {
                request.setSuccess(false);
                //request.setMessage("Identifier already present");
                return request;
            }

            languageData = modelMapper.map(languageDto, Language.class);
        }
//            baseService.populateCommonData(kycData);
//            kycData.setCreator(coreService.getCurrentUser().getCreator());
        languageRepository.save(languageData);
        languageData.setLastModified(new Date());
        if (languageData.getRecordId() == null) {
            languageData.setRecordId(String.valueOf(languageData.getId().getTimestamp()));
        }
        languageRepository.save(languageData);
        languageList.add(languageData);
        languageWsDto.setMessage("MatchType was updated successfully");
        languageWsDto.setBaseUrl(ADMIN_LANGUAGE);
    }
    languageWsDto.setLanguageDtoList(modelMapper.map(languageList, List.class));
    return languageWsDto;
}
}
