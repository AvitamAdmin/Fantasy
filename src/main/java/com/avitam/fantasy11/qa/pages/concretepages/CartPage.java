package com.avitam.fantasy11.qa.pages.concretepages;

import com.avitam.fantasy11.qa.framework.TestContext;
import com.avitam.fantasy11.qa.pages.abstractpages.CartAbstractPage;
import com.avitam.fantasy11.qa.utils.ClickUtils;
import com.avitam.fantasy11.qa.utils.ReportUtils;
import org.openqa.selenium.By;
import org.springframework.stereotype.Component;

import static com.avitam.fantasy11.qa.utils.CheilStringUtils.CLICKING_CHECKOUT_BUTTON;
import static com.avitam.fantasy11.qa.utils.CheilStringUtils.EMPTY_STRING;

@Component
public class CartPage extends CartAbstractPage {

    public static final By GO_TO_CHECKOUT_BUTTON = By.cssSelector(".CartTotalsComponent > .checkout-button > .btn");

    @Override
    public void goToCheckout(TestContext context) {
        ClickUtils.click(context, GO_TO_CHECKOUT_BUTTON, CLICKING_CHECKOUT_BUTTON);
        ReportUtils.info(context, EMPTY_STRING, true);
    }

    @Override
    public void openPage(TestContext context) {

    }
}
