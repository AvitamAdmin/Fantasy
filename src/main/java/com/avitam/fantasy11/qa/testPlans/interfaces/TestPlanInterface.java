package com.avitam.fantasy11.qa.testPlans.interfaces;

import com.avitam.fantasy11.qa.framework.TestContext;

public interface TestPlanInterface {
    void testPlanSpecificSteps(TestContext context); //specific test steps - must be implemented in the child class

    String getTestName(TestContext context); //test name in the Extent report
}
