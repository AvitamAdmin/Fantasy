package com.avitam.fantasy11.core.service;

import com.avitam.fantasy11.core.model.TestResult;

import java.util.List;

public interface CheckoutService {

    void saveCheckout(TestResult testResult);

    List<TestResult> findAllBySessionIdAndTestName(String sessionId, String testName);
}
