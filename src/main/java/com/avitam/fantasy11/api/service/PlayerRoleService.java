package com.avitam.fantasy11.api.service;

import com.avitam.fantasy11.api.dto.PlayerRoleWsDto;
import com.avitam.fantasy11.model.PlayerRole;


public interface PlayerRoleService {

    PlayerRole findByRecordId(String recordId);

    PlayerRoleWsDto handleEdit(PlayerRoleWsDto request);

    void deleteByRecordId(String recordId);

    void updateByRecordId(String recordId);
}
