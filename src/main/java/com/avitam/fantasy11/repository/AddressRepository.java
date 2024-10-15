package com.avitam.fantasy11.repository;

import com.avitam.fantasy11.model.Address;
import com.avitam.fantasy11.repository.generic.GenericImportRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("AddressRepository")
public interface AddressRepository extends GenericImportRepository<Address> {
    Address findByRecordId(String recordId);

    void deleteByRecordId(String recordId);

    Optional<Address> findById(String id);

    List<Address> findByStatusOrderByIdentifier(Boolean b);
}
