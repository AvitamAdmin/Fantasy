package com.avitam.fantasy11.api.service.impl;

import com.avitam.fantasy11.api.dto.LeaderBoardDto;
import com.avitam.fantasy11.api.service.BaseService;
import com.avitam.fantasy11.api.service.LeaderBoardService;
import com.avitam.fantasy11.core.service.CoreService;
import com.avitam.fantasy11.model.LeaderBoard;
import com.avitam.fantasy11.repository.EntityConstants;
import com.avitam.fantasy11.repository.LeaderBoardRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

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

    @Override
    public LeaderBoard findByRecordId(String recordId) {
        return leaderBoardRepository.findByRecordId(recordId);
    }

    @Override
    public void deleteByRecordId(String recordId) {

        leaderBoardRepository.deleteByRecordId(recordId);
    }

    @Override
    public LeaderBoardDto handleEdit(LeaderBoardDto request) {
        LeaderBoardDto leaderBoardDto=new LeaderBoardDto();
        LeaderBoard leaderBoard=null;
        if (request.getRecordId()!=null){
            LeaderBoard requestData=request.getLeaderBoard();
            leaderBoard=leaderBoardRepository.findByRecordId(request.getRecordId());
            modelMapper.map(requestData,leaderBoard);
        }else {
            if(baseService.validateIdentifier(EntityConstants.LEADER_BOARD,request.getLeaderBoard().getIdentifier())!=null)
            {
                request.setSuccess(false);
                request.setMessage("Identifier already present");
                return request;
            }
            leaderBoard=request.getLeaderBoard();
        }
        baseService.populateCommonData(leaderBoard);
        leaderBoardRepository.save(leaderBoard);
        if (request.getRecordId()==null){
            leaderBoard.setRecordId(String.valueOf(leaderBoard.getId().getTimestamp()));
        }
        leaderBoardRepository.save(leaderBoard);
        leaderBoardDto.setLeaderBoard(leaderBoard);
        return leaderBoardDto;
    }


    @Override
    public void updateByRecordId(String recordId) {

        LeaderBoard leaderBoard=leaderBoardRepository.findByRecordId(recordId);
        if(leaderBoard!=null){
            leaderBoardRepository.save(leaderBoard);
        }
    }
}
