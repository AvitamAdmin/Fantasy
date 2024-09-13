package com.avitam.fantasy11.model;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import   java.util.List;
@Repository
public interface MainContestRepository extends MongoRepository<MainContest, ObjectId> {
    MainContest findById(String id);

    MainContest findByMainContestId(String mainContestId);

    MainContest findByRecordId(String recordId);

    void deleteByRecordId(String recordId);

    List<MainContest> findByStatusOrderByIdentifier(Boolean b);


}