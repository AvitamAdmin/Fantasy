package com.avitam.fantasy11.repository;

import com.avitam.fantasy11.model.WithdrawalDetails;
import com.avitam.fantasy11.repository.generic.GenericImportRepository;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("WithdrawalDetailsRepository")
public interface WithdrawalDetailsRepository extends GenericImportRepository<WithdrawalDetails> {

    Optional<WithdrawalDetails> findById(String id);

    WithdrawalDetails findByRecordId(String recordId);

    void deleteByRecordId(String recordId);

    List<WithdrawalDetails> findByStatusOrderByIdentifier(Boolean b);
}
