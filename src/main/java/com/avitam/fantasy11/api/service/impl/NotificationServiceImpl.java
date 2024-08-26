package com.avitam.fantasy11.api.service.impl;

import com.avitam.fantasy11.api.service.NotificationService;
import com.avitam.fantasy11.model.Notification;
import com.avitam.fantasy11.model.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class NotificationServiceImpl implements NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;
    @Override
    public Optional<Notification> findByRecordId(String recordId) {
        return notificationRepository.findByRecordId(recordId);
    }

    @Override
    public void deleteByRecordId(String recordId) {
        notificationRepository.deleteByRecordId(recordId);
    }

    @Override
    public void updateByRecordId(String recordId) {
        Optional<Notification> notificationOptional=notificationRepository.findByRecordId(recordId);
        notificationOptional.ifPresent(notification -> notificationRepository.save(notification));
    }
}
