package com.avitam.fantasy11.repository;

import com.avitam.fantasy11.model.LineUpStatus;
import com.avitam.fantasy11.repository.generic.GenericImportRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository("LineUpStatusRepository")
public interface LineUpStatusRepository extends GenericImportRepository<LineUpStatus> {

    LineUpStatus findById(String id);

    LineUpStatus findByRecordId(String recordId);

    void deleteByRecordId(String recordId);

    List<LineUpStatus> findByStatusOrderByIdentifier(Boolean b);


}
