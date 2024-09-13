package com.avitam.fantasy11.api.service.impl;

import com.avitam.fantasy11.api.dto.AddressDto;
import com.avitam.fantasy11.api.dto.MatchesDto;
import com.avitam.fantasy11.api.service.MatchesService;
import com.avitam.fantasy11.core.service.CoreService;
import com.avitam.fantasy11.model.Address;
import com.avitam.fantasy11.model.Matches;
import com.avitam.fantasy11.model.MatchesRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class MatchesServiceImpl implements MatchesService {

    @Autowired
    private MatchesRepository matchesRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CoreService coreService;
    public static final String ADMIN_MATCHES = "/admin/matches";
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
        Matches matchesOptional=matchesRepository.findByRecordId(recordId);
        //matchesOptional.ifPresent(matches -> matchesRepository.save(matches));
    }

    @Override
    public MatchesDto handleEdit(MatchesDto request) {
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
        matchesDto.setBaseUrl(ADMIN_MATCHES);
        return matchesDto;

    }
}
