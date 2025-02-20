package com.avitam.fantasy11.repository;

import com.avitam.fantasy11.model.Contest;
import com.avitam.fantasy11.repository.generic.GenericImportRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("ContestRepository")
public interface ContestRepository extends GenericImportRepository<Contest> {
    Optional<Contest> findById(String id);

    void deleteByRecordId(String recordId);

    List<Contest> findByStatusOrderByIdentifier(Boolean b);

    Contest findByRecordId(String recordId);

    List<Contest> findContestByMatchId(String matchId);

    List<Contest> findByCommonContest(Boolean b);

    List<Contest> findByMatchId(String matchId);
}