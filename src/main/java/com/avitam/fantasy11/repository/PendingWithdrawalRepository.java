package com.avitam.fantasy11.repository;

import com.avitam.fantasy11.model.PendingWithdrawal;
import com.avitam.fantasy11.repository.generic.GenericImportRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("PendingWithdrawalRepository")
public interface PendingWithdrawalRepository extends GenericImportRepository<PendingWithdrawal> {

    Optional<PendingWithdrawal> findById(String id);

    PendingWithdrawal findByRecordId(String recordId);

    void deleteByRecordId(String recordId);

    List<PendingWithdrawal> findByStatusOrderByIdentifier(Boolean b);
}
