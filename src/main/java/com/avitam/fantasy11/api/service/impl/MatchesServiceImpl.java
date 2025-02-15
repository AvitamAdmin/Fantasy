package com.avitam.fantasy11.api.service.impl;

import com.avitam.fantasy11.api.dto.MatchesDto;
import com.avitam.fantasy11.api.dto.MatchesWsDto;
import com.avitam.fantasy11.api.service.BaseService;
import com.avitam.fantasy11.api.service.MatchesService;
import com.avitam.fantasy11.model.Matches;
import com.avitam.fantasy11.repository.MatchesRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

@Service
public class MatchesServiceImpl implements MatchesService {

    public static final String ADMIN_MATCHES = "/admin/matches";
    @Autowired
    private MatchesRepository matchesRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private BaseService baseService;

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
        Matches matches = matchesRepository.findByRecordId(recordId);
        if (matches != null) {
            matchesRepository.save(matches);
        }
    }

    @Override
    public MatchesWsDto handleEdit(MatchesWsDto request) {
        Matches matches;
        List<MatchesDto> matchesDto = request.getMatchesDtoList();
        List<Matches> matchesList = new ArrayList<>();
        for (MatchesDto matchesDtos : matchesDto) {
            if (matchesDtos.getRecordId() != null) {
                matches = matchesRepository.findByRecordId(matchesDtos.getRecordId());
                modelMapper.map(matchesDtos, matches);
                matchesRepository.save(matches);
                request.setMessage("Data updated Successfully");
            } else {

                matches = modelMapper.map(matchesDtos, Matches.class);
                baseService.populateCommonData(matches);
                matches.setStatus(true);
                matchesRepository.save(matches);
                if (matches.getRecordId() == null) {
                    matches.setRecordId(String.valueOf(matches.getId().getTimestamp()));
                }
                matchesRepository.save(matches);
                request.setMessage("Data added Successfully");
            }
            matchesList.add(matches);
            request.setBaseUrl(ADMIN_MATCHES);

        }
        request.setMatchesDtoList(modelMapper.map(matchesList, List.class));
        return request;


    }
}
