package com.avitam.fantasy11.api.service;

import com.avitam.fantasy11.model.BaseEntity;
import com.avitam.fantasy11.model.CommonFields;

public interface BaseService {

     void populateCommonData(BaseEntity request);

    BaseEntity validateIdentifier(String entityName , String identifier);
}
