package com.avitam.fantasy11.api.service;

import com.avitam.fantasy11.api.dto.UserTeamsDto;
import com.avitam.fantasy11.model.Address;
import com.avitam.fantasy11.model.PointsUpdate;
import com.avitam.fantasy11.model.User;
import com.avitam.fantasy11.model.UserTeams;

import java.util.Optional;

public interface UserTeamsService {
        UserTeams findByRecordId(String recordId);

        void deleteByRecordId(String recordId);

        void updateByRecordId(String recordId);

        UserTeamsDto handleEdit(UserTeamsDto request);
}
