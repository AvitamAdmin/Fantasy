package com.avitam.fantasy11.api.service;

import com.avitam.fantasy11.api.dto.SEOWsDto;
import com.avitam.fantasy11.model.SEO;

public interface SEOService {

    SEO findByRecordId(String recordId);

    void deleteByRecordId(String recordId);

    void updateByRecordId(String recordId);

    SEOWsDto handleEdit(SEOWsDto request);
}
