package com.avitam.fantasy11.model;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface MobileTokenRepository extends MongoRepository<MobileToken,Integer> {
}
