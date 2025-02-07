package com.avitam.fantasy11.api.service.impl;

import com.avitam.fantasy11.api.dto.PlayerRoleDto;
import com.avitam.fantasy11.api.dto.PlayerRoleWsDto;
import com.avitam.fantasy11.api.service.BaseService;
import com.avitam.fantasy11.api.service.PlayerRoleService;
import com.avitam.fantasy11.model.PlayerRole;
import com.avitam.fantasy11.repository.EntityConstants;
import com.avitam.fantasy11.repository.PlayerRoleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PlayerRoleServiceImpl implements PlayerRoleService {

    @Autowired
    private PlayerRoleRepository playerRoleRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private BaseService baseService;

    private static final String ADMIN_PLAYERROLE = "/admin/playerRole";

    @Override
    public PlayerRole findByRecordId(String recordId) {
        return playerRoleRepository.findByRecordId(recordId);
    }

    @Override
    public PlayerRoleWsDto handleEdit(PlayerRoleWsDto request) {
        PlayerRole playerRole = null;
        List<PlayerRoleDto> playerRoleDto = request.getPlayerRoleDtoList();
        List<PlayerRole> playerRoles = new ArrayList<>();
        for (PlayerRoleDto playerRoleDto1 : playerRoleDto) {
            if (playerRoleDto1.getRecordId() != null) {
                playerRole = playerRoleRepository.findByRecordId(playerRoleDto1.getRecordId());
                modelMapper.map(playerRoleDto1, playerRole);
                playerRole.setLastModified(new Date());
                playerRoleRepository.save(playerRole);
                request.setMessage("Data updated Successfully");
            } else {
                if (baseService.validateIdentifier(EntityConstants.PLAYER_ROLE, playerRoleDto1.getIdentifier()) != null) {
                    request.setSuccess(false);
                    request.setMessage("Identifier already present");
                    return request;
                }
                playerRole = modelMapper.map(playerRoleDto1, PlayerRole.class);
                baseService.populateCommonData(playerRole);
                playerRole.setStatus(true);
                playerRoleRepository.save(playerRole);
                if (playerRole.getRecordId() == null) {
                    playerRole.setRecordId(String.valueOf(playerRole.getId().getTimestamp()));
                }
                playerRoleRepository.save(playerRole);
                request.setMessage("Data added successfully");
            }
            playerRoles.add(playerRole);
            request.setBaseUrl(ADMIN_PLAYERROLE);
        }
        request.setPlayerRoleDtoList(modelMapper.map(playerRoles, List.class));
        return request;
    }


    @Override
    public void deleteByRecordId(String recordId) {
        playerRoleRepository.deleteByRecordId(recordId);

    }

    @Override
    public void updateByRecordId(String recordId) {

        PlayerRole playerRole = playerRoleRepository.findByRecordId(recordId);
        if (playerRole != null) {
            playerRoleRepository.save(playerRole);
        }

    }
}
