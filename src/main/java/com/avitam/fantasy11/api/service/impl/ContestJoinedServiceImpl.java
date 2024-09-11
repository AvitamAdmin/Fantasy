package com.avitam.fantasy11.api.service.impl;

import com.avitam.fantasy11.api.dto.ContestJoinedDto;
import com.avitam.fantasy11.api.service.ContestJoinedService;
import com.avitam.fantasy11.core.service.CoreService;
import com.avitam.fantasy11.model.ContestJoined;
import com.avitam.fantasy11.model.ContestJoinedRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ContestJoinedServiceImpl implements ContestJoinedService {

    @Autowired
    private ContestJoinedRepository contestJoinedRepository;
    @Autowired
    private CoreService coreService;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ContestJoined findByRecordId(String recordId) {
        return contestJoinedRepository.findByRecordId(recordId);
    }

    @Override
    public ContestJoinedDto handleEdit(ContestJoinedDto request) {

        ContestJoinedDto contestJoinedDto=new ContestJoinedDto();
        ContestJoined contestJoined=null;
        if (request.getRecordId()!=null){
            ContestJoined requestData=request.getContestJoined();
            contestJoined=contestJoinedRepository.findByRecordId(request.getRecordId());
            modelMapper.map(requestData,contestJoined);
        }else {
            contestJoined=request.getContestJoined();
            contestJoined.setCreator(coreService.getCurrentUser().getUsername());
            contestJoined.setCreationTime(new Date());
            contestJoinedRepository.save(contestJoined);
        }
        contestJoined.setLastModified(new Date());
        if (request.getRecordId()==null){
            contestJoined.setRecordId(String.valueOf(contestJoined.getId().getTimestamp()));
        }
        contestJoinedRepository.save(contestJoined);
        contestJoinedDto.setContestJoined(contestJoined);
        return contestJoinedDto;
    }

    @Override
    public void deleteByRecordId(String recordId) {

        contestJoinedRepository.deleteByRecordId(recordId);
    }

    @Override
    public void updateByRecordId(String recordId) {

        ContestJoined contestJoined =contestJoinedRepository.findByRecordId(recordId);

        if(contestJoined !=null){
            contestJoinedRepository.save(contestJoined);
        }


    }


}
