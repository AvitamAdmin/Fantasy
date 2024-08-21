package com.avitam.fantasy11.api.service;

import com.avitam.fantasy11.model.TeamLineup;

public interface TeamLineUpService {


    public TeamLineup findByTeamId(String teamId);



    public TeamLineup deleteByTeamId(String teamId);


    public void save(TeamLineup teamLineup) ;


    public TeamLineup updateByTeamId(String teamId);
}
