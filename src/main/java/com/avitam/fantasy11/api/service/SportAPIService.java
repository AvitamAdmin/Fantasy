package com.avitam.fantasy11.api.service;

import com.avitam.fantasy11.api.dto.SportsAPIWsDto;
import com.avitam.fantasy11.model.SportsApi;

public interface SportAPIService {

    SportsApi findByRecordId(String recordId);

    void deleteByRecordId(String recordId);

    void updateByRecordId(String recordId);

    SportsAPIWsDto handleEdit(SportsAPIWsDto request);
}
