package com.avitam.fantasy11.repository;

import com.avitam.fantasy11.model.PointsMaster;
import com.avitam.fantasy11.repository.generic.GenericImportRepository;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("PointsMasterRepository")
public interface PointsMasterRepository extends GenericImportRepository<PointsMaster> {

    Optional<PointsMaster> findById(String id);

    PointsMaster findByRecordId(String recordId);

    void deleteByRecordId(String recordId);

    List<PointsMaster> findByStatusOrderByIdentifier(boolean b);
}
