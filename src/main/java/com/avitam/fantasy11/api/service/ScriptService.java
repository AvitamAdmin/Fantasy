package com.avitam.fantasy11.api.service;

import com.avitam.fantasy11.api.dto.ScriptDto;
import com.avitam.fantasy11.api.dto.ScriptWsDto;
import com.avitam.fantasy11.model.Role;

public interface ScriptService {

    Role findByRecordId(String recordId);

    void deleteByRecordId(String recordId);

    void updateByRecordId(String recordId);

    ScriptWsDto handleEdit(ScriptWsDto scriptWsDto);
}
