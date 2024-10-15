package com.avitam.fantasy11.repository;

import com.avitam.fantasy11.model.TeamLineup;
import com.avitam.fantasy11.repository.generic.GenericImportRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("TeamLineupRepository")
public interface TeamLineupRepository extends GenericImportRepository<TeamLineup> {

    Optional<TeamLineup> findById(String id);

    TeamLineup findByRecordId(String recordId);

    void deleteByRecordId(String recordId);

    List<TeamLineup> findByStatusOrderByIdentifier(Boolean b);
}
