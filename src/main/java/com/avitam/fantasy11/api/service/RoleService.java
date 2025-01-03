package com.avitam.fantasy11.api.service;

import com.avitam.fantasy11.api.dto.RoleWsDto;
import com.avitam.fantasy11.model.Role;

public interface RoleService {

    Role findByRecordId(String recordId);

    void deleteByRecordId(String recordId);

    void updateByRecordId(String recordId);

    RoleWsDto handleEdit(RoleWsDto request);
}
