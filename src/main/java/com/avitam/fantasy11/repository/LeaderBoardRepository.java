package com.avitam.fantasy11.repository;

import com.avitam.fantasy11.api.dto.LeaderBoardDto;
import com.avitam.fantasy11.model.LeaderBoard;
import com.avitam.fantasy11.repository.generic.GenericImportRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("LeaderBoardRepository")
public interface LeaderBoardRepository extends GenericImportRepository<LeaderBoard> {
    LeaderBoard findById(String id);

    LeaderBoard findByRecordId(String recordId);

    void deleteByRecordId(String recordId);

    List<LeaderBoard> findByStatusOrderByIdentifier(boolean b);
}
