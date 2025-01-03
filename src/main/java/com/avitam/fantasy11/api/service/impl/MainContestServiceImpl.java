package com.avitam.fantasy11.api.service.impl;

import com.avitam.fantasy11.api.dto.MainContestDto;
import com.avitam.fantasy11.api.dto.MainContestWsDto;
import com.avitam.fantasy11.api.service.BaseService;
import com.avitam.fantasy11.api.service.MainContestService;
import com.avitam.fantasy11.model.MainContest;
import com.avitam.fantasy11.repository.EntityConstants;
import com.avitam.fantasy11.repository.MainContestRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MainContestServiceImpl implements MainContestService {

    @Autowired
    private MainContestRepository mainContestRepository;
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
        MainContest mainContestData = null;
        List<MainContestDto> mainContestDtos = request.getMainContestDtoList();
        List<MainContest> mainContestList = new ArrayList<>();

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

                mainContestData = modelMapper.map(mainContestDto1, MainContest.class);
            }
            baseService.populateCommonData(mainContestData);
            mainContestData.setStatus(true);
            mainContestRepository.save(mainContestData);
            if (mainContestData.getRecordId() == null) {
                mainContestData.setRecordId(String.valueOf(mainContestData.getId().getTimestamp()));
            }
            mainContestRepository.save(mainContestData);
            mainContestList.add(mainContestData);
            request.setMessage("Data added successfully");
            request.setBaseUrl(ADMIN_MAINCONTEST);

        }
        request.setMainContestDtoList(modelMapper.map(mainContestList, List.class));
        return request;
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

