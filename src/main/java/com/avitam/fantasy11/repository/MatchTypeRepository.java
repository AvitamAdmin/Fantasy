package com.avitam.fantasy11.repository;

import com.avitam.fantasy11.model.MatchType;
import com.avitam.fantasy11.repository.generic.GenericImportRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("MatchTypeRepository")
public interface MatchTypeRepository extends GenericImportRepository<MatchType> {

    Optional<MatchType> findById(String id);

    MatchType findByRecordId(String recordId);

    void deleteByRecordId(String recordId);

    List<MatchType> findByStatusOrderByIdentifier(boolean b);
}
