package com.avitam.fantasy11.api.service.impl;

import com.avitam.fantasy11.api.dto.MatchScoreDto;
import com.avitam.fantasy11.api.dto.RoleDto;
import com.avitam.fantasy11.api.service.MatchScoreService;
import com.avitam.fantasy11.core.service.CoreService;
import com.avitam.fantasy11.model.MatchScore;
import com.avitam.fantasy11.model.MatchScoreRepository;
import com.avitam.fantasy11.model.Role;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class MatchScoreServiceImpl implements MatchScoreService {
   @Autowired
   private MatchScoreRepository matchScoreRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CoreService coreService;
    @Override
    public MatchScore findByRecordId(String recordId) {
        return matchScoreRepository.findByRecordId(recordId);
    }
    public static final String ADMIN_MATCHSCORE = "/admin/matchScore";
    @Override
    public void deleteByRecordId(String recordId) {
        matchScoreRepository.deleteByRecordId(recordId);
    }

    @Override
    public void updateByRecordId(String recordId) {
        MatchScore matchScoreOptional=matchScoreRepository.findByRecordId(recordId);
        if (matchScoreOptional != null) {
            matchScoreRepository.save(matchScoreOptional);
        }
    }

    @Override
    public MatchScoreDto handleEdit(MatchScoreDto request) {
        {
            MatchScoreDto matchScoreDto = new MatchScoreDto();
            MatchScore matchScore=null;
            if (request.getRecordId()!=null) {
                MatchScore requestData=request.getMatchScore();
                matchScore=matchScoreRepository.findByRecordId(request.getRecordId());
                modelMapper.map(requestData,matchScore);
            }else {
                matchScore=request.getMatchScore();
                matchScore.setCreator(coreService.getCurrentUser().getUsername());
                matchScore.setCreationTime(new Date());
                matchScoreRepository.save(matchScore);
            }
            matchScore.setLastModified(new Date());
            if (request.getRecordId()==null){
                matchScore.setRecordId(String.valueOf(matchScore.getId().getTimestamp()));
            }
            matchScoreRepository.save(matchScore);
            matchScoreDto.setMatchScore(matchScore);
            matchScoreDto.setBaseUrl(ADMIN_MATCHSCORE);
            return matchScoreDto;
        }

    }
}
