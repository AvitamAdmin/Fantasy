package com.avitam.fantasy11.qa.utils;

import com.avitam.fantasy11.qa.framework.TestContext;
import org.apache.commons.lang3.StringUtils;

import static com.avitam.fantasy11.qa.utils.CheilStringUtils.*;


public class OpenPageUtils {
    public static void openUrl(TestContext context, String url, String pageName) {
        String localizedPageName = context.getLocalizedString(pageName);
        try {
            ReportUtils.info(context,
                    String.format(OPENING_PAGE_MESSAGE,
                            context.getLocalizedString(OPENING_PAGE),
                            localizedPageName.toUpperCase()),
                    false);

            ReportUtils.info(context,
                    String.format(URL_INFO_MESSAGE, url),
                    false);

            context.getDriver().get(url);

            ReportUtils.info(context,
                    String.format(OPENED_MESSAGE,
                            context.getLocalizedString(OPENED),
                            localizedPageName),
                    true);
        } catch (Exception e) {
            ReportUtils.fail(context,
                    String.format(ERROR_MESSAGE_OPENING_URL,
                            context.getLocalizedString(ERROR_OPENING_URL_FAILED),
                            localizedPageName,
                            url),
                    e.getMessage(),
                    true);
        }
    }

    public static String constructUrl(String env, String url, String environmentField) {
        if (!StringUtils.equals(env, TestDataUtils.PRD)) {
            return url.replace(environmentField, env);
        }
        return url.replace(environmentField + PERIOD, EMPTY_STRING);
    }
}
