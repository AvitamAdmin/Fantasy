package com.avitam.fantasy11.api.service;

import com.avitam.fantasy11.model.CommonFields;

public interface BaseService {

    void populateCommonData(CommonFields request);

    CommonFields validateIdentifier(String entityName, String identifier);
}
