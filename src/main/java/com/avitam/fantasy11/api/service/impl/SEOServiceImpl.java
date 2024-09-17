package com.avitam.fantasy11.api.service.impl;

import com.avitam.fantasy11.api.dto.SEODto;
import com.avitam.fantasy11.api.service.SEOService;
import com.avitam.fantasy11.core.service.CoreService;
import com.avitam.fantasy11.model.Language;
import com.avitam.fantasy11.model.SEO;
import com.avitam.fantasy11.model.SEORepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class SEOServiceImpl implements SEOService {

    public static final String ADMIN_SEO = "/admin/seo";

    @Autowired
    private SEORepository seoRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CoreService coreService;

    @Override
    public SEO findByRecordId(String recordId) {
        return null;
    }

    @Override
    public void deleteByRecordId(String recordId) {

    }

    @Override
    public void updateByRecordId(String recordId) {
        SEO seoOptional=seoRepository.findByRecordId(recordId);
        if(seoOptional!=null)
        {
            seoRepository.save((seoOptional));
        }

    }

    @Override
    public SEODto handleEdit(SEODto request) {
        SEODto seoDto = new SEODto();
        SEO seo = null;
        if(request.getRecordId()!=null){
            SEO requestData = request.getSeo();
            seo = seoRepository.findByRecordId(request.getRecordId());
            modelMapper.map(requestData, seo);
        }
        else {
            seo=request.getSeo();
            seo.setCreator(coreService.getCurrentUser().getUsername());
            seo.setCreationTime(new Date());
            seoRepository.save(seo);
        }
        seo.setLastModified(new Date());
        if(request.getRecordId()==null){
            seo.setRecordId(String.valueOf(seo.getId().getTimestamp()));
        }
        seoRepository.save(seo);
        seoDto.setSeo(seo);
        seoDto.setBaseUrl(ADMIN_SEO);
        return seoDto;
    }
}
