package com.avitam.fantasy11.api.service.impl;

import com.avitam.fantasy11.api.dto.ContestDto;
import com.avitam.fantasy11.api.service.ContestService;
import com.avitam.fantasy11.core.service.CoreService;
import com.avitam.fantasy11.model.Contest;
import com.avitam.fantasy11.model.ContestRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
@Service
public class ContestServiceImpl implements ContestService {

    @Autowired
    private ContestRepository contestRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CoreService coreService;

    @Override
    public Contest findByRecordId(String recordId) {

        return contestRepository.findByRecordId(recordId);
    }

    @Override
    public ContestDto handleEdit(ContestDto request) {
        ContestDto contestDto=new ContestDto();
        Contest contest=null;
        if (request.getRecordId()!=null){
            Contest requestData=request.getContest();
            contest=contestRepository.findByRecordId(request.getRecordId());
            modelMapper.map(requestData,contest);
        }else {
            contest=request.getContest();
            contest.setCreator(coreService.getCurrentUser().getUsername());
            contest.setCreationTime(new Date());
            contestRepository.save(contest);
        }
        contest.setLastModified(new Date());
        if (request.getRecordId()==null){
            contest.setRecordId(String.valueOf(contest.getId().getTimestamp()));
        }
        contestRepository.save(contest);
        contestDto.setContest(contest);
        return contestDto;
    }

    @Override
    public void deleteByRecordId(String recordId) {

        contestRepository.deleteByRecordId(recordId);
    }

    @Override
    public void updateByRecordId(String recordId) {
       Contest contest=contestRepository.findByRecordId(recordId);
       if(contest!=null) {

           contestRepository.save(contest);
       }
    }

}
