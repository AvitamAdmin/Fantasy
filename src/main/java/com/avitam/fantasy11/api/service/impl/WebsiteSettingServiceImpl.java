package com.avitam.fantasy11.api.service.impl;


import com.avitam.fantasy11.api.dto.WebsiteSettingDto;
import com.avitam.fantasy11.api.dto.WebsiteSettingsWsDto;
import com.avitam.fantasy11.api.service.BaseService;
import com.avitam.fantasy11.api.service.WebsiteSettingService;
import com.avitam.fantasy11.model.WebsiteSetting;
import com.avitam.fantasy11.repository.EntityConstants;
import com.avitam.fantasy11.repository.WebsiteSettingRepository;
import org.bson.types.Binary;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class WebsiteSettingServiceImpl implements WebsiteSettingService {

    public static final String ADMIN_WEBSITESETTINGS = "/admin/website";
    @Autowired
    private WebsiteSettingRepository websiteSettingRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private BaseService baseService;

    @Override
    public WebsiteSettingsWsDto handleEdit(WebsiteSettingsWsDto request) throws IOException {

        WebsiteSetting websiteData = null;
        List<WebsiteSettingDto> websiteSettingDtos = request.getWebsiteSettingDtoList();
        List<WebsiteSetting> websiteSettingList = new ArrayList<>();

        for (WebsiteSettingDto websiteDto1 : websiteSettingDtos) {
            if (websiteDto1.getRecordId() != null) {
                websiteData = websiteSettingRepository.findByRecordId(websiteDto1.getRecordId());
                modelMapper.map(websiteDto1, websiteData);
                websiteData.setLastModified(new Date());
                if (websiteDto1.getLogo() != null) {
                    websiteData.setLogo(new Binary(websiteDto1.getLogo().getBytes()));
                }
                if (websiteDto1.getFavicon() != null) {
                    websiteData.setFavicon(new Binary(websiteDto1.getFavicon().getBytes()));
                }
                websiteSettingRepository.save(websiteData);
                request.setMessage("Data updated Successfully");
            } else {

                if (baseService.validateIdentifier(EntityConstants.WEBSITESETTING, websiteDto1.getIdentifier()) != null) {
                    request.setSuccess(false);
                    request.setMessage("Identifier already present");
                    return request;
                }
                websiteData = modelMapper.map(websiteDto1, WebsiteSetting.class);
                baseService.populateCommonData(websiteData);
                websiteData.setStatus(true);
                if (websiteDto1.getLogo() != null) {
                    websiteData.setLogo(new Binary(websiteDto1.getLogo().getBytes()));

                }
                if (websiteDto1.getFavicon() != null) {
                    websiteData.setFavicon(new Binary(websiteDto1.getFavicon().getBytes()));

                }
                websiteSettingRepository.save(websiteData);
                if (websiteData.getRecordId() == null) {
                    websiteData.setRecordId(String.valueOf(websiteData.getId().getTimestamp()));
                }
                websiteSettingRepository.save(websiteData);
                request.setMessage("Data added successfully");
            }
            websiteSettingList.add(websiteData);
            request.setBaseUrl(ADMIN_WEBSITESETTINGS);

        }
        request.setWebsiteSettingDtoList(modelMapper.map(websiteSettingList, List.class));
        return request;
    }


}
