package com.avitam.fantasy11.api.service;

import com.avitam.fantasy11.model.Contest;
import com.avitam.fantasy11.model.SportType;

import java.util.Optional;

public interface SportTypeService {

    Optional<SportType> findByRecordId(String recordId);

    void deleteByRecordId(String recordId);

    void updateByRecordId(String recordId);
}
