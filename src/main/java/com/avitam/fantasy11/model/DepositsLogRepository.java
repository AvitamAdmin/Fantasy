package com.avitam.fantasy11.model;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface DepositsLogRepository extends MongoRepository<DepositsLog, ObjectId>{


    Optional<DepositsLog> findById(String id);

    Object findByStatus(String pending);

   List<DepositsLog>  findByDepositStatus(String pending);
}
