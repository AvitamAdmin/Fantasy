package com.avitam.fantasy11.api.service.impl;

import com.avitam.fantasy11.api.dto.TournamentDto;
import com.avitam.fantasy11.api.dto.TournamentWsDto;
import com.avitam.fantasy11.api.service.BaseService;
import com.avitam.fantasy11.api.service.TournamentService;
import com.avitam.fantasy11.core.service.CoreService;
import com.avitam.fantasy11.model.Tournament;
import com.avitam.fantasy11.repository.EntityConstants;
import com.avitam.fantasy11.repository.TournamentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TournamentServiceImpl implements TournamentService {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CoreService coreService;
    @Autowired
    private TournamentRepository tournamentRepository;
    @Autowired
    private BaseService baseService;
    private static final String ADMIN_TOURNAMENT = "/admin/tournament";

    @Override
    public Tournament findByRecordId(String recordId) {
        return tournamentRepository.findByRecordId(recordId);
    }

    @Override
    public TournamentWsDto handleEdit(TournamentWsDto request) {
        List<TournamentDto> tournamentDtos = request.getTournamentDtoList();
        List<Tournament> tournaments = new ArrayList<>();
        Tournament tournament = null;
        for (TournamentDto tournamentDto : tournamentDtos) {
            if (request.getRecordId() != null) {
                Tournament requestData = modelMapper.map(tournamentDto, Tournament.class);
                tournament = tournamentRepository.findByRecordId(request.getRecordId());
                modelMapper.map(requestData, tournament);
            } else {
                if (baseService.validateIdentifier(EntityConstants.TOURNAMENT, tournament.getIdentifier()) != null) {
                    request.setSuccess(false);
                    request.setMessage("Identifier already present");
                    return request;
                }
                tournament = modelMapper.map(tournamentDto, Tournament.class);
            }
            baseService.populateCommonData(tournament);
            tournamentRepository.save(tournament);
            if (request.getRecordId() == null) {
                tournament.setRecordId(String.valueOf(tournament.getId().getTimestamp()));
            }
            tournamentRepository.save(tournament);
            tournaments.add(tournament);
            request.setMessage("Data added Successfully");
            request.setBaseUrl(ADMIN_TOURNAMENT);
        }
        request.setTournamentDtoList(modelMapper.map(tournaments, List.class));
        return request;
    }

    @Override
    public void deleteByRecordId(String recordId) {

        tournamentRepository.deleteByRecordId(recordId);
    }

    @Override
    public void updateByRecordId(String recordId) {
        Tournament tournament = tournamentRepository.findByRecordId(recordId);
        if (tournament != null) {
            tournamentRepository.save(tournament);
        }
    }

}
