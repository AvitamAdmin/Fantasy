package com.avitam.fantasy11.repository;

import com.avitam.fantasy11.model.UserWinnings;
import com.avitam.fantasy11.repository.generic.GenericImportRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("UserWinningsRepository")
public interface UserWinningsRepository extends GenericImportRepository<UserWinnings> {
    Optional<UserWinnings> findById(String id);

    UserWinnings findByRecordId(String recordId);

    void deleteByRecordId(String recordId);

    List<UserWinnings> findByStatusOrderByIdentifier(Boolean b);
}
