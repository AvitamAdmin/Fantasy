package com.avitam.fantasy11.repository;

import com.avitam.fantasy11.model.ContestDetails;
import com.avitam.fantasy11.repository.generic.GenericImportRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("ContestDetailsRepository")
public interface ContestDetailsRepository extends GenericImportRepository<ContestDetails> {
    List<ContestDetails> findByStatusOrderByIdentifier(boolean b);

    ContestDetails findByRecordId(String recordId);
}
