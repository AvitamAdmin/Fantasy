package com.avitam.fantasy11.api.service.impl;

import com.avitam.fantasy11.api.dto.PlayerDto;
import com.avitam.fantasy11.api.dto.PlayerWsDto;
import com.avitam.fantasy11.api.service.BaseService;
import com.avitam.fantasy11.api.service.PlayerService;
import com.avitam.fantasy11.model.Player;
import com.avitam.fantasy11.repository.EntityConstants;
import com.avitam.fantasy11.repository.PlayerRepository;
import org.bson.types.Binary;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PlayerServiceImpl implements PlayerService {

    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private BaseService baseService;

    private static final String ADMIN_PLAYER = "/admin/player";

    @Override
    public Player findByRecordId(String recordId) {

        return playerRepository.findByRecordId(recordId);
    }

    @Override
    public PlayerWsDto handleEdit(PlayerWsDto request) {
        PlayerWsDto playerWsDto = new PlayerWsDto();
        Player player = null;
        List<PlayerDto> playerDto = request.getPlayerDtoList();
        List<Player> players = new ArrayList<>();
        for (PlayerDto playerDto1 : playerDto) {
            if (playerDto1.getRecordId() != null) {
                player = playerRepository.findByRecordId(playerDto1.getRecordId());
                modelMapper.map(playerDto1, player);
                playerRepository.save(player);
            } else {
                if (baseService.validateIdentifier(EntityConstants.PLAYER, playerDto1.getIdentifier()) != null) {
                    request.setSuccess(false);
                    request.setMessage("Identifier already present");
                    return request;
                }
                player = modelMapper.map(playerDto1, Player.class);
                if (playerDto1.getPlayerImage() != null) {
                    try {
                        player.setPlayerImage(new Binary(playerDto1.getPlayerImage().getBytes()));
                    } catch (IOException e) {
                        e.printStackTrace();
                        request.setMessage("Error processing image file");
                        return request;
                    }
                }
                playerRepository.save(player);
            }

            player.setLastModified(new Date());
            if (playerDto1.getRecordId() == null) {
                player.setRecordId(String.valueOf(player.getId().getTimestamp()));

            }
            playerRepository.save(player);
            players.add(player);
            playerWsDto.setMessage("Player was updated successfully");
            playerWsDto.setBaseUrl(ADMIN_PLAYER);
        }
        playerWsDto.setPlayerDtoList(modelMapper.map(players, List.class));
        return playerWsDto;
    }


    @Override
    public void deleteByRecordId(String recordId) {

        playerRepository.deleteByRecordId(recordId);
    }

    @Override
    public void updateByRecordId(String recordId) {
        Player player = playerRepository.findByRecordId(recordId);
        if (player != null) {
            playerRepository.save(player);
        }
    }
}
