package com.avitam.fantasy11.api.service;

import com.avitam.fantasy11.api.dto.WebsiteSettingsWsDto;
import com.avitam.fantasy11.model.WebsiteSetting;

public interface WebsiteSettingService {

    WebsiteSetting findByRecordId(String recordId);

    void deleteByRecordId(String recordId) ;

    WebsiteSettingsWsDto handleEdit(WebsiteSettingsWsDto request);

    void updateByRecordId(String recordId);
}
