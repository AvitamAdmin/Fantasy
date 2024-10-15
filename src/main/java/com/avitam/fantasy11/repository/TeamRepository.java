package com.avitam.fantasy11.repository;

import com.avitam.fantasy11.model.Team;
import com.avitam.fantasy11.repository.generic.GenericImportRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("TeamRepository")
public interface TeamRepository extends GenericImportRepository<Team> {

    Optional<Team> findById(String id);

    Team findByRecordId(String recordId);

    void deleteByRecordId(String recordId);

    List<Team> findByStatusOrderByIdentifier(Boolean b);
}
