package com.avitam.fantasy11.api.service;


import com.avitam.fantasy11.model.LineUpStatus;

public interface LineUpStatusService {


    LineUpStatus findById(String id);

    void save(LineUpStatus lineUpStatus);

    LineUpStatus deleteById(String id);

    LineUpStatus updateStatus(String id);
}
