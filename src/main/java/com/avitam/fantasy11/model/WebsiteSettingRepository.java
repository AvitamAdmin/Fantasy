package com.avitam.fantasy11.model;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WebsiteSettingRepository extends MongoRepository<WebsiteSetting, Long> {

}

