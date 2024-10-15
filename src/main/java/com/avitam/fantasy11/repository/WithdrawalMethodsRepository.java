package com.avitam.fantasy11.repository;

import com.avitam.fantasy11.model.WithdrawalMethods;
import com.avitam.fantasy11.repository.generic.GenericImportRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("WithdrawalMethodsRepository")
public interface WithdrawalMethodsRepository extends GenericImportRepository<WithdrawalMethods> {

    Optional<WithdrawalMethods> findById(String id);

    WithdrawalMethods findByMethodName(String methodName);

    WithdrawalMethods findByRecordId(String recordId);

    void deleteByRecordId(String recordId);

    List<WithdrawalMethods> findByStatusOrderByIdentifier(Boolean b);
}
