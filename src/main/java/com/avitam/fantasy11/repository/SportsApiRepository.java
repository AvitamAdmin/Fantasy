package com.avitam.fantasy11.repository;

import com.avitam.fantasy11.model.SportsApi;
import com.avitam.fantasy11.repository.generic.GenericImportRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("SportsApiRepository")
public interface SportsApiRepository extends GenericImportRepository<SportsApi> {

    Optional<SportsApi> findById(String id);

    SportsApi findByRecordId(String recordId);

    void deleteByRecordId(String recordId);


    List<SportsApi> findByStatusOrderByIdentifier(boolean b);
}
