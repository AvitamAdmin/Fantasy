package com.avitam.fantasy11.model;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchTypeRepository extends MongoRepository<MatchType, Long> {
}