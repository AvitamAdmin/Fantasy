package com.avitam.fantasy11.api.service.impl;

import com.avitam.fantasy11.api.dto.MainContestDto;
import com.avitam.fantasy11.api.dto.MainContestWsDto;
import com.avitam.fantasy11.api.service.BaseService;
import com.avitam.fantasy11.api.service.MainContestService;
import com.avitam.fantasy11.core.service.CoreService;
import com.avitam.fantasy11.model.MainContest;
import com.avitam.fantasy11.repository.EntityConstants;
import com.avitam.fantasy11.repository.MainContestRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class MainContestServiceImpl implements MainContestService {

    @Autowired
    private MainContestRepository mainContestRepository;

    @Autowired
    private CoreService coreService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private BaseService baseService;

    private static final String ADMIN_MAINCONTEST = "/admin/mainContest";

    @Override
    public MainContest findByRecordId(String recordId) {
        return mainContestRepository.findByRecordId(recordId);
    }

    @Override
    public MainContestWsDto handleEdit(MainContestWsDto request) {
        MainContestWsDto mainContestWsDto = new MainContestWsDto();
        MainContest mainContestData = null;
        List<MainContestDto> mainContestDtos = request.getMainContestDtoList();
        List<MainContest> mainContestList = new ArrayList<>();
        MainContestDto mainContestDto = new MainContestDto();
        for (MainContestDto mainContestDto1 : mainContestDtos) {
            if (mainContestDto1.getRecordId() != null) {
                mainContestData = mainContestRepository.findByRecordId(mainContestDto1.getRecordId());
                modelMapper.map(mainContestDto1, mainContestData);
                mainContestRepository.save(mainContestData);
                request.setMessage("Data updated Successfully");
            } else {
                if (baseService.validateIdentifier(EntityConstants.MAINCONTEST, mainContestDto1.getIdentifier()) != null) {
                    request.setSuccess(false);
                    request.setMessage("Identifier already present");
                    return request;
                }

                mainContestData = modelMapper.map(mainContestDto, MainContest.class);
            }
            mainContestRepository.save(mainContestData);
            mainContestData.setLastModified(new Date());
            if (mainContestData.getRecordId() == null) {
                mainContestData.setRecordId(String.valueOf(mainContestData.getId().getTimestamp()));
            }
            mainContestRepository.save(mainContestData);
            mainContestList.add(mainContestData);
            mainContestWsDto.setMessage("Data added successfully");
            mainContestWsDto.setBaseUrl(ADMIN_MAINCONTEST);

        }
        mainContestWsDto.setMainContestDtoList(modelMapper.map(mainContestList, List.class));
        return mainContestWsDto;
    }


    @Override
    public void deleteByRecordId(String recordId) {
        mainContestRepository.deleteByRecordId(recordId);
    }

    @Override
    public void updateByRecordId(String recordId) {
        MainContest mainContest = mainContestRepository.findByRecordId(recordId);
        if (mainContest != null) {
            mainContestRepository.save(mainContest);
        }
    }


}

