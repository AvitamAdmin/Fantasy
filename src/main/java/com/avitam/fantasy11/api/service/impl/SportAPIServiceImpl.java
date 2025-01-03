package com.avitam.fantasy11.api.service.impl;

import com.avitam.fantasy11.api.dto.SportAPIDto;
import com.avitam.fantasy11.api.dto.SportsAPIWsDto;
import com.avitam.fantasy11.api.service.BaseService;
import com.avitam.fantasy11.api.service.SportAPIService;
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
    private BaseService baseService;

    public static final String ADMIN_SPORTAPI = "/admin/sportsApi";

    @Override
    public SportsApi findByRecordId(String recordId) {
        return sportsApiRepository.findByRecordId(recordId);
    }

    @Override
    public void deleteByRecordId(String recordId) {

    }

    @Override
    public void updateByRecordId(String recordId) {

    }


    @Override
    public SportsAPIWsDto handleEdit(SportsAPIWsDto request) {
        SportsApi sportsApiData = null;
        List<SportAPIDto> sportsAPIDtos = request.getSportAPIDtoList();
        List<SportsApi> sportsAPIList = new ArrayList<>();

        for (SportAPIDto sportsApiDto1 : sportsAPIDtos) {
            if (sportsApiDto1.getRecordId() != null) {
                sportsApiData = sportsApiRepository.findByRecordId(sportsApiDto1.getRecordId());
                modelMapper.map(sportsApiDto1, sportsApiData);
                sportsApiRepository.save(sportsApiData);
                request.setMessage("Data updated Successfully");
            } else {
                if (baseService.validateIdentifier(EntityConstants.SPORTAPI, sportsApiDto1.getIdentifier()) != null) {
                    request.setSuccess(false);
                    request.setMessage("Identifier already present");
                    return request;
                }
                sportsApiData = modelMapper.map(sportsApiDto1, SportsApi.class);
            }
            baseService.populateCommonData(sportsApiData);
            sportsApiData.setStatus(true);
            sportsApiRepository.save(sportsApiData);
            if (sportsApiData.getRecordId() == null) {
                sportsApiData.setRecordId(String.valueOf(sportsApiData.getId().getTimestamp()));
            }
            sportsApiRepository.save(sportsApiData);
            request.setMessage("Data added successfully");
            sportsAPIList.add(sportsApiData);
        }
        request.setBaseUrl(ADMIN_SPORTAPI);
        request.setSportAPIDtoList(modelMapper.map(sportsAPIList, List.class));
        return request;
    }
}