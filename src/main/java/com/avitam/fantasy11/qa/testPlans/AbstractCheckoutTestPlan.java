package com.avitam.fantasy11.qa.testPlans;

import com.avitam.fantasy11.qa.framework.TestContext;
import com.avitam.fantasy11.qa.utils.TestDataUtils;
import com.avitam.fantasy11.qa.utils.TestDataUtils.Field;
import org.springframework.stereotype.Component;

@Component
public abstract class AbstractCheckoutTestPlan extends AbstractTestPlan {
    @Override
    public String getTestName(TestContext context) {
        return getCurrentSku(context);
    }

    protected String getCurrentSku(TestContext context) {
        return TestDataUtils.getString(testData, Field.SKUS, context.getTestIdentifier());
    }

}
