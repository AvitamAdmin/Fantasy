package com.avitam.fantasy11.repository;

import com.avitam.fantasy11.model.Notification;
import com.avitam.fantasy11.repository.generic.GenericImportRepository;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("NotificationRepository")
public interface NotificationRepository extends GenericImportRepository<Notification> {
    //Optional<Notification> findById(ObjectId id);

    Optional<Notification> findById(String id);

    Notification findByRecordId(String recordId);

    void deleteByRecordId(String recordId);


    List<Notification> findByStatusOrderByIdentifier(Boolean b);
}
