package com.avitam.fantasy11.repository;

import com.avitam.fantasy11.model.SEO;
import com.avitam.fantasy11.repository.generic.GenericImportRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("SEORepository")
public interface SEORepository extends GenericImportRepository<SEO> {
    Optional<SEO> findById(String id);

    SEO findByRecordId(String recordId);

    void deleteByRecordId(String recordId);

    List<SEO> findByStatusOrderByIdentifier(boolean b);
}
