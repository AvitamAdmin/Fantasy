package com.avitam.fantasy11.repository;

import com.avitam.fantasy11.model.KYC;
import com.avitam.fantasy11.repository.generic.GenericImportRepository;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("KYCRepository")
public interface KYCRepository extends GenericImportRepository<KYC> {

    Optional<KYC> findById(String objectId);

    KYC findByRecordId(String recordId);

    void deleteByRecordId(String recordId);

    List<KYC> findByStatusOrderByIdentifier(boolean b);
}
