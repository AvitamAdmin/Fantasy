package com.avitam.fantasy11.api.service.impl;

import com.avitam.fantasy11.api.dto.ContestDto;
import com.avitam.fantasy11.api.dto.ContestJoinedDto;
import com.avitam.fantasy11.api.dto.ContestJoinedWsDto;
import com.avitam.fantasy11.api.service.BaseService;
import com.avitam.fantasy11.api.service.ContestJoinedService;
import com.avitam.fantasy11.core.service.CoreService;
import com.avitam.fantasy11.model.Contest;
import com.avitam.fantasy11.model.ContestJoined;
import com.avitam.fantasy11.repository.ContestJoinedRepository;
import com.avitam.fantasy11.repository.EntityConstants;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class ContestJoinedServiceImpl implements ContestJoinedService {

    @Autowired
    private ContestJoinedRepository contestJoinedRepository;
    @Autowired
    private CoreService coreService;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private BaseService baseService;

    private static final String ADMIN_CONTESTJOINED="/admin/contestJoined";

    @Override
    public ContestJoined findByRecordId(String recordId) {

        return contestJoinedRepository.findByRecordId(recordId);
    }

    @Override
    public ContestJoinedWsDto handleEdit(ContestJoinedWsDto request) {
        ContestJoinedWsDto contestJoinedWsDto = new ContestJoinedWsDto();
        ContestJoined contestJoined = null;
        List<ContestJoinedDto> contestJoinedDtos = request.getContestJoinedDtoList();
        List<ContestJoined> contestJoineds = new ArrayList<>();
        for(ContestJoinedDto contestJoinedDto1 : contestJoinedDtos){
            if(contestJoinedDto1.getRecordId() != null){
                contestJoined = contestJoinedRepository.findByRecordId(contestJoinedDto1.getRecordId());
                modelMapper.map(contestJoinedDto1,contestJoined);
                contestJoinedRepository.save(contestJoined);
            }else {
                if(baseService.validateIdentifier(EntityConstants.CONTEST_JOINED,contestJoinedDto1.getIdentifier()) != null){
                    request.setSuccess(false);
                    request.setMessage("Identifier already present");
                    return request;
                }
                contestJoined = modelMapper.map(contestJoinedDto1, ContestJoined.class);
                contestJoinedRepository.save(contestJoined);

            }
            contestJoined.setLastModified(new Date());
            if (contestJoined.getRecordId() == null) {
                contestJoined.setRecordId(String.valueOf(contestJoined.getId().getTimestamp()));
            }
            contestJoinedRepository.save(contestJoined);
            contestJoineds.add(contestJoined);
            contestJoinedWsDto.setMessage("Contest was updated successfully");
            contestJoinedWsDto.setBaseUrl(ADMIN_CONTESTJOINED);
        }
        contestJoinedWsDto.setContestJoinedDtoList(modelMapper.map(contestJoineds, List.class));
        return contestJoinedWsDto;


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
