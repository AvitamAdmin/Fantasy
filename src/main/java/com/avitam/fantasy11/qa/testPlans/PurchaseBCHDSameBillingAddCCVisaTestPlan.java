package com.avitam.fantasy11.qa.testPlans;

import com.avitam.fantasy11.qa.factories.BuyingConfiguratorAemPageFactory;
import com.avitam.fantasy11.qa.factories.CartPageFactory;
import com.avitam.fantasy11.qa.factories.CheckoutPageFactory;
import com.avitam.fantasy11.qa.framework.TestContext;
import com.avitam.fantasy11.qa.pages.abstractpages.BuyingConfiguratorAemAbstractPage;
import com.avitam.fantasy11.qa.pages.abstractpages.CartAbstractPage;
import com.avitam.fantasy11.qa.pages.abstractpages.CheckoutAbstractPage;
import com.avitam.fantasy11.qa.service.QualityAssuranceService;
import org.springframework.stereotype.Component;

import static com.avitam.fantasy11.qa.utils.CheilStringUtils.EMPTY_STRING;
import static com.avitam.fantasy11.qa.utils.CheilStringUtils.GALAXY_S23_ULTRA_BUY_URL_FRAGMENT;

@Component
public class PurchaseBCHDSameBillingAddCCVisaTestPlan extends AbstractCheckoutTestPlan {

    private BuyingConfiguratorAemPageFactory buyingConfiguratorAemPageFactory;

    private CartPageFactory cartPageFactory;

    private CheckoutPageFactory checkoutPageFactory;

    private QualityAssuranceService qualityAssuranceService;


    @Override
    public void testPlanSpecificSteps(TestContext context) {

        String sku = getCurrentSku(context);

        BuyingConfiguratorAemAbstractPage buyingConfiguratorAemPage = getBuyingConfiguratorAemPageFactory().getBuyingConfiguratorAemPage();
        buyingConfiguratorAemPage.openPage(context, testData, GALAXY_S23_ULTRA_BUY_URL_FRAGMENT + sku);
        buyingConfiguratorAemPage.closeCustomerAgentMessagePopupAtBottomRightCorner(context);
        buyingConfiguratorAemPage.refuseTradeIn(context);
        buyingConfiguratorAemPage.refuseSamsungCare(context);
        buyingConfiguratorAemPage.addToCartFixedButtonAtBCbottom(context);
        buyingConfiguratorAemPage.addToCartStickyButtonAtUpsellingPage(context);
        buyingConfiguratorAemPage.addToCartStickyButtonAtUpsellingPage(context);

        CartAbstractPage cartPage = getCartPageFactory().getCartPage();
        cartPage.goToCheckout(context);

        CheckoutAbstractPage checkoutPage = getCheckoutPageFactory().getCheckoutPage();
        checkoutPage.continueAsGuest(context, testData);
        checkoutPage.enterContactInfo(context, testData);
        checkoutPage.enterShippingAddress(context, testData);
        checkoutPage.continueToPayment(context);

        //persist report to db
        getQualityAssuranceService().saveTestResult(testData, EMPTY_STRING, sku);
    }

    private BuyingConfiguratorAemPageFactory getBuyingConfiguratorAemPageFactory() {
        return buyingConfiguratorAemPageFactory = getFactory(buyingConfiguratorAemPageFactory, BuyingConfiguratorAemPageFactory.class);
    }

    private CartPageFactory getCartPageFactory() {
        return cartPageFactory = getFactory(cartPageFactory, CartPageFactory.class);
    }

    private CheckoutPageFactory getCheckoutPageFactory() {
        return checkoutPageFactory = getFactory(checkoutPageFactory, CheckoutPageFactory.class);
    }

    private QualityAssuranceService getQualityAssuranceService() {
        return qualityAssuranceService = getFactory(qualityAssuranceService, QualityAssuranceService.class);
    }
}
