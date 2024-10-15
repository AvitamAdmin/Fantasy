package com.avitam.fantasy11.repository;

import com.avitam.fantasy11.model.Withdraw;
import com.avitam.fantasy11.repository.generic.GenericImportRepository;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository("WithdrawRepository")
public interface WithdrawRepository extends GenericImportRepository<Withdraw> {
}
