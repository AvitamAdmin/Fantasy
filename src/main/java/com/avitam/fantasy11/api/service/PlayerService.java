package com.avitam.fantasy11.api.service;

import com.avitam.fantasy11.api.dto.PlayerDto;
import com.avitam.fantasy11.model.Player;


public interface PlayerService {

    Player  findByRecordId(String recordId);

    PlayerDto handleEdit(PlayerDto request);

    public void deleteByRecordId(String recordId);

    public  void updateByRecordId(String recordId);
}
