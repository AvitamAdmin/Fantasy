package com.avitam.fantasy11.api.service.impl;

import com.avitam.fantasy11.api.dto.MatchesDto;
import com.avitam.fantasy11.api.service.MatchesService;
import com.avitam.fantasy11.core.service.CoreService;
import com.avitam.fantasy11.model.Matches;
import com.avitam.fantasy11.model.MatchesRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class MatchesServiceImpl implements MatchesService {

    @Autowired
    private MatchesRepository matchesRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CoreService coreService;
    public static final String ADMIN_MATCHES = "/admin/matches";
    public static final String ADMIN_CLOSEDMATCHES = "/admin/closedMatches";
    public static final String ADMIN_LIVEMATCHES = "/admin/liveMatches";
    public static final String ADMIN_UPCOMINGMATCHES = "/admin/upcomingMatches";


    @Override
    public Matches findByRecordId(String recordId) {
        return matchesRepository.findByRecordId(recordId);
    }

    @Override
    public void deleteByRecordId(String recordId) {
        matchesRepository.deleteByRecordId(recordId);
    }

    @Override
    public void updateByRecordId(String recordId) {
        Matches matches=matchesRepository.findByRecordId(recordId);
        if(matches !=null){
            matchesRepository.save(matches);
        }
    }

    @Override
    public MatchesDto handleEdit(MatchesDto request,int flag) {
        MatchesDto matchesDto = new MatchesDto();
        Matches matches = null;
        if(request.getRecordId()!=null){
            Matches requestData = request.getMatches();
            matches = matchesRepository.findByRecordId(request.getRecordId());
            modelMapper.map(requestData, matches);
        }
        else {
            matches=request.getMatches();
            matches.setCreator(coreService.getCurrentUser().getUsername());
            matches.setCreationTime(new Date());
            matchesRepository.save(matches);
        }
        matches.setLastModified(new Date());
        if(request.getRecordId()==null){
            matches.setRecordId(String.valueOf(matches.getId().getTimestamp()));
        }
        matchesRepository.save(matches);
        matchesDto.setMatches(matches);
        if(flag==1) {
            matchesDto.setBaseUrl(ADMIN_MATCHES);
        }if(flag==2){
            matchesDto.setBaseUrl(ADMIN_CLOSEDMATCHES);
        }if(flag==3){
            matchesDto.setBaseUrl(ADMIN_LIVEMATCHES);
        }else{
            matchesDto.setBaseUrl(ADMIN_UPCOMINGMATCHES);
        }
        return matchesDto;

    }
}
