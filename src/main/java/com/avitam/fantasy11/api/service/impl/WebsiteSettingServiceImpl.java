package com.avitam.fantasy11.api.service.impl;


import com.avitam.fantasy11.api.dto.WebsiteSettingDto;
import com.avitam.fantasy11.api.dto.WebsiteSettingsWsDto;
import com.avitam.fantasy11.api.service.BaseService;
import com.avitam.fantasy11.api.service.WebsiteSettingService;
import com.avitam.fantasy11.core.service.CoreService;

import com.avitam.fantasy11.model.WebsiteSetting;
import com.avitam.fantasy11.repository.EntityConstants;
import com.avitam.fantasy11.repository.WebsiteSettingRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class WebsiteSettingServiceImpl implements WebsiteSettingService {

    @Autowired
    private WebsiteSettingRepository websiteSettingRepository;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    private BaseService baseService;
    public static final String ADMIN_WEBSITESETTINGS = "/admin/websiteSettings";

    @Override
    public WebsiteSetting findByRecordId(String recordId) {
        return websiteSettingRepository.findByRecordId(recordId);
    }

    @Override
    public void deleteByRecordId(String recordId) {

        websiteSettingRepository.deleteByRecordId(recordId);
    }

    @Override
    public WebsiteSettingsWsDto handleEdit(WebsiteSettingsWsDto request) {

        WebsiteSetting websiteSettingData = null;
        List<WebsiteSettingDto> websiteSettingDtos = request.getWebsiteSettingDtoList();
        List<WebsiteSetting> websiteSettingList = new ArrayList<>();

        for (WebsiteSettingDto websiteSettingDto1 : websiteSettingDtos) {
            if (websiteSettingDto1.getRecordId() != null) {
                websiteSettingData = websiteSettingRepository.findByRecordId(websiteSettingDto1.getRecordId());
                modelMapper.map(websiteSettingDto1, websiteSettingData);
                websiteSettingRepository.save(websiteSettingData);
                request.setMessage("Data updated Successfully");
            } else {
                if (baseService.validateIdentifier(EntityConstants.WEBSITESETTING, websiteSettingDto1.getIdentifier()) != null) {
                    request.setSuccess(false);
                    request.setMessage("already present");
                    return request;
                }

                websiteSettingData = modelMapper.map(websiteSettingDto1, WebsiteSetting.class);
            }
            baseService.populateCommonData(websiteSettingData);
            websiteSettingData.setStatus(true);
            websiteSettingRepository.save(websiteSettingData);
            if (websiteSettingData.getRecordId() == null) {
                websiteSettingData.setRecordId(String.valueOf(websiteSettingData.getId().getTimestamp()));
            }
            websiteSettingRepository.save(websiteSettingData);
            websiteSettingList.add(websiteSettingData);
            request.setMessage("Data added successfully");
            request.setBaseUrl(ADMIN_WEBSITESETTINGS);

        }
        request.setWebsiteSettingDtoList(modelMapper.map(websiteSettingList, List.class));
        return request;
    }

    @Override
    public void updateByRecordId(String recordId) {
        WebsiteSetting website = websiteSettingRepository.findByRecordId(recordId);
        if (website != null) {
            websiteSettingRepository.save(website);
        }
    }
}
