package com.avitam.fantasy11.api.service;

import com.avitam.fantasy11.model.MatchType;

public interface MatchTypeService {


    public MatchType findById(String matchTypeId);



    public MatchType deleteById(String matchTypeId);


    public void save(MatchType matchType) ;


    public MatchType updateByMatchTypeId(String matchTypeId);
}
