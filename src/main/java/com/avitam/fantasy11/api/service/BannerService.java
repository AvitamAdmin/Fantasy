package com.avitam.fantasy11.api.service;

import com.avitam.fantasy11.api.dto.BannerDto;
import com.avitam.fantasy11.model.Banner;


public interface BannerService {

    Banner findByRecordId(String recordId);

    void deleteByRecordId(String recordId);

    BannerDto handleEdit(BannerDto request);

    void updateByRecordId(String recordId);

}
