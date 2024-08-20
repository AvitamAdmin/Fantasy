package com.avitam.fantasy11.api.service;

import com.avitam.fantasy11.model.Address;
import com.avitam.fantasy11.model.UserTeams;

public interface UserTeamsService {
        UserTeams findByUserId(String userId);

        UserTeams deleteByUserId(String userId);

        void save(UserTeams userTeams);


        UserTeams updateByUserId(String userId);
}
