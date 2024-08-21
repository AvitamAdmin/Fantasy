package com.avitam.fantasy11.api.service;

import com.avitam.fantasy11.model.Contest;
import com.avitam.fantasy11.model.SportType;

public interface SportTypeService {

    public SportType findBySportId(String id);


    public void save(SportType sportType) ;


    public SportType deleteBySportId(String id);


    public SportType updateSportType(String id);
}
