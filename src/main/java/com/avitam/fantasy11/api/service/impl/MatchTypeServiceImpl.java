package com.avitam.fantasy11.api.service.impl;

import com.avitam.fantasy11.api.dto.MatchTypeDto;
import com.avitam.fantasy11.api.service.BaseService;
import com.avitam.fantasy11.api.service.MatchTypeService;
import com.avitam.fantasy11.core.service.CoreService;
import com.avitam.fantasy11.model.MatchType;
import com.avitam.fantasy11.repository.EntityConstants;
import com.avitam.fantasy11.repository.MatchTypeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class MatchTypeServiceImpl implements MatchTypeService {

    @Autowired
    private MatchTypeRepository matchTypeRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CoreService coreService;
    @Autowired
    private BaseService baseService;

    private static final String ADMIN_MATCHTYPE="/admin/matchType";

    @Override
    public MatchType findByRecordId(String recordId) {

        return matchTypeRepository.findByRecordId(recordId);
    }

    @Override
    public MatchTypeDto handleEdit(MatchTypeDto request) {
        MatchTypeDto matchTypeDto=new MatchTypeDto();
        MatchType matchType=null;
        if (request.getRecordId()!=null){
            MatchType requestData=request.getMatchType();
            matchType=matchTypeRepository.findByRecordId(request.getRecordId());
            modelMapper.map(requestData,matchType);
        }else {
            if(baseService.validateIdentifier(EntityConstants.MATCH_TYPE,request.getMatchType().getIdentifier())!=null)
            {
                request.setSuccess(false);
                request.setMessage("Identifier already present");
                return request;
            }
            matchType=request.getMatchType();
        }
        baseService.populateCommonData(matchType);
        matchTypeRepository.save(matchType);
        if (request.getRecordId()==null){
            matchType.setRecordId(String.valueOf(matchType.getId().getTimestamp()));
        }
        matchTypeRepository.save(matchType);
        matchTypeDto.setMatchType(matchType);
        matchTypeDto.setBaseUrl(ADMIN_MATCHTYPE);
        return matchTypeDto;
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
