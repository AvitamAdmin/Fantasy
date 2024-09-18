package com.avitam.fantasy11.api.service;

import com.avitam.fantasy11.api.dto.ExtensionDto;
import com.avitam.fantasy11.model.Extension;

public interface ExtensionService {

    Extension findByRecordId(String recordId);

    void deleteByRecordId(String recordId);

    ExtensionDto handleEdit(ExtensionDto request);

    void updateByRecordId(String recordId);
}
