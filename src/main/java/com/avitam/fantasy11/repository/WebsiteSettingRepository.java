package com.avitam.fantasy11.repository;

import com.avitam.fantasy11.model.WebsiteSetting;
import com.avitam.fantasy11.repository.generic.GenericImportRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("WebsiteSettingRepository")
public interface WebsiteSettingRepository extends GenericImportRepository<WebsiteSetting> {

    Optional<WebsiteSetting> findById(String id);

    WebsiteSetting findByRecordId(String recordId);

    void deleteByRecordId(String recordId);

    Object findByStatusOrderByIdentifier(boolean b);
}


