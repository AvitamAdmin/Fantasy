package com.avitam.fantasy11.api.service.impl;

import com.avitam.fantasy11.api.dto.PlayerDto;
import com.avitam.fantasy11.api.service.BaseService;
import com.avitam.fantasy11.api.service.PlayerService;
import com.avitam.fantasy11.core.service.CoreService;
import com.avitam.fantasy11.model.Player;
import com.avitam.fantasy11.repository.EntityConstants;
import com.avitam.fantasy11.repository.PlayerRepository;
import org.bson.types.Binary;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class PlayerServiceImpl implements PlayerService {

    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CoreService coreService;
    @Autowired
    private BaseService baseService;

    private static final String ADMIN_PLAYER="/admin/player";

    @Override
    public Player findByRecordId(String recordId) {

        return playerRepository.findByRecordId(recordId);
    }

    @Override
    public PlayerDto handleEdit(PlayerDto request) {
        PlayerDto playerDto=new PlayerDto();
        Player player=null;
        if (request.getRecordId()!=null){
            Player requestData=request.getPlayer();
            player=playerRepository.findByRecordId(request.getRecordId());
            modelMapper.map(requestData,player);
        }else {
            if(baseService.validateIdentifier(EntityConstants.PLAYER,request.getPlayer().getIdentifier())!=null)
            {
                request.setSuccess(false);
                request.setMessage("Identifier already present");
                return request;
            }
            player=request.getPlayer();
        }
        if(request.getPlayerImage()!=null && !request.getPlayerImage().isEmpty()) {
            try {
                player.setPlayerImage(new Binary(request.getPlayerImage().getBytes()));
            } catch (IOException e) {
                e.printStackTrace();
                playerDto.setMessage("Error processing image file");
                return playerDto;
            }
        }
        baseService.populateCommonData(player);
        playerRepository.save(player);
        if (request.getRecordId()==null){
            player.setRecordId(String.valueOf(player.getId().getTimestamp()));
        }
        playerRepository.save(player);
        playerDto.setPlayer(player);
        playerDto.setBaseUrl(ADMIN_PLAYER);
        return playerDto;
    }

    @Override
    public void deleteByRecordId(String recordId) {

        playerRepository.deleteByRecordId(recordId);
    }

    @Override
    public void updateByRecordId(String recordId) {
        Player player=playerRepository.findByRecordId(recordId);
        if(player!=null){
            playerRepository.save(player);
        }
    }
}
