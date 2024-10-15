package com.avitam.fantasy11.repository;

import com.avitam.fantasy11.model.SportType;
import com.avitam.fantasy11.repository.generic.GenericImportRepository;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("SportTypeRepository")
public interface SportTypeRepository extends GenericImportRepository<SportType> {

    SportType findById(String id);

    SportType findByRecordId(String recordId);

    void deleteByRecordId(String recordId);

    List<SportType> findByStatusOrderByIdentifier(boolean b);
}
