package com.avitam.fantasy11.repository;

import com.avitam.fantasy11.model.PointsUpdate;
import com.avitam.fantasy11.repository.generic.GenericImportRepository;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("PointsUpdateRepository")
public interface PointsUpdateRepository extends GenericImportRepository<PointsUpdate> {
    Optional<PointsUpdate> findById(String id);

    PointsUpdate findByRecordId(String recordId);

    void deleteByRecordId(String recordId);


    List<PointsUpdate> findByStatusOrderByIdentifier(boolean b);
}
