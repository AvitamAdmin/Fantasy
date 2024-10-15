package com.avitam.fantasy11.api.service.impl;

import com.avitam.fantasy11.api.dto.LanguageDto;
import com.avitam.fantasy11.api.service.BaseService;
import com.avitam.fantasy11.api.service.LanguageService;
import com.avitam.fantasy11.core.service.CoreService;
import com.avitam.fantasy11.model.Language;
import com.avitam.fantasy11.repository.EntityConstants;
import com.avitam.fantasy11.repository.LanguageRepository;
import org.apache.poi.sl.draw.geom.GuideIf;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Date;

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
    public LanguageDto handleEdit(@RequestBody LanguageDto request) {
        LanguageDto languageDto = new LanguageDto();
        Language language = null;
        if(request.getRecordId()!=null){
            Language requestData = request.getLanguage();
            language = languageRepository.findByRecordId(request.getRecordId());
            modelMapper.map(requestData, language);
        }
        else {
            if(baseService.validateIdentifier(EntityConstants.LANGUAGE,request.getLanguage().getIdentifier())!=null)
            {
                request.setSuccess(false);
                request.setMessage("Identifier already present");
                return request;
            }
            language=request.getLanguage();
        }
        baseService.populateCommonData(language);
        languageRepository.save(language);
        if(request.getRecordId()==null){
            language.setRecordId(String.valueOf(language.getId().getTimestamp()));
        }
        languageRepository.save(language);
        languageDto.setLanguage(language);
        languageDto.setBaseUrl(ADMIN_LANGUAGE);
        return languageDto;
    }
}
