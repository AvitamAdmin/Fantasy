package com.avitam.fantasy11.repository;

import com.avitam.fantasy11.model.MainContest;
import com.avitam.fantasy11.repository.generic.GenericImportRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository("MainContestRepository")
public interface MainContestRepository extends GenericImportRepository<MainContest> {
    MainContest findById(String id);

    MainContest findByMainContestId(String mainContestId);

    MainContest findByRecordId(String recordId);

    void deleteByRecordId(String recordId);

    List<MainContest> findByStatusOrderByIdentifier(Boolean b);


}