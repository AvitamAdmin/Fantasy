package com.avitam.fantasy11.api.service.impl;

import com.avitam.fantasy11.api.dto.PlayerRoleDto;
import com.avitam.fantasy11.api.service.BaseService;
import com.avitam.fantasy11.api.service.PlayerRoleService;
import com.avitam.fantasy11.core.service.CoreService;
import com.avitam.fantasy11.model.PlayerRole;
import com.avitam.fantasy11.repository.EntityConstants;
import com.avitam.fantasy11.repository.PlayerRoleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    private static final String ADMIN_PLAYERROLE="/admin/playerRole";

    @Override
    public PlayerRole findByRecordId(String recordId) {
        return playerRoleRepository.findByRecordId(recordId);
    }

    @Override
    public PlayerRoleDto handleEdit(PlayerRoleDto request) {
        PlayerRoleDto playerRoleDto=new PlayerRoleDto();
        PlayerRole playerRole=null;
        if (request.getRecordId()!=null){
            PlayerRole requestData=request.getPlayerRole();
            playerRole=playerRoleRepository.findByRecordId(request.getRecordId());
            modelMapper.map(requestData,playerRole);
        }else {
            if(baseService.validateIdentifier(EntityConstants.PLAYER_ROLE,request.getPlayerRole().getIdentifier())!=null)
            {
                request.setSuccess(false);
                request.setMessage("Identifier already present");
                return request;
            }
            playerRole=request.getPlayerRole();
        }
        baseService.populateCommonData(playerRole);
        playerRoleRepository.save(playerRole);
        if (request.getRecordId()==null){
            playerRole.setRecordId(String.valueOf(playerRole.getId().getTimestamp()));
        }
        playerRoleRepository.save(playerRole);
        playerRoleDto.setPlayerRole(playerRole);
        playerRoleDto.setBaseUrl(ADMIN_PLAYERROLE);
        return playerRoleDto;
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
