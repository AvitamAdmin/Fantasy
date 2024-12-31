package com.avitam.fantasy11.api.service.impl;

import com.avitam.fantasy11.api.dto.ExtensionDto;
import com.avitam.fantasy11.api.dto.ExtensionWsDto;
import com.avitam.fantasy11.api.dto.SportAPIDto;
import com.avitam.fantasy11.api.dto.SportsAPIWsDto;
import com.avitam.fantasy11.api.service.BaseService;
import com.avitam.fantasy11.api.service.SportAPIService;
import com.avitam.fantasy11.core.service.CoreService;
import com.avitam.fantasy11.model.Extension;
import com.avitam.fantasy11.model.SportsApi;
import com.avitam.fantasy11.repository.EntityConstants;
import com.avitam.fantasy11.repository.SportsApiRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class SportAPIServiceImpl implements SportAPIService {

    @Autowired
    private SportsApiRepository sportsApiRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CoreService coreService;

    @Autowired
    private BaseService baseService;

    public static final String ADMIN_SPORTAPI = "/admin/sportsApi";

    @Override
    public SportsApi findByRecordId(String recordId) {
        return null;
    }

    @Override
    public void deleteByRecordId(String recordId) {

    }

    @Override
    public void updateByRecordId(String recordId) {

    }


    @Override
    public SportsAPIWsDto handleEdit(SportsAPIWsDto request) {
        SportsAPIWsDto sportsAPIWsDto = new SportsAPIWsDto();
        SportsApi sportsAPIData = null;
        List<SportAPIDto> sportsAPIDtos = request.getSportAPIDtoList();
        List<SportsApi> sportsAPIList = new ArrayList<>();
        SportAPIDto sportsAPIDto = new SportAPIDto();
        for (SportAPIDto SportsAPIDto1 : sportsAPIDtos) {
            if (SportsAPIDto1.getRecordId() != null) {
                sportsAPIData = sportsApiRepository.findByRecordId(SportsAPIDto1.getRecordId());
                modelMapper.map(SportsAPIDto1, sportsAPIData);
                sportsApiRepository.save(sportsAPIData);
            } else {
                if (baseService.validateIdentifier(EntityConstants.SPORTAPI, SportsAPIDto1.getIdentifier()) != null) {
                    request.setSuccess(false);
                    return request;
                }

                sportsAPIData = modelMapper.map(sportsAPIDto, SportsApi.class);
            }
            sportsApiRepository.save(sportsAPIData);
            sportsAPIData.setLastModified(new Date());
            if (sportsAPIData.getRecordId() == null) {
                sportsAPIData.setRecordId(String.valueOf(sportsAPIData.getId().getTimestamp()));
            }
            sportsApiRepository.save(sportsAPIData);
            sportsAPIList.add(sportsAPIData);
            sportsAPIWsDto.setMessage("Extension was updated successfully");
            sportsAPIWsDto.setBaseUrl(ADMIN_SPORTAPI);
        }
        sportsAPIWsDto.setSportAPIDtoList(modelMapper.map(sportsAPIList, List.class));
        return sportsAPIWsDto;
    }}

