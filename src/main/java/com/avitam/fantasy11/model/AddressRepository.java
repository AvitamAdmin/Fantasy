package com.avitam.fantasy11.model;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AddressRepository extends MongoRepository<Address, ObjectId> {
    Optional<Address> findById(String id);

    // Optional<Address> findByUserId(ObjectId id);

    //Node getByUserId(ObjectId userId);
}
