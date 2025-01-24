package com.avitam.fantasy11.api.service.impl;

import com.avitam.fantasy11.api.dto.MatchScoreDto;
import com.avitam.fantasy11.api.dto.MatchScoreWsDto;
import com.avitam.fantasy11.api.service.BaseService;
import com.avitam.fantasy11.api.service.MatchScoreService;
import com.avitam.fantasy11.model.MatchScore;
import com.avitam.fantasy11.repository.EntityConstants;
import com.avitam.fantasy11.repository.MatchScoreRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class MatchScoreServiceImpl implements MatchScoreService {
   @Autowired
   private MatchScoreRepository matchScoreRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private BaseService baseService;
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
public MatchScoreWsDto handleEdit(MatchScoreWsDto request) {
    MatchScore matchScoreData = null;
    List<MatchScoreDto> matchScoreDtos = request.getMatchScoreDtoList();
    List<MatchScore> matchScoreList = new ArrayList<>();

    for (MatchScoreDto matchScoreDto1 : matchScoreDtos) {
        if (matchScoreDto1.getRecordId() != null) {
            matchScoreData = matchScoreRepository.findByRecordId(matchScoreDto1.getRecordId());
            modelMapper.map(matchScoreDto1, matchScoreData);
            matchScoreRepository.save(matchScoreData);
            request.setMessage("MatchScore updated successfully");
        } else {
            if (baseService.validateIdentifier(EntityConstants.MATCH_SCORE, matchScoreDto1.getIdentifier()) != null) {
                request.setSuccess(false);
                request.setMessage("Identifier already present");
                return request;
            }

            matchScoreData = modelMapper.map(matchScoreDto1, MatchScore.class);
            request.setMessage("MatchScore added successfully");
        }
        baseService.populateCommonData(matchScoreData);
        matchScoreData.setStatus(true);
        matchScoreRepository.save(matchScoreData);

        if (matchScoreData.getRecordId() == null) {
            matchScoreData.setRecordId(String.valueOf(matchScoreData.getId().getTimestamp()));

        }
        matchScoreRepository.save(matchScoreData);
        matchScoreList.add(matchScoreData);
        request.setBaseUrl(ADMIN_MATCHSCORE);

    }
    request.setMatchScoreDtoList(modelMapper.map(matchScoreList, List.class));
    return request;
}

}
