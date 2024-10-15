package com.avitam.fantasy11.repository;

import com.avitam.fantasy11.model.LeaderBoard;
import com.avitam.fantasy11.repository.generic.GenericImportRepository;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("LeaderBoardRepository")
public interface LeaderBoardRepository extends GenericImportRepository<LeaderBoard> {
    LeaderBoard findById(String id);

    LeaderBoard findByRecordId(String recordId);

    void deleteByRecordId(String recordId);


    List<LeaderBoard> findStatusOrderByIdentifier(boolean b);
}
