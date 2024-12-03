package com.avitam.fantasy11.repository;

import com.avitam.fantasy11.model.MobileToken;
import com.avitam.fantasy11.model.User;
import com.avitam.fantasy11.repository.generic.GenericImportRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("MobileTokenRepository")
public interface MobileTokenRepository extends GenericImportRepository<MobileToken> {
    Optional<MobileToken> findById(String id);

    MobileToken findByRecordId(String recordId);

    void deleteByRecordId(String recordId);

    List<MobileToken> findByStatusOrderByIdentifier(boolean b);

    MobileToken findByEmail(String email);
}