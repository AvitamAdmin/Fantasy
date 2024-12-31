package com.avitam.fantasy11.api.service.impl;

import com.avitam.fantasy11.api.dto.LanguageDto;
import com.avitam.fantasy11.api.dto.LanguageWsDto;
import com.avitam.fantasy11.api.dto.LeaderBoardDto;
import com.avitam.fantasy11.api.dto.LeaderBoardWsDto;
import com.avitam.fantasy11.api.service.BaseService;
import com.avitam.fantasy11.api.service.LeaderBoardService;
import com.avitam.fantasy11.core.service.CoreService;
import com.avitam.fantasy11.model.Language;
import com.avitam.fantasy11.model.LeaderBoard;
import com.avitam.fantasy11.repository.EntityConstants;
import com.avitam.fantasy11.repository.LeaderBoardRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class LeaderBoardServiceImpl implements LeaderBoardService {

    @Autowired
    private LeaderBoardRepository leaderBoardRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CoreService coreService;
    @Autowired
    private BaseService baseService;

    public static final String ADMIN_LEADER_BOARD = "/admin/leaderBoard";

    @Override
    public LeaderBoard findByRecordId(String recordId) {
        return leaderBoardRepository.findByRecordId(recordId);
    }

    @Override
    public void deleteByRecordId(String recordId) {

        leaderBoardRepository.deleteByRecordId(recordId);
    }


    @Override
    public LeaderBoardWsDto handleEdit(LeaderBoardWsDto request) {
        LeaderBoardWsDto leaderBoardWsDto = new LeaderBoardWsDto();
        LeaderBoard leaderBoardData = null;
        List<LeaderBoardDto> leaderBoardDtos = request.getLeaderBoardDtoList();
        List<LeaderBoard> leaderBoardList = new ArrayList<>();
        LeaderBoardDto leaderBoardDto = new LeaderBoardDto();
        for (LeaderBoardDto leaderBoardDto1 : leaderBoardDtos) {
            if (leaderBoardDto1.getRecordId() != null) {
                leaderBoardData = leaderBoardRepository.findByRecordId(leaderBoardDto1.getRecordId());
                modelMapper.map(leaderBoardDto1, leaderBoardData);
                leaderBoardRepository.save(leaderBoardData);
            } else {
                if (baseService.validateIdentifier(EntityConstants.LEADER_BOARD, leaderBoardDto1.getIdentifier()) != null) {
                    request.setSuccess(false);
                    //request.setMessage("Identifier already present");
                    return request;
                }

                leaderBoardData = modelMapper.map(leaderBoardDto, LeaderBoard.class);
            }
            leaderBoardRepository.save(leaderBoardData);
            leaderBoardData.setLastModified(new Date());
            if (leaderBoardData.getRecordId() == null) {
                leaderBoardData.setRecordId(String.valueOf(leaderBoardData.getId().getTimestamp()));
            }
            leaderBoardRepository.save(leaderBoardData);
            leaderBoardList.add(leaderBoardData);
            leaderBoardWsDto.setMessage("LeaderBoard was updated successfully");
            leaderBoardWsDto.setBaseUrl(ADMIN_LEADER_BOARD);

        }
       leaderBoardWsDto.setLeaderBoardDtoList(modelMapper.map(leaderBoardList, List.class));
        return leaderBoardWsDto;
    }

    @Override
    public void updateByRecordId(String recordId) {

        LeaderBoard leaderBoard=leaderBoardRepository.findByRecordId(recordId);
        if(leaderBoard!=null){
            leaderBoardRepository.save(leaderBoard);
        }
    }
}
