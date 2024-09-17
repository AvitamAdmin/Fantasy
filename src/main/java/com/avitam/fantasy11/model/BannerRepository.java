package com.avitam.fantasy11.model;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface BannerRepository extends MongoRepository<Banner, ObjectId> {

    Optional<Banner> findById(String id);

    List<Banner> findByStatusOrderByIdentifier(boolean b);

    Banner findByRecordId(String recordId);

    void deleteByRecordId(String id);
}
