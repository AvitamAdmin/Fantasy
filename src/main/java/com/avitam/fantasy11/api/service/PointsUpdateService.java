package com.avitam.fantasy11.api.service;

import com.avitam.fantasy11.model.PointsUpdate;

public interface PointsUpdateService {

    PointsUpdate findById(String Id);

    PointsUpdate deleteById(String id);

    PointsUpdate updatePoints(String id);

    void save(PointsUpdate pointsUpdate);
}
