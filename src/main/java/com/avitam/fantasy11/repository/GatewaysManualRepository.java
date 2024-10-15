package com.avitam.fantasy11.repository;

import com.avitam.fantasy11.model.GatewaysManual;
import com.avitam.fantasy11.repository.generic.GenericImportRepository;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("GatewaysManualRepository")
public interface GatewaysManualRepository extends GenericImportRepository<GatewaysManual> {

    Optional<GatewaysManual> findById(String id);

    GatewaysManual findByRecordId(String recordId);

    void deleteByRecordId(String recordId);

    List<GatewaysManual> findByStatusOrderByIdentifier(Boolean b);
}
