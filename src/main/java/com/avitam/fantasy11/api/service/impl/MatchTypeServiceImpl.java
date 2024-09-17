package com.avitam.fantasy11.api.service.impl;

import com.avitam.fantasy11.api.dto.MatchTypeDto;
import com.avitam.fantasy11.api.service.MatchTypeService;
import com.avitam.fantasy11.core.service.CoreService;
import com.avitam.fantasy11.model.MatchType;
import com.avitam.fantasy11.model.MatchTypeRepository;
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
            matchType=request.getMatchType();
            matchType.setCreator(coreService.getCurrentUser().getUsername());
            matchType.setCreationTime(new Date());
            matchTypeRepository.save(matchType);
        }
        matchType.setLastModified(new Date());
        if (request.getRecordId()==null){
            matchType.setRecordId(String.valueOf(matchType.getId().getTimestamp()));
        }
        matchTypeRepository.save(matchType);
        matchTypeDto.setMatchType(matchType);
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
