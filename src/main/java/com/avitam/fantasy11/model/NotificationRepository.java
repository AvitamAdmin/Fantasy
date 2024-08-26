package com.avitam.fantasy11.model;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NotificationRepository extends MongoRepository<Notification, ObjectId> {
    //Optional<Notification> findById(ObjectId id);

    Optional<Notification> findById(String id);

    Optional<Notification> findByRecordId(String recordId);

    void deleteByRecordId(String recordId);



}
