package com.avitam.fantasy11.repository;

import com.avitam.fantasy11.model.Contest;
import com.avitam.fantasy11.model.Matches;
import com.avitam.fantasy11.repository.generic.GenericImportRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("MatchesRepository")
public interface MatchesRepository extends GenericImportRepository<Matches> {
    Optional<Matches> findById(String id);

    Matches findByRecordId(String recordId);

    void deleteByRecordId(String recordId);

    List<Matches> findByStatusOrderByIdentifier(boolean b);

    List<Matches> findByEventStatus(String eventStatus);


}