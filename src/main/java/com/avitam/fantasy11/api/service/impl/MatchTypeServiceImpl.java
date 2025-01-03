package com.avitam.fantasy11.api.service.impl;

import com.avitam.fantasy11.api.dto.MatchTypeDto;
import com.avitam.fantasy11.api.dto.MatchTypeWsDto;
import com.avitam.fantasy11.api.service.BaseService;
import com.avitam.fantasy11.api.service.MatchTypeService;
import com.avitam.fantasy11.model.MatchType;
import com.avitam.fantasy11.repository.EntityConstants;
import com.avitam.fantasy11.repository.MatchTypeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MatchTypeServiceImpl implements MatchTypeService {

    @Autowired
    private MatchTypeRepository matchTypeRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private BaseService baseService;

    private static final String ADMIN_MATCHTYPE="/admin/matchType";

    @Override
    public MatchType findByRecordId(String recordId) {

        return matchTypeRepository.findByRecordId(recordId);
    }


    @Override
    public MatchTypeWsDto handleEdit(MatchTypeWsDto request) {

        MatchType matchTypeData = null;
        List<MatchTypeDto> matchTypeDtos = request.getMatchTypeDtoList();
        List<MatchType> matchTypeList = new ArrayList<>();

        for (MatchTypeDto matchTypeDto1 : matchTypeDtos) {
            if (matchTypeDto1.getRecordId() != null) {
                matchTypeData = matchTypeRepository.findByRecordId(matchTypeDto1.getRecordId());
                modelMapper.map(matchTypeDto1, matchTypeData);
                matchTypeRepository.save(matchTypeData);
                request.setMessage("Data updated Successfully");
            } else {
                if (baseService.validateIdentifier(EntityConstants.MATCH_TYPE, matchTypeDto1.getIdentifier()) != null) {
                    request.setSuccess(false);
                    request.setMessage("Identifier already present");
                    return request;
                }

                matchTypeData = modelMapper.map(matchTypeDto1, MatchType.class);
            }
            baseService.populateCommonData(matchTypeData);
            matchTypeData.setStatus(true);
            matchTypeRepository.save(matchTypeData);
            if (matchTypeData.getRecordId() == null) {
                matchTypeData.setRecordId(String.valueOf(matchTypeData.getId().getTimestamp()));
            }
            matchTypeRepository.save(matchTypeData);
            matchTypeList.add(matchTypeData);
            request.setMessage("MatchType added successfully");
            request.setBaseUrl(ADMIN_MATCHTYPE);

        }
        request.setMatchTypeDtoList(modelMapper.map(matchTypeList, List.class));
        return request;
    }

    @Override
    public void deleteByRecordId(String recordId) {

        matchTypeRepository.deleteByRecordId(recordId);

    }

    @Override
    public void updateByRecordId(String recordId) {
        MatchType matchType=matchTypeRepository.findByRecordId(recordId);
        if(matchType!=null){

            matchTypeRepository.save(matchType);
        }
    }

}
