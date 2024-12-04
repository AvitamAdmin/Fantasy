package com.avitam.fantasy11.api.service.impl;

import com.avitam.fantasy11.api.dto.MatchesDto;
import com.avitam.fantasy11.api.dto.MatchesWsDto;
import com.avitam.fantasy11.api.service.BaseService;
import com.avitam.fantasy11.api.service.MatchesService;
import com.avitam.fantasy11.core.service.CoreService;
import com.avitam.fantasy11.model.Matches;
import com.avitam.fantasy11.repository.EntityConstants;
import com.avitam.fantasy11.repository.MatchesRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class MatchesServiceImpl implements MatchesService {

    @Autowired
    private MatchesRepository matchesRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CoreService coreService;
    @Autowired
    private BaseService baseService;
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
        Matches matches = matchesRepository.findByRecordId(recordId);
        if (matches != null) {
            matchesRepository.save(matches);
        }
    }

    @Override
    public MatchesWsDto handleEdit(MatchesWsDto request) {
        Matches matches = new Matches();
        List<MatchesDto> matchesDto = request.getMatchesDtoList();
        List<Matches> matchesList = new ArrayList<>();
        for (MatchesDto matchesDtos : matchesDto) {
            if (matchesDtos.getRecordId() != null) {
                matches = matchesRepository.findByRecordId(matchesDtos.getRecordId());
                modelMapper.map(matchesDtos, matches);
                matchesRepository.save(matches);
            } else {
                matches = modelMapper.map(matchesDtos, Matches.class);
                eventStatus(matches, request);
                matchesRepository.save(matches);
            }
            if (request.getRecordId() == null) {
                matches.setRecordId(String.valueOf(matches.getId().getTimestamp()));
            }
            matchesRepository.save(matches);
            matchesList.add(matches);
            request.setBaseUrl(ADMIN_MATCHES);

        }
        request.setMatchesDtoList(modelMapper.map(matchesList, List.class));
        return request;


    }

    public void eventStatus(Matches matches, MatchesWsDto request) {

        for (MatchesDto matchesDto : request.getMatchesDtoList()) {
            LocalDateTime startDateAndTime = LocalDateTime.parse(matchesDto.getStartDateAndTime());
            LocalDateTime endDateAndTime = LocalDateTime.parse(matchesDto.getEndDateAndTime());
            LocalDateTime currentDateAndTime = LocalDateTime.now();

            if (startDateAndTime.isBefore(currentDateAndTime) && endDateAndTime.isAfter(currentDateAndTime)) {
                matches.setEventStatus("Live");
            } else if (startDateAndTime.isBefore(currentDateAndTime) && endDateAndTime.isBefore(currentDateAndTime)) {
                matches.setEventStatus("Closed");
            } else if (startDateAndTime.isAfter(currentDateAndTime)) {
                matches.setEventStatus("Upcoming");
            }
        }
    }
}

