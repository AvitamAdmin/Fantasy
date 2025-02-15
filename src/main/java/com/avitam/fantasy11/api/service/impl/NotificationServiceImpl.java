package com.avitam.fantasy11.api.service.impl;

import com.avitam.fantasy11.api.dto.NotificationDto;
import com.avitam.fantasy11.api.dto.NotificationWsDto;
import com.avitam.fantasy11.api.service.BaseService;
import com.avitam.fantasy11.api.service.NotificationService;
import com.avitam.fantasy11.model.Notification;
import com.avitam.fantasy11.repository.EntityConstants;
import com.avitam.fantasy11.repository.NotificationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {
    public static final String ADMIN_NOTIFICATION = "/admin/notification";
    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private BaseService baseService;

    @Override
    public Notification findByRecordId(String recordId) {
        return notificationRepository.findByRecordId(recordId);
    }

    @Override
    public NotificationWsDto handleEdit(NotificationWsDto request) {

        Notification notification = null;
        List<NotificationDto> notificationDtos = request.getNotificationDtoList();
        List<Notification> notifications = new ArrayList<>();

        for (NotificationDto notificationDto1 : notificationDtos) {
            if (notificationDto1.getRecordId() != null) {
                notification = notificationRepository.findByRecordId(notificationDto1.getRecordId());
                modelMapper.map(notificationDto1, notification);
                notification.setLastModified(new Date());
                notificationRepository.save(notification);
                request.setMessage("Data updated Successfully");
            } else {
                if (baseService.validateIdentifier(EntityConstants.NOTIFICATION, notificationDto1.getIdentifier()) != null) {
                    request.setMessage("Identifier already present");
                    request.setSuccess(false);
                    return request;
                }
                notification = modelMapper.map(notificationDto1, Notification.class);
                baseService.populateCommonData(notification);
                notificationRepository.save(notification);

                if (notification.getRecordId() == null) {
                    notification.setRecordId(String.valueOf(notification.getId().getTimestamp()));
                }
                notificationRepository.save(notification);
                request.setMessage("Notification added successfully");
            }
            notifications.add(notification);
            request.setBaseUrl(ADMIN_NOTIFICATION);
        }
        request.setNotificationDtoList(modelMapper.map(notifications, List.class));
        return request;

    }

    @Override
    public void deleteByRecordId(String recordId) {
        notificationRepository.deleteByRecordId(recordId);
    }

    @Override
    public void updateByRecordId(String recordId) {
        Notification notification = notificationRepository.findByRecordId(recordId);
        if (notification != null) {
            notificationRepository.save(notification);
        }
    }
}
