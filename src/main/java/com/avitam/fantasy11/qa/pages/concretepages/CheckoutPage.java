package com.avitam.fantasy11.qa.pages.concretepages;

import com.avitam.fantasy11.qa.framework.TestContext;
import com.avitam.fantasy11.qa.pages.abstractpages.CheckoutAbstractPage;
import avitam.fantasy11.qa.utils.ClickUtils;
import avitam.fantasy11.qa.utils.FillUtils;
import avitam.fantasy11.qa.utils.ReportUtils;
import avitam.fantasy11.qa.utils.TestDataUtils;
import org.openqa.selenium.By;
import org.springframework.stereotype.Component;

import java.util.Map;

import static avitam.fantasy11.qa.utils.CheilStringUtils.*;
import static avitam.fantasy11.qa.utils.TestDataUtils.*;

@Component
public class CheckoutPage extends CheckoutAbstractPage {

    public static final By CONTINUE_AS_GUEST_BUTTON = By.cssSelector(".btn-sa");
    public static final By EMAIL_TEXT_FIELD = By.id("email");
    public static final By SUBMIT_GUEST_EMAIL_BUTTON = By.id("tokonew-submit-btn");

    public static final By TITLE_DROPDOWN = By.id("CHECKOUT_CUSTOMER_INFO_title_1");
    public static final By FIRST_OPTION_IN_TITLE_DROPDOWN = By.xpath("//option[2]");
    public static final By CUSTOMER_FIRST_NAME_TEXT_FIELD = By.id("CHECKOUT_CUSTOMER_INFO_de_firstName5");
    public static final By CUSTOMER_LAST_NAME_TEXT_FIELD = By.id("CHECKOUT_CUSTOMER_INFO_de_lastName5");
    public static final By ENTER_SHIPPING_ADDRESS_MANUALLY_BUTTON = By.cssSelector("#CHECKOUT_SHIPPING_ADDRESS_manualInput > .col-lg-12 > .ng-scope .ng-scope");
    public static final By SHIPPING_ADDRESS_STREET_TEXTBOX = By.id("CHECKOUT_SHIPPING_ADDRESS_deline1_2_shipping");
    public static final By SHIPPING_ADDRESS_HOUSE_NR_TEXTBOX = By.id("CHECKOUT_SHIPPING_ADDRESS_deline2_1_small_shipping");
    public static final By SHIPPING_ADDRESS_POSTCODE_TEXTBOX = By.id("CHECKOUT_SHIPPING_ADDRESS_depostcode_3_medium_shipping");
    public static final By SHIPPING_ADDRESS_CITY_TEXTBOX = By.id("CHECKOUT_SHIPPING_ADDRESS_detownCity_1_shipping");
    public static final By CONTINUE_TO_PAYMENT_BUTTON = By.cssSelector(".btn > .ng-scope");
    private static final Map<String, By> CUSTOMER_PHONE_TEXT_FIELDS = Map.of(
            PRD, By.id("CHECKOUT_CUSTOMER_INFO_detelephone_digits_only"),
            STG2, By.id("CHECKOUT_CUSTOMER_INFO_detelephone"),
            STG3, By.id("CHECKOUT_CUSTOMER_INFO_detelephone")
    );

    @Override
    public void continueAsGuest(TestContext context, Map<String, Object> testData) {
        ClickUtils.click(context, CONTINUE_AS_GUEST_BUTTON, CLICKING_CONTINUE_AS_GUEST_BUTTON);
        FillUtils.enterText(context, EMAIL_TEXT_FIELD, TestDataUtils.getString(testData, Field.EMAIL), ENTERING_GUEST_EMAIL);
        ClickUtils.click(context, SUBMIT_GUEST_EMAIL_BUTTON, CLICKING_SUBMIT_GUEST_EMAIL_BUTTON);
        ReportUtils.info(context, "", true);
    }

    @Override
    public void enterContactInfo(TestContext context, Map<String, Object> testData) {
        ClickUtils.click(context, TITLE_DROPDOWN, CLICK_ON_TITLE_DROPDOWN);
        ClickUtils.click(context, FIRST_OPTION_IN_TITLE_DROPDOWN, CLICK_ON_MR_TITLE);
        FillUtils.enterText(context, CUSTOMER_FIRST_NAME_TEXT_FIELD, TestDataUtils.getString(testData, Field.FIRST_NAME), ENTERING_CUSTOMER_FIRST_NAME);
        FillUtils.enterText(context, CUSTOMER_LAST_NAME_TEXT_FIELD, TestDataUtils.getString(testData, Field.LAST_NAME), ENTERING_CUSTOMER_LAST_NAME);
        FillUtils.enterText(context, CUSTOMER_PHONE_TEXT_FIELDS.get(TestDataUtils.getString(testData, Field.HYBRIS_ENVIRONMENT)), TestDataUtils.getString(testData, Field.PHONE_NUMBER), ENTERING_CUSTOMER_PHONE_NUMBER);
        ReportUtils.info(context, "", true);
    }

    @Override
    public void enterShippingAddress(TestContext context, Map<String, Object> testData) {
        ClickUtils.click(context, ENTER_SHIPPING_ADDRESS_MANUALLY_BUTTON, OPTING_TO_ENTER_SHIPPING_ADDRESS_MANUALLY);
        FillUtils.enterText(context, SHIPPING_ADDRESS_STREET_TEXTBOX, TestDataUtils.getString(testData, Field.SHIPPING_STREET), ENTERING_SHIPPING_STREET);
        FillUtils.enterText(context, SHIPPING_ADDRESS_HOUSE_NR_TEXTBOX, TestDataUtils.getString(testData, Field.SHIPPING_HOUSE_NUMBER), ENTERING_SHIPPING_HOUSE_NUMBER);
        FillUtils.enterText(context, SHIPPING_ADDRESS_POSTCODE_TEXTBOX, TestDataUtils.getString(testData, Field.SHIPPING_POSTCODE), ENTERING_SHIPPING_POSTCODE);
        FillUtils.enterText(context, SHIPPING_ADDRESS_CITY_TEXTBOX, TestDataUtils.getString(testData, Field.SHIPPING_CITY), ENTERING_SHIPPING_CITY);
        ReportUtils.info(context, "", true);
    }

    @Override
    public void continueToPayment(TestContext context) {
        ClickUtils.click(context, CONTINUE_TO_PAYMENT_BUTTON, CLICKING_CONTINUE_TO_PAYMENT_BUTTON);
    }
}
