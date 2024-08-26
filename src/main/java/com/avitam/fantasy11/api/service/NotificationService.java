package com.avitam.fantasy11.api.service;

import com.avitam.fantasy11.model.MobileToken;
import com.avitam.fantasy11.model.Notification;

import java.util.Optional;

public interface NotificationService {


    Optional<Notification> findByRecordId(String recordId);

    void deleteByRecordId(String recordId);

    void updateByRecordId(String recordId);
}
