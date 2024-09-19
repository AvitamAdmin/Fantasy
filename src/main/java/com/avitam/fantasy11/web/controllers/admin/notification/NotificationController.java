package com.avitam.fantasy11.web.controllers.admin.notification;

import com.avitam.fantasy11.api.dto.NotificationDto;
import com.avitam.fantasy11.api.service.NotificationService;
import com.avitam.fantasy11.model.*;
import com.avitam.fantasy11.web.controllers.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;

@Controller
@RequestMapping("/admin/notification")
public class NotificationController extends BaseController {

    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private NotificationService notificationService;
    public static final String ADMIN_NOTIFICATION = "/admin/notification";

    @PostMapping
    @ResponseBody
    public NotificationDto getAllNotifications(NotificationDto notificationDto) {
        Pageable pageable=getPageable(notificationDto.getPage(),notificationDto.getSizePerPage(),notificationDto.getSortDirection(),notificationDto.getSortField());
        Notification notification=notificationDto.getNotification();
        Page<Notification> page=isSearchActive(notification)!=null ? notificationRepository.findAll(Example.of(notification),pageable) : notificationRepository.findAll(pageable);
        notificationDto.setNotificationList(page.getContent());
        notificationDto.setTotalPages(page.getTotalPages());
        notificationDto.setTotalRecords(page.getTotalElements());
        notificationDto.setBaseUrl(ADMIN_NOTIFICATION);
        return notificationDto;

    }

    @GetMapping("/get")
    @ResponseBody
    public NotificationDto getActiveNotificationList(){
        NotificationDto notificationDto = new NotificationDto();
        notificationDto.setNotificationList(notificationRepository.findByStatusOrderByIdentifier(true));
        notificationDto.setBaseUrl(ADMIN_NOTIFICATION);
        return notificationDto;
    }

    @PostMapping("/getedit")
    @ResponseBody
    public NotificationDto editNotification(@RequestBody NotificationDto request) {
        NotificationDto notificationDto = new NotificationDto();

        Notification notification = notificationRepository.findByRecordId(request.getRecordId());
        notificationDto.setNotification(notification);
        notificationDto.setBaseUrl(ADMIN_NOTIFICATION);
        return notificationDto;
    }

    @PostMapping("/edit")
    public NotificationDto handleEdit(@RequestBody NotificationDto request) {
        return notificationService.handleEdit(request);
    }

    @GetMapping("/add")
    @ResponseBody
    public NotificationDto addNotification(Model model) {
        NotificationDto notificationDto = new NotificationDto();
        notificationDto.setNotificationList(notificationRepository.findByStatusOrderByIdentifier(true));
        notificationDto.setBaseUrl(ADMIN_NOTIFICATION);
        return notificationDto;
    }

    @PostMapping("/delete")
    @ResponseBody
    public NotificationDto deleteNotification(@RequestBody NotificationDto notificationDto) {
        for (String id : notificationDto.getRecordId().split(",")) {
            notificationRepository.deleteByRecordId(id);
        }
        notificationDto.setMessage("Data deleted successfully");
        notificationDto.setBaseUrl(ADMIN_NOTIFICATION);
        return notificationDto;
    }
}
