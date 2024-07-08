package com.avitam.fantasy11.model;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationTokenRepository extends MongoRepository<VerificationToken, ObjectId> {
    VerificationToken findByToken(String verificationToken);
}
