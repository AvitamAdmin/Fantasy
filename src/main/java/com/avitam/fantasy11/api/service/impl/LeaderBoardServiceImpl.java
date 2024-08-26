package com.avitam.fantasy11.api.service.impl;

import com.avitam.fantasy11.api.service.LeaderBoardService;
import com.avitam.fantasy11.model.Address;
import com.avitam.fantasy11.model.LeaderBoard;
import com.avitam.fantasy11.model.LeaderBoardRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class LeaderBoardServiceImpl implements LeaderBoardService {

    @Autowired
    private LeaderBoardRepository leaderBoardRepository;


    @Override
    public Optional<LeaderBoard> findByRecordId(String recordId) {
        return leaderBoardRepository.findByRecordId(recordId);
    }

    @Override
    public void deleteByRecordId(String recordId) {
        leaderBoardRepository.deleteByRecordId(recordId);
    }



    @Override
    public void updateByRecordId(String recordId) {

        Optional<LeaderBoard> leaderBoardOptional=leaderBoardRepository.findByRecordId(recordId);
        leaderBoardOptional.ifPresent(leaderBoard -> leaderBoardRepository.save(leaderBoard));
    }
}
