package com.avitam.fantasy11.api.service;

import com.avitam.fantasy11.model.PointsUpdate;
import com.avitam.fantasy11.model.Role;

import java.util.Optional;

public interface RoleService {

    Role findByRecordId(String recordId);

    void deleteByRecordId(String recordId);

    void updateByRecordId(String recordId);
}
