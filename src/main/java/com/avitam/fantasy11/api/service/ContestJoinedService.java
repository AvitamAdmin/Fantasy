package com.avitam.fantasy11.api.service;

import com.avitam.fantasy11.model.ContestJoined;

public interface ContestJoinedService {

    public ContestJoined findById(String id);


    public void save(ContestJoined contestJoined) ;


    public ContestJoined deleteById(String id);


    public ContestJoined updateContest(String id);
}
