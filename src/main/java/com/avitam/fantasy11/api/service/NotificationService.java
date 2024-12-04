package com.avitam.fantasy11.api.service;

import com.avitam.fantasy11.api.dto.NotificationWsDto;
import com.avitam.fantasy11.model.Notification;

public interface NotificationService {


    Notification findByRecordId(String recordId);

    NotificationWsDto handleEdit(NotificationWsDto request);

    void deleteByRecordId(String recordId);

    void updateByRecordId(String recordId);
}
