package com.avitam.fantasy11.repository;

import com.avitam.fantasy11.model.Extension;
import com.avitam.fantasy11.repository.generic.GenericImportRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository("ExtensionRepository")
public interface ExtensionRepository extends GenericImportRepository<Extension> {
    Extension findById(String id);

    Extension findByRecordId(String recordId);

    void deleteByRecordId(String recordId);

    List<Extension> findByStatusOrderByIdentifier(Boolean b);


}
