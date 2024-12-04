package com.avitam.fantasy11.api.service;

import com.avitam.fantasy11.api.dto.SEOWsDto;
import com.avitam.fantasy11.model.SEO;

public interface SEOService {

    public SEO findByRecordId(String recordId);

    public void deleteByRecordId(String recordId);

    public void updateByRecordId(String recordId);

    public SEOWsDto handleEdit(SEOWsDto request);
}
