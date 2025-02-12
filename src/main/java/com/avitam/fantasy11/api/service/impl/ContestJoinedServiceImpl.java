package com.avitam.fantasy11.api.service.impl;

import com.avitam.fantasy11.api.dto.ContestJoinedDto;
import com.avitam.fantasy11.api.dto.ContestJoinedWsDto;
import com.avitam.fantasy11.api.service.BaseService;
import com.avitam.fantasy11.api.service.ContestJoinedService;
import com.avitam.fantasy11.model.ContestJoined;
import com.avitam.fantasy11.repository.ContestJoinedRepository;
import com.avitam.fantasy11.repository.EntityConstants;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ContestJoinedServiceImpl implements ContestJoinedService {

    private static final String ADMIN_CONTESTJOINED = "/admin/contestJoined";
    @Autowired
    private ContestJoinedRepository contestJoinedRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private BaseService baseService;

    @Override
    public ContestJoined findByRecordId(String recordId) {

        return contestJoinedRepository.findByRecordId(recordId);
    }

    @Override
    public ContestJoinedWsDto handleEdit(ContestJoinedWsDto request) {

        ContestJoined contestJoined = null;
        List<ContestJoined> contestJoineds = new ArrayList<>();
        List<ContestJoinedDto> contestJoinedDtos = request.getContestJoinedDtoList();

        for (ContestJoinedDto contestJoinedDto1 : contestJoinedDtos) {
            if (contestJoinedDto1.getRecordId() != null) {
                contestJoined = contestJoinedRepository.findByRecordId(contestJoinedDto1.getRecordId());
                modelMapper.map(contestJoinedDto1, contestJoined);
                contestJoined.setLastModified(new Date());
                contestJoinedRepository.save(contestJoined);
                request.setMessage("Data updated Successfully");
            } else {
                if (baseService.validateIdentifier(EntityConstants.CONTEST_JOINED, contestJoinedDto1.getIdentifier()) != null) {
                    request.setSuccess(false);
                    request.setMessage("Identifier already present");
                    return request;
                }
                contestJoined = modelMapper.map(contestJoinedDto1, ContestJoined.class);
                contestJoined.setCreationTime(new Date());
                contestJoined.setStatus(true);
                contestJoinedRepository.save(contestJoined);
                if (contestJoined.getRecordId() == null) {
                    contestJoined.setRecordId(String.valueOf(contestJoined.getId().getTimestamp()));
                }
                contestJoinedRepository.save(contestJoined);
                request.setMessage("Contest added successfully");
            }
            contestJoineds.add(contestJoined);
        }
        request.setBaseUrl(ADMIN_CONTESTJOINED);
        request.setContestJoinedDtoList(modelMapper.map(contestJoineds, List.class));
        return request;
    }

    @Override
    public void deleteByRecordId(String recordId) {

        contestJoinedRepository.deleteByRecordId(recordId);
    }

    @Override
    public void updateByRecordId(String recordId) {
        ContestJoined contestJoined = contestJoinedRepository.findByRecordId(recordId);
        if (contestJoined != null) {
            contestJoinedRepository.save(contestJoined);
        }
    }

}