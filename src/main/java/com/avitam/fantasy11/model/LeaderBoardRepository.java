package com.avitam.fantasy11.model;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LeaderBoardRepository extends MongoRepository<LeaderBoard, ObjectId>{
    LeaderBoard findById(String id);

    LeaderBoard findByRecordId(String recordId);

    void deleteByRecordId(String recordId);


    List<LeaderBoard> findStatusOrderByIdentifier(boolean b);
}
