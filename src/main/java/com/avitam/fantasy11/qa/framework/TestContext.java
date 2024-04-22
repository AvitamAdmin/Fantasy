package com.avitam.fantasy11.qa.framework;

import com.aventstack.extentreports.ExtentTest;
import lombok.Data;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Scope;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;


@Data
@Component
@Scope("prototype")
public class TestContext {
    public WebDriver driver;
    public WebDriverWait webDriverWait;
    public ExtentTest extentTest;
    public int testIdentifier; //allows each test to identify SKUs or other test-specific data amongst other tests running in parallel

    @Autowired
    private MessageSource messageSource;

    public String getLocalizedString(String key) {
        return messageSource.getMessage(key, null, LocaleContextHolder.getLocale());
    }
}
