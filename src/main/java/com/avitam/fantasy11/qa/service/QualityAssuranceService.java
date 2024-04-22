package com.avitam.fantasy11.qa.service;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public interface QualityAssuranceService {

    public void populateTestData(Map<String, String[]> parameterMap) throws JsonProcessingException;

    HashMap<String, Collection> getQaProfileMap();

    void saveTestResult(Map<String, Object> testData, String orderNumber, String sku);
}
