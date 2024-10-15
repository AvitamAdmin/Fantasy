package com.avitam.fantasy11.repository;

import com.avitam.fantasy11.model.MatchScore;
import com.avitam.fantasy11.repository.generic.GenericImportRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("MatchScoreRepository")
public interface MatchScoreRepository extends GenericImportRepository<MatchScore> {

    MatchScore findById(String id);

    MatchScore findByRecordId(String recordId);

    void deleteByRecordId(String recordId);


    List<MatchScore> findByStatusOrderByIdentifier(boolean b);
}
