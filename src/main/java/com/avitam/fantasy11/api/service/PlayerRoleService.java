package com.avitam.fantasy11.api.service;

import com.avitam.fantasy11.api.dto.PlayerRoleWsDto;
import com.avitam.fantasy11.model.PlayerRole;


public interface PlayerRoleService {

    public PlayerRole findByRecordId(String recordId);

    PlayerRoleWsDto handleEdit(PlayerRoleWsDto request);

    public void deleteByRecordId(String recordId);

    public  void updateByRecordId(String recordId);
}
