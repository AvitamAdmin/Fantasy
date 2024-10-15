package com.avitam.fantasy11.api.service;

import com.avitam.fantasy11.model.BaseEntity;

public interface BaseService {

     void populateCommonData(BaseEntity request);

    BaseEntity validateIdentifier(String entityName , String identifier);
}
