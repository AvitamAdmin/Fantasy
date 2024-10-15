package com.avitam.fantasy11.api.service.impl;

import com.avitam.fantasy11.api.dto.TournamentDto;
import com.avitam.fantasy11.api.service.BaseService;
import com.avitam.fantasy11.api.service.TournamentService;
import com.avitam.fantasy11.core.service.CoreService;
import com.avitam.fantasy11.model.Tournament;
import com.avitam.fantasy11.repository.EntityConstants;
import com.avitam.fantasy11.repository.TournamentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

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

    private static final String ADMIN_TOURNAMENT="/admin/tournament";
    @Override
    public Tournament findByRecordId(String recordId) {
        return tournamentRepository.findByRecordId(recordId);
    }

    @Override
    public TournamentDto handleEdit(TournamentDto request) {
        TournamentDto tournamentDto=new TournamentDto();
        Tournament tournament=null;
        if (request.getRecordId()!=null){
            Tournament requestData= request.getTournament();
            tournament=tournamentRepository.findByRecordId(request.getRecordId());
            modelMapper.map(requestData,tournament);
        }else {
            if(baseService.validateIdentifier(EntityConstants.TOURNAMENT,request.getTournament().getIdentifier())!=null)
            {
                request.setSuccess(false);
                request.setMessage("Identifier already present");
                return request;
            }
            tournament=request.getTournament();
        }
        baseService.populateCommonData(tournament);
        tournamentRepository.save(tournament);
        if (request.getRecordId()==null){
            tournament.setRecordId(String.valueOf(tournament.getId().getTimestamp()));
        }
        tournamentRepository.save(tournament);
        tournamentDto.setTournament(tournament);

        return tournamentDto;
    }

    @Override
    public void deleteByRecordId(String recordId) {

        tournamentRepository.deleteByRecordId(recordId);
    }

    @Override
    public void updateByRecordId(String recordId) {
        Tournament tournament=tournamentRepository.findByRecordId(recordId);
        if(tournament!=null){
            tournamentRepository.save(tournament);
    }
 }

}
