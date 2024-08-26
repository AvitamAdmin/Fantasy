package com.avitam.fantasy11.api.service.impl;

import com.avitam.fantasy11.api.service.PlayerRoleService;
import com.avitam.fantasy11.model.PlayerRole;
import com.avitam.fantasy11.model.PlayerRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class PlayerRoleServiceImpl implements PlayerRoleService {

    @Autowired
    private PlayerRoleRepository playerRoleRepository;
    @Override
    public Optional<PlayerRole> findByRecordId(String recordId) {
        return playerRoleRepository.findByRecordId(recordId);
    }

    @Override
    public void deleteByRecordId(String recordId) {
        playerRoleRepository.deleteByRecordId(recordId);

    }

    @Override
    public void updateByRecordId(String recordId) {

        Optional<PlayerRole> playerRoleOptional=playerRoleRepository.findByRecordId(recordId);
        playerRoleOptional.ifPresent(playerRole->playerRoleRepository.save(playerRole));

    }
}
