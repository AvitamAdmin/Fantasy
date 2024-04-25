package com.avitam.fantasy11.core.event;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

import java.util.Locale;

@Getter
@Setter
public class OnRegistrationCompleteEvent extends ApplicationEvent {
    private String appUrl;
    private Locale locale;
    private UserTM user;

    private String subject;

    private String content;

    private String toAddress;

    private String level;

    public OnRegistrationCompleteEvent(
            UserTM user, Locale locale, String appUrl, String subject, String content, String toAddress, String level) {
        super(user);

        this.user = user;
        this.locale = locale;
        this.appUrl = appUrl;
        this.subject = subject;
        this.content = content;
        this.toAddress = toAddress;
        this.level = level;
    }
}
