package com.avitam.fantasy11.api.service;

import com.avitam.fantasy11.api.dto.SportAPIDto;
import com.avitam.fantasy11.api.dto.SportsAPIWsDto;
import com.avitam.fantasy11.model.SportsApi;

public interface SportAPIService {

    public SportsApi findByRecordId(String recordId);

    public void deleteByRecordId(String recordId);

    public void updateByRecordId(String recordId);

    public SportsAPIWsDto handleEdit(SportsAPIWsDto request);
}
