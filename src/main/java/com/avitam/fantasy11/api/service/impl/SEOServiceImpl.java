package com.avitam.fantasy11.api.service.impl;

import com.avitam.fantasy11.api.dto.SEODto;
import com.avitam.fantasy11.api.dto.SEOWsDto;
import com.avitam.fantasy11.api.service.BaseService;
import com.avitam.fantasy11.api.service.SEOService;
import com.avitam.fantasy11.model.SEO;
import com.avitam.fantasy11.repository.EntityConstants;
import com.avitam.fantasy11.repository.SEORepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SEOServiceImpl implements SEOService {

    public static final String ADMIN_SEO = "/admin/seo";
    @Autowired
    private SEORepository seoRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private BaseService baseService;

    @Override
    public SEO findByRecordId(String recordId) {
        return null;
    }

    @Override
    public void deleteByRecordId(String recordId) {

    }

    @Override
    public void updateByRecordId(String recordId) {
        SEO seoOptional = seoRepository.findByRecordId(recordId);
        if (seoOptional != null) {
            seoRepository.save((seoOptional));
        }

    }

    @Override
    public SEOWsDto handleEdit(SEOWsDto request) {
        SEO seo = null;
        List<SEODto> seoDtos = request.getSeoDtoList();
        List<SEO> seos = new ArrayList<>();

        for (SEODto seoDto1 : seoDtos) {
            if (seoDto1.getRecordId() != null) {
                seo = seoRepository.findByRecordId(seoDto1.getRecordId());
                modelMapper.map(seoDto1, seo);
                seoRepository.save(seo);
                request.setMessage("Data updated Successfully");
            } else {
                if (baseService.validateIdentifier(EntityConstants.SEO, seoDto1.getIdentifier()) != null) {
                    request.setSuccess(false);
                    request.setMessage("identifier already present");
                    return request;
                }
                seo = modelMapper.map(seoDto1, SEO.class);
                seoRepository.save(seo);
            }
            if (seo.getRecordId() == null) {
                seo.setRecordId(String.valueOf(seo.getId().getTimestamp()));
            }
            seoRepository.save(seo);
            request.setMessage("Data added successfully");
            seos.add(seo);
        }
        request.setBaseUrl(ADMIN_SEO);
        request.setSeoDtoList(modelMapper.map(seos, List.class));
        return request;
    }
}
