package com.avitam.fantasy11.api.service.impl;

import com.avitam.fantasy11.api.dto.UserWinningsDto;
import com.avitam.fantasy11.api.dto.WebsiteSettingDto;
import com.avitam.fantasy11.api.dto.WebsiteSettingsWsDto;
import com.avitam.fantasy11.api.service.BaseService;
import com.avitam.fantasy11.api.service.WebsiteSettingService;
import com.avitam.fantasy11.core.service.CoreService;
import com.avitam.fantasy11.model.UserWinnings;
import com.avitam.fantasy11.model.WebsiteSetting;
import com.avitam.fantasy11.repository.EntityConstants;
import com.avitam.fantasy11.repository.WebsiteSettingRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WebsiteSettingServiceImpl implements WebsiteSettingService {

    @Autowired
    private WebsiteSettingRepository websiteSettingRepository;

    @Autowired
    ModelMapper modelMapper;
    @Autowired
    private CoreService coreService;
    @Autowired
    private BaseService baseService;
    public static final String ADMIN_WEBSITESETTINGS = "/admin/websiteSettings";
    @Override
    public WebsiteSetting findByRecordId(String recordId)
    {
        return websiteSettingRepository.findByRecordId(recordId);
    }

    @Override
    public void deleteByRecordId(String recordId) {

        websiteSettingRepository.deleteByRecordId(recordId);
    }

    @Override
    public WebsiteSettingsWsDto handleEdit(WebsiteSettingsWsDto request) {
        WebsiteSettingsWsDto websiteSettingsWsDto = new WebsiteSettingsWsDto();
        WebsiteSetting websiteSettingData = null;
        List<WebsiteSettingDto> websiteSettingDtos = request.getWebsiteSettingDtoList();
        List<WebsiteSetting> websiteSettingList = new ArrayList<>();
        WebsiteSettingDto websiteSettingDto = new WebsiteSettingDto();
        for (WebsiteSettingDto websiteSettingDto1 : websiteSettingDtos) {
            if (websiteSettingDto1.getRecordId() != null) {
                websiteSettingData = websiteSettingRepository.findByRecordId(websiteSettingDto1.getRecordId());
                modelMapper.map(websiteSettingDto1, websiteSettingData);
                websiteSettingRepository.save(websiteSettingData);
            } else {
                if (baseService.validateIdentifier(EntityConstants.WEBSITESETTING, websiteSettingDto1.getIdentifier()) != null) {
                    request.setSuccess(false);
                    request.setMessage("already present");
                    return request;
                }

                websiteSettingData = modelMapper.map( websiteSettingDto,  WebsiteSetting.class);
            }
            websiteSettingRepository.save(websiteSettingData);
            websiteSettingData.setLastModified(new Date());
            if (websiteSettingData.getRecordId() == null) {
                websiteSettingData.setRecordId(String.valueOf(websiteSettingData.getId().getTimestamp()));
            }
            websiteSettingRepository.save(websiteSettingData);
            websiteSettingList.add(websiteSettingData);
            websiteSettingsWsDto.setMessage("Image was updated successfully");
            websiteSettingsWsDto.setBaseUrl(ADMIN_WEBSITESETTINGS);

        }
        websiteSettingsWsDto.setWebsiteSettingDtoList(modelMapper.map(websiteSettingList, List.class));
        return websiteSettingsWsDto;
    }

    @Override
    public void updateByRecordId(String recordId) {
        WebsiteSetting website=websiteSettingRepository.findByRecordId(recordId);
        if (website!=null){
            websiteSettingRepository.save(website);
        }
    }
}
