package com.avitam.fantasy11.model;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface TournamentRepository extends MongoRepository<Tournament, ObjectId> {

    Tournament findById(String id);

    Tournament findByRecordId(String recordId);

    void deleteByRecordId(String recordId);

    List<Tournament> findStatusOrderByIdentifier(boolean b);
}
