package com.avitam.fantasy11.api.service.impl;

import com.avitam.fantasy11.api.dto.WebsiteSettingDto;
import com.avitam.fantasy11.api.service.WebsiteSettingService;
import com.avitam.fantasy11.model.WebsiteSetting;
import com.avitam.fantasy11.model.WebsiteSettingRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class WebsiteSettingServiceImpl implements WebsiteSettingService {

    @Autowired
    private WebsiteSettingRepository websiteSettingRepository;
    @Override
    public WebsiteSetting findByRecordId(String recordId) {

        return websiteSettingRepository.findByRecordId(recordId);
    }

    @Override
    public void deleteByRecordId(String recordId) {

        websiteSettingRepository.deleteByRecordId(recordId);
    }

    @Override
    public WebsiteSettingDto handleEdit(WebsiteSettingDto request) {
        return null;
    }

    @Override
    public void updateByRecordId(String recordId) {
        WebsiteSetting website=websiteSettingRepository.findByRecordId(recordId);
        if (website!=null){
            websiteSettingRepository.save(website);
        }
    }
}
