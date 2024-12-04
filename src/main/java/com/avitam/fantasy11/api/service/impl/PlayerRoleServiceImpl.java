package com.avitam.fantasy11.api.service.impl;

import com.avitam.fantasy11.api.dto.PlayerRoleDto;
import com.avitam.fantasy11.api.dto.PlayerRoleWsDto;
import com.avitam.fantasy11.api.service.BaseService;
import com.avitam.fantasy11.api.service.PlayerRoleService;
import com.avitam.fantasy11.core.service.CoreService;
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
    private CoreService coreService;
    @Autowired
    private BaseService baseService;

    private static final String ADMIN_PLAYERROLE = "/admin/playerRole";

    @Override
    public PlayerRole findByRecordId(String recordId) {
        return playerRoleRepository.findByRecordId(recordId);
    }

    @Override
    public PlayerRoleWsDto handleEdit(PlayerRoleWsDto request) {
        PlayerRoleWsDto playerRoleWsDto = new PlayerRoleWsDto();
        PlayerRole playerRole = null;
        List<PlayerRoleDto> playerRoleDto = request.getPlayerRoleDtoList();
        List<PlayerRole> playerRoles = new ArrayList<>();
        PlayerRoleDto playerRoleDto1 = new PlayerRoleDto();
        for (PlayerRoleDto playerRoleDto2 : playerRoleDto) {
            if (playerRoleDto2 != null) {
                playerRole = playerRoleRepository.findByRecordId(request.getRecordId());
                modelMapper.map(playerRoleDto2, playerRole);
                playerRoleRepository.save(playerRole);
            } else {
                if (baseService.validateIdentifier(EntityConstants.PLAYER_ROLE, playerRoleDto2.getIdentifier()) != null) {
                    request.setSuccess(false);
                    request.setMessage("Identifier already present");
                    return request;
                }
                playerRole = modelMapper.map(playerRoleDto1, PlayerRole.class);
            }
            playerRoleRepository.save(playerRole);
            playerRole.setLastModified(new Date());
            if (playerRoleDto1.getRecordId() == null) {
                playerRole.setRecordId(String.valueOf(playerRole.getId().getTimestamp()));
            }
            playerRoleRepository.save(playerRole);
            playerRoles.add(playerRole);
            playerRoleWsDto.setBaseUrl(ADMIN_PLAYERROLE);
            playerRoleWsDto.setMessage("playerRole was updated successfully");

        }
        playerRoleWsDto.setPlayerRoleDtoList(modelMapper.map(playerRoles,List.class));
        return playerRoleWsDto;
    }





    @Override
    public void deleteByRecordId(String recordId) {
        playerRoleRepository.deleteByRecordId(recordId);

    }

    @Override
    public void updateByRecordId(String recordId) {

        PlayerRole playerRole=playerRoleRepository.findByRecordId(recordId);
        if(playerRole!= null){
            playerRoleRepository.save(playerRole);
        }

    }
}
