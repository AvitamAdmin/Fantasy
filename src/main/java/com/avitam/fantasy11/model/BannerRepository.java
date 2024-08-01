package com.avitam.fantasy11.model;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface BannerRepository extends MongoRepository<Banner, ObjectId> {

    Optional<Banner> findById(String id);
}
