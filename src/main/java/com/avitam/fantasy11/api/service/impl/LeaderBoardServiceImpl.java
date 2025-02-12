package com.avitam.fantasy11.api.service.impl;

import com.avitam.fantasy11.api.dto.LeaderBoardDto;
import com.avitam.fantasy11.api.dto.LeaderBoardWsDto;
import com.avitam.fantasy11.api.service.BaseService;
import com.avitam.fantasy11.api.service.LeaderBoardService;
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

    public static final String ADMIN_LEADER_BOARD = "/admin/leaderBoard";
    @Autowired
    private LeaderBoardRepository leaderBoardRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private BaseService baseService;

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

        LeaderBoard leaderBoardData = null;
        List<LeaderBoardDto> leaderBoardDtos = request.getLeaderBoardDtoList();
        List<LeaderBoard> leaderBoardList = new ArrayList<>();

        for (LeaderBoardDto leaderBoardDto1 : leaderBoardDtos) {
            if (leaderBoardDto1.getRecordId() != null) {
                leaderBoardData = leaderBoardRepository.findByRecordId(leaderBoardDto1.getRecordId());
                modelMapper.map(leaderBoardDto1, leaderBoardData);
                leaderBoardData.setLastModified(new Date());
                leaderBoardRepository.save(leaderBoardData);
                request.setMessage("Data updated Successfully");
            } else {
                if (baseService.validateIdentifier(EntityConstants.LEADER_BOARD, leaderBoardDto1.getIdentifier()) != null) {
                    request.setSuccess(false);
                    request.setMessage("Identifier already present");
                    return request;
                }

                leaderBoardData = modelMapper.map(leaderBoardDto1, LeaderBoard.class);

                baseService.populateCommonData(leaderBoardData);
                leaderBoardData.setStatus(true);
                leaderBoardRepository.save(leaderBoardData);
                if (leaderBoardData.getRecordId() == null) {
                    leaderBoardData.setRecordId(String.valueOf(leaderBoardData.getId().getTimestamp()));
                }
                leaderBoardRepository.save(leaderBoardData);
                request.setMessage("LeaderBoard added successfully");
            }
            leaderBoardList.add(leaderBoardData);
        }
        request.setBaseUrl(ADMIN_LEADER_BOARD);
        request.setLeaderBoardDtoList(modelMapper.map(leaderBoardList, List.class));
        return request;
    }

    @Override
    public void updateByRecordId(String recordId) {

        LeaderBoard leaderBoard = leaderBoardRepository.findByRecordId(recordId);
        if (leaderBoard != null) {
            leaderBoardRepository.save(leaderBoard);
        }
    }
}
