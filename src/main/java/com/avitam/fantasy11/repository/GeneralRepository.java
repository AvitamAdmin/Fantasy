package com.avitam.fantasy11.repository;

import com.avitam.fantasy11.model.General;
import com.avitam.fantasy11.repository.generic.GenericImportRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("GeneralRepository")
public interface GeneralRepository extends GenericImportRepository<General> {
    Optional<General> findById(String id);

    General findByRecordId(String recordId);

    void deleteByRecordId(String recordId);


}
