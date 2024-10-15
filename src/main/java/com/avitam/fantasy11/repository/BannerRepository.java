package com.avitam.fantasy11.repository;

import com.avitam.fantasy11.model.Banner;
import com.avitam.fantasy11.repository.generic.GenericImportRepository;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository("BannerRepository")
public interface BannerRepository extends GenericImportRepository<Banner> {

    Optional<Banner> findById(String id);

    List<Banner> findByStatusOrderByIdentifier(boolean b);

    Banner findByRecordId(String recordId);

    void deleteByRecordId(String id);
}
