package com.avitam.fantasy11.api.service.impl;

import com.avitam.fantasy11.api.service.MainContestService;
import com.avitam.fantasy11.model.MainContest;
import com.avitam.fantasy11.model.MainContestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import  org.modelmapper.ModelMapper;
import com.avitam.fantasy11.core.service.CoreService;
import com.avitam.fantasy11.api.dto.MainContestDto;
import java.util.Date;
@Service
public class MainContestServiceImpl implements MainContestService {

    @Autowired
    private MainContestRepository mainContestRepository;

    @Autowired
    private CoreService coreService;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public MainContest findByRecordId(String recordId) {
        return mainContestRepository.findByRecordId(recordId);
    }

    @Override
    public MainContestDto handleEdit(MainContestDto request) {
        MainContestDto mainContestDto = new MainContestDto();
        MainContest mainContest = null;
        if (request.getRecordId() != null) {
            MainContest requestData = request.getMainContest();
            mainContest = mainContestRepository.findByRecordId(request.getRecordId());
            modelMapper.map(requestData, mainContest);
        } else {
            mainContest = request.getMainContest();
            mainContest.setCreator(coreService.getCurrentUser().getUsername());
            mainContest.setCreationTime(new Date());
            mainContestRepository.save(mainContest);
        }
        mainContest.setLastModified(new Date());
        if (request.getRecordId()==null){
            mainContest.setRecordId(String.valueOf(mainContest.getId().getTimestamp()));
        }
        mainContestRepository.save(mainContest);
        mainContestDto.setMainContest(mainContest);
        return mainContestDto;
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

