package com.avitam.fantasy11.repository;

import com.avitam.fantasy11.model.ContestType;
import com.avitam.fantasy11.repository.generic.GenericImportRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("ContestTypeRepository")
public interface ContestTypeRepository extends GenericImportRepository<ContestType> {

    ContestType findById(String id);

    ContestType findByRecordId(String recordId);

    void deleteByRecordId(String recordId);

    List<ContestType> findByStatusOrderByIdentifier(Boolean b);
}