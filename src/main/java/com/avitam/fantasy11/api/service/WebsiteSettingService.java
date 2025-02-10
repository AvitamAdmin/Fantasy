package com.avitam.fantasy11.api.service;

import com.avitam.fantasy11.api.dto.WebsiteSettingsWsDto;
import com.avitam.fantasy11.model.WebsiteSetting;

import java.io.IOException;

public interface WebsiteSettingService {

    WebsiteSettingsWsDto handleEdit(WebsiteSettingsWsDto request) throws IOException;


}
