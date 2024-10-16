package com.avitam.fantasy11.api.service.impl;

import com.avitam.fantasy11.api.dto.NotificationDto;
import com.avitam.fantasy11.api.service.BaseService;
import com.avitam.fantasy11.api.service.NotificationService;
import com.avitam.fantasy11.core.service.CoreService;
import com.avitam.fantasy11.model.Notification;
import com.avitam.fantasy11.repository.EntityConstants;
import com.avitam.fantasy11.repository.NotificationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CoreService coreService;
    @Autowired
    private BaseService baseService;

    public static final String ADMIN_NOTIFICATION = "/admin/notification";

    @Override
    public Notification findByRecordId(String recordId) {
        return notificationRepository.findByRecordId(recordId);
    }

    @Override
    public NotificationDto handleEdit(NotificationDto request) {
        NotificationDto notificationDto = new NotificationDto();
        Notification notification = null;
        if(request.getRecordId()!=null){
            Notification requestData = request.getNotification();
            notification = notificationRepository.findByRecordId(request.getRecordId());
            modelMapper.map(requestData, notification);
        }
        else {
            if(baseService.validateIdentifier(EntityConstants.NOTIFICATION,request.getNotification().getIdentifier())!=null)
            {
                request.setSuccess(false);
                request.setMessage("Identifier already present");
                return request;
            }
            notification=request.getNotification();
        }
        baseService.populateCommonData(notification);
        notificationRepository.save(notification);
        if(request.getRecordId()==null){
            notification.setRecordId(String.valueOf(notification.getId().getTimestamp()));
        }
        notificationRepository.save(notification);
        notificationDto.setNotification(notification);
        notificationDto.setBaseUrl(ADMIN_NOTIFICATION);
        return notificationDto;
    }

    @Override
    public void deleteByRecordId(String recordId) {
        notificationRepository.deleteByRecordId(recordId);
    }

    @Override
    public void updateByRecordId(String recordId) {
        Notification notification=notificationRepository.findByRecordId(recordId);
        //notificationOptional.ifPresent(notification -> notificationRepository.save(notification));
    }
}
