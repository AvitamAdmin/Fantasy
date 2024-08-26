package com.avitam.fantasy11.api.service.impl;

import com.avitam.fantasy11.api.service.PlayerService;
import com.avitam.fantasy11.model.Player;
import com.avitam.fantasy11.model.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class PlayerServiceImpl implements PlayerService {

    @Autowired
    private PlayerRepository playerRepository;
    @Override
    public Optional<Player> findByRecordId(String recordId) {
        return playerRepository.findByRecordId(recordId);
    }

    @Override
    public void deleteByRecordId(String recordId) {
        playerRepository.deleteByRecordId(recordId);
    }

    @Override
    public void updateByRecordId(String recordId) {
        Optional<Player> playerOptional=playerRepository.findByRecordId(recordId);
        playerOptional.ifPresent(player -> playerRepository.save(player));
    }
}
