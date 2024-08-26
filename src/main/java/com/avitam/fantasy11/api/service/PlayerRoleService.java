package com.avitam.fantasy11.api.service;

import com.avitam.fantasy11.model.PlayerRole;

import java.util.Optional;

public interface PlayerRoleService {

    public Optional<PlayerRole> findByRecordId(String recordId);

    public void deleteByRecordId(String recordId);

    public  void updateByRecordId(String recordId);
}
