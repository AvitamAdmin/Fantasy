package com.avitam.fantasy11.model;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PlayerRoleRepository extends MongoRepository<PlayerRole, ObjectId> {
    PlayerRole findById(String id);

    PlayerRole findByRecordId(String recordId);

    void deleteByRecordId(String recordId);

    List<PlayerRole> findByStatusOrderByIdentifier(boolean b);
}
