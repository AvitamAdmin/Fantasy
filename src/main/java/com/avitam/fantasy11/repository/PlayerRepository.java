package com.avitam.fantasy11.repository;

import com.avitam.fantasy11.model.Player;
import com.avitam.fantasy11.repository.generic.GenericImportRepository;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("PlayerRepository")
public interface PlayerRepository extends GenericImportRepository<Player> {

    Optional<Player> findById(String id);

    List<Player> findByTeamId(String teamId);

    Player findByRecordId(String recordId);

    void deleteByRecordId(String recordId);

    List<Player> findByStatusOrderByIdentifier(boolean b);
}
