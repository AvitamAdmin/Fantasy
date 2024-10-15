package com.avitam.fantasy11.repository;

import com.avitam.fantasy11.model.PlayerRole;
import com.avitam.fantasy11.repository.generic.GenericImportRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository("PlayerRoleRepository")
public interface PlayerRoleRepository extends GenericImportRepository<PlayerRole> {
    PlayerRole findById(String id);

    PlayerRole findByRecordId(String recordId);

    void deleteByRecordId(String recordId);

    List<PlayerRole> findByStatusOrderByIdentifier(boolean b);
}
