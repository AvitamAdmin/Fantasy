package com.avitam.fantasy11.api.service;

import com.avitam.fantasy11.api.dto.BannerDto;
import com.avitam.fantasy11.api.dto.BannerWsDto;
import com.avitam.fantasy11.model.Banner;


public interface BannerService {

    Banner findByRecordId(String recordId);

    void deleteByRecordId(String recordId);

    BannerWsDto handleEdit(BannerWsDto bannerWsDto);

    void updateByRecordId(String recordId);

}
