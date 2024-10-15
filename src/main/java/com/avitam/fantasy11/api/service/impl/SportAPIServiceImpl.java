package com.avitam.fantasy11.api.service.impl;

import com.avitam.fantasy11.api.dto.SportAPIDto;
import com.avitam.fantasy11.api.service.BaseService;
import com.avitam.fantasy11.api.service.SportAPIService;
import com.avitam.fantasy11.core.service.CoreService;
import com.avitam.fantasy11.model.SportsApi;
import com.avitam.fantasy11.repository.EntityConstants;
import com.avitam.fantasy11.repository.SportsApiRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

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
    public SportAPIDto handleEdit(SportAPIDto request) {
        SportAPIDto sportAPIDto = new SportAPIDto();
        SportsApi sportsApi = null;
        if(request.getRecordId()!=null){
            SportsApi requestData = request.getSportAPI();
            sportsApi = sportsApiRepository.findByRecordId(request.getRecordId());
            modelMapper.map(requestData, sportsApi);
        }
        else {
            if(baseService.validateIdentifier(EntityConstants.SPORTAPI,request.getSportAPI().getIdentifier())!=null)
            {
                request.setSuccess(false);
                request.setMessage("Identifier already present");
                return request;
            }
            sportsApi=request.getSportAPI();
        }
        baseService.populateCommonData(sportsApi);
        sportsApiRepository.save(sportsApi);
        if(request.getRecordId()==null){
            sportsApi.setRecordId(String.valueOf(sportsApi.getId().getTimestamp()));
        }
        sportsApiRepository.save(sportsApi);
        sportAPIDto.setSportAPI(sportsApi);
        sportAPIDto.setBaseUrl(ADMIN_SPORTAPI);
        return sportAPIDto;
    }
}
