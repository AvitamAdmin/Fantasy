package com.avitam.fantasy11.repository;

import com.avitam.fantasy11.model.ContestJoined;
import com.avitam.fantasy11.repository.generic.GenericImportRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("ContestJoinedRepository")
public interface ContestJoinedRepository extends GenericImportRepository<ContestJoined> {
    Optional<ContestJoined> findById(String id);

    ContestJoined findByRecordId(String recordId);

    void deleteByRecordId(String recordId);

    List<ContestJoined> findByStatusOrderByIdentifier(boolean b);


    List<ContestJoined> findByUserId(String userId);
}