package com.avitam.fantasy11.model;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AddressRepository extends MongoRepository<Address, ObjectId> {
    Address findByRecordId(String recordId);

    void deleteByRecordId(String recordId);



    Optional<Address> findById(String id);

    List<Address> findByStatusOrderByIdentifier(Boolean b);
}
