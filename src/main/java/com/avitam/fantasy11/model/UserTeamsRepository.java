package com.avitam.fantasy11.model;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserTeamsRepository extends MongoRepository <UserTeams, ObjectId>{
    Optional<UserTeams> findById(String id);

    @Query("{matchId:?0)}")
    List<Team>getTeamsByMatchId(String matchId);

    Optional<UserTeams> findByRecordId(String recordId);

    void deleteByRecordId(String recordId);

    Optional<UserTeams> updateByRecordId(String recordId);

}