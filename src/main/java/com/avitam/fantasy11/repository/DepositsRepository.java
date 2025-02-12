package com.avitam.fantasy11.repository;

import com.avitam.fantasy11.model.Deposits;
import com.avitam.fantasy11.repository.generic.GenericImportRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("DepositsRepository")
public interface DepositsRepository extends GenericImportRepository<Deposits> {

    Deposits findById(String id);

    List<Deposits> findByDepositStatus(String approved);

    Deposits findByRecordId(String recordId);

    void deleteByRecordId(String recordId);

    List<Deposits> findByStatusAndDepositStatusOrderByIdentifier(boolean b, String depositStatus);

    List<Deposits> findByStatusOrderByIdentifier(boolean b);
}
