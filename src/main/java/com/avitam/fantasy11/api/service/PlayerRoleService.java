package com.avitam.fantasy11.api.service;

import com.avitam.fantasy11.api.dto.PlayerRoleDto;
import com.avitam.fantasy11.model.PlayerRole;


public interface PlayerRoleService {

    public PlayerRole findByRecordId(String recordId);

    PlayerRoleDto handleEdit(PlayerRoleDto request);

    public void deleteByRecordId(String recordId);

    public  void updateByRecordId(String recordId);
}
