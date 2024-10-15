package com.avitam.fantasy11.repository;

import com.avitam.fantasy11.model.Tournament;
import com.avitam.fantasy11.repository.generic.GenericImportRepository;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository("TournamentRepository")
public interface TournamentRepository extends GenericImportRepository<Tournament> {

    Tournament findById(String id);

    Tournament findByRecordId(String recordId);

    void deleteByRecordId(String recordId);

    List<Tournament> findByStatusOrderByIdentifier(boolean b);
}
