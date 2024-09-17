package com.avitam.fantasy11.model;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface PlayerRepository extends MongoRepository<Player, ObjectId> {

    Optional<Player> findById(String id);

    List<Player> findByTeamId(String teamId);

    Player findByRecordId(String recordId);

    void deleteByRecordId(String recordId);

    List<Player> findByStatusOrderByIdentifier(boolean b);
}
