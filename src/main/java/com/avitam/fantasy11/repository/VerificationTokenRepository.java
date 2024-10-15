package com.avitam.fantasy11.repository;

import com.avitam.fantasy11.model.VerificationToken;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository("VerificationTokenRepository")
public interface VerificationTokenRepository extends MongoRepository<VerificationToken,ObjectId> {
    VerificationToken findByToken(String verificationToken);

}
