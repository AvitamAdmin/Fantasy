package com.avitam.fantasy11.api.service;

import com.avitam.fantasy11.model.Contest;

public interface ContestService {
    Contest findById(String id);

    void save(Contest contest);

    Contest deleteById(String id);

    Contest updateContest(String id);
}
