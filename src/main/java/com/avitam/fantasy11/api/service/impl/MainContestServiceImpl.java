package com.avitam.fantasy11.api.service.impl;

import com.avitam.fantasy11.api.service.BaseService;
import com.avitam.fantasy11.api.service.MainContestService;
import com.avitam.fantasy11.model.MainContest;
import com.avitam.fantasy11.repository.EntityConstants;
import com.avitam.fantasy11.repository.MainContestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;
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

    @Autowired
    private BaseService baseService;

    private static final String ADMIN_MAINCONTEST = "/admin/mainContest";

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
            if(baseService.validateIdentifier(EntityConstants.MAINCONTEST,request.getMainContest().getIdentifier())!=null)
            {
                request.setSuccess(false);
                request.setMessage("Identifier already present");
                return request;
            }
            mainContest = request.getMainContest();
        }
        baseService.populateCommonData(mainContest);
        mainContestRepository.save(mainContest);
        if (request.getRecordId() == null) {
            mainContest.setRecordId(String.valueOf(mainContest.getId().getTimestamp()));
        }
        mainContestRepository.save(mainContest);
        mainContestDto.setMainContest(mainContest);
        mainContestDto.setBaseUrl(ADMIN_MAINCONTEST);
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

