package com.avitam.fantasy11.repository;

import com.avitam.fantasy11.model.Script;
import com.avitam.fantasy11.repository.generic.GenericImportRepository;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository("ScriptRepository")
public interface ScriptRepository extends GenericImportRepository<Script> {

    Optional<Script> findById(String id);

    Script findByRecordId(String recordId);

    void deleteByRecordId(String recordId);

    List<Script> findByStatusOrderByIdentifier(boolean b);
}
