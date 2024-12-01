package com.avitam.fantasy11.api.service;

import com.avitam.fantasy11.api.dto.NotificationDto;
import com.avitam.fantasy11.model.Notification;

public interface NotificationService {


    Notification findByRecordId(String recordId);

    NotificationDto handleEdit(NotificationDto request);

    void deleteByRecordId(String recordId);

    void updateByRecordId(String recordId);
}
