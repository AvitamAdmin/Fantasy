package com.avitam.fantasy11.repository;

import com.avitam.fantasy11.model.GatewaysAutomatic;
import com.avitam.fantasy11.repository.generic.GenericImportRepository;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("GatewaysAutomaticRepository")
public interface GatewaysAutomaticRepository extends GenericImportRepository<GatewaysAutomatic> {

    Optional<GatewaysAutomatic> findById(String id);

    GatewaysAutomatic findByRecordId(String recordId);

    void deleteByRecordId(String recordId);

    List<GatewaysAutomatic> findByStatusOrderByIdentifier(Boolean b);

    void deleteById(String id);
}
