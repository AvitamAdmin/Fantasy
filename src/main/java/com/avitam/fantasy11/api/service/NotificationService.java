package com.avitam.fantasy11.api.service;

import com.avitam.fantasy11.model.MobileToken;
import com.avitam.fantasy11.model.Notification;

public interface NotificationService {


    Notification findByMobileNumber(String mobileNumber);

    void save(Notification notify);

    Notification deleteById(String id);

    Notification updateMobileNumber(String mobileNumber);
}
