package com.avitam.fantasy11.model;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface TeamRepository extends MongoRepository<Team,Integer> {
}