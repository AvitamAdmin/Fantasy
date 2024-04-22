package com.avitam.fantasy11.mail;

import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;

import java.util.Date;

public interface BackendMailAdapter {

    @Retryable(value = {EmailNotRecievedException.class}, maxAttempts = 12, backoff = @Backoff(delay = 5000))
    public String getBackendResponse(Date currentDate, boolean onlyNewMessages) throws EmailNotRecievedException;

    @Recover
    public String getBackendResponseFallback(RuntimeException e);

}
