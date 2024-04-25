package com.avitam.fantasy11.core.service.impl;

import com.avitam.fantasy11.core.service.CheckoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CheckoutServiceImpl implements CheckoutService {
    @Autowired
    private TestResultRepository testResultRepository;

    @Override
    public void saveCheckout(TestResult testResult) {
        testResultRepository.save(testResult);

    }

    @Override
    public List<TestResult> findAllBySessionIdAndTestName(String sessionId, String testName) {
        return testResultRepository.findAllBySessionIdAndTestName(sessionId, testName);
    }
}
