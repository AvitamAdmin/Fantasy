package com.avitam.fantasy11.api.service;

import com.avitam.fantasy11.api.dto.PlayerWsDto;
import com.avitam.fantasy11.model.Player;


public interface PlayerService {

    Player  findByRecordId(String recordId);

    PlayerWsDto handleEdit(PlayerWsDto request);

    public void deleteByRecordId(String recordId);

    public  void updateByRecordId(String recordId);
}
