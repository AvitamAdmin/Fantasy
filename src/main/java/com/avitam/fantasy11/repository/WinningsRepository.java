package com.avitam.fantasy11.repository;

import com.avitam.fantasy11.model.Winnings;
import com.avitam.fantasy11.repository.generic.GenericImportRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("WinningsRepository")
public interface WinningsRepository extends GenericImportRepository<Winnings> {

    Optional<Winnings> findById(String id);

    Winnings findByRecordId(String recordId);

    void deleteByRecordId(String recordId);

    List<Winnings> findByStatusOrderByIdentifier(Boolean b);
}
