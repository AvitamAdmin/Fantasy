package com.avitam.fantasy11.qa.factories;

import com.avitam.fantasy11.qa.pages.abstractpages.SamsungTestConfigAbstractPage;
import com.avitam.fantasy11.qa.pages.concretepages.SamsungTestConfigPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class SamsungTestConfigPageFactory {

    private final ApplicationContext context;

    @Autowired
    public SamsungTestConfigPageFactory(ApplicationContext context) {
        this.context = context;
    }

    public SamsungTestConfigAbstractPage getSamsungTestConfigPage() {
        return context.getBean(SamsungTestConfigPage.class);
    }


}
