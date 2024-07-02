package com.avitam.billing.model;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BillOfSupplyRepository extends MongoRepository<BillOfSupply, ObjectId> {

    Optional<BillOfSupply> findById(String id);
}
