package com.avitam.fantasy11.web.controllers.admin.notification;

import com.avitam.fantasy11.api.dto.NotificationDto;
import com.avitam.fantasy11.api.dto.NotificationWsDto;
import com.avitam.fantasy11.api.dto.SearchDto;
import com.avitam.fantasy11.api.service.NotificationService;
import com.avitam.fantasy11.model.Notification;
import com.avitam.fantasy11.repository.NotificationRepository;
import com.avitam.fantasy11.web.controllers.BaseController;
import org.apache.commons.collections4.CollectionUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/notification")
public class NotificationController extends BaseController {

    public static final String ADMIN_NOTIFICATION = "/admin/notification";
    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    @ResponseBody
    public NotificationWsDto getAllNotifications(NotificationWsDto notificationWsDto) {
        Pageable pageable = getPageable(notificationWsDto.getPage(), notificationWsDto.getSizePerPage(), notificationWsDto.getSortDirection(), notificationWsDto.getSortField());
        NotificationDto notificationDto = CollectionUtils.isNotEmpty(notificationWsDto.getNotificationDtoList()) ? notificationWsDto.getNotificationDtoList().get(0) : new NotificationDto();
        Notification notification = modelMapper.map(notificationDto, Notification.class);
        Page<Notification> page = isSearchActive(notification) != null ? notificationRepository.findAll(Example.of(notification), pageable) : notificationRepository.findAll(pageable);
        notificationWsDto.setNotificationDtoList(modelMapper.map(page.getContent(), List.class));
        notificationWsDto.setTotalPages(page.getTotalPages());
        notificationWsDto.setTotalRecords(page.getTotalElements());
        notificationWsDto.setBaseUrl(ADMIN_NOTIFICATION);
        return notificationWsDto;
    }

    @GetMapping("/get")
    @ResponseBody
    public NotificationWsDto getActiveNotificationList() {
        NotificationWsDto notificationWsDto = new NotificationWsDto();
        notificationWsDto.setNotificationDtoList(modelMapper.map(notificationRepository.findByStatusOrderByIdentifier(true), List.class));
        notificationWsDto.setBaseUrl(ADMIN_NOTIFICATION);
        return notificationWsDto;
    }

    @PostMapping("/getedit")
    @ResponseBody
    public NotificationWsDto editNotification(@RequestBody NotificationWsDto request) {
        NotificationWsDto notificationWsDto = new NotificationWsDto();
        notificationWsDto.setBaseUrl(ADMIN_NOTIFICATION);
        Notification notification = notificationRepository.findByRecordId(request.getNotificationDtoList().get(0).getRecordId());
        if (notification != null) {
            notificationWsDto.setNotificationDtoList(List.of(modelMapper.map(notification, NotificationDto.class)));
        }
        return notificationWsDto;
    }

    @PostMapping("/edit")
    @ResponseBody
    public NotificationWsDto handleEdit(@RequestBody NotificationWsDto request) {
        return notificationService.handleEdit(request);
    }

    @PostMapping("/delete")
    @ResponseBody
    public NotificationWsDto deleteNotification(@RequestBody NotificationWsDto notificationWsDto) {
        for (NotificationDto notificationDto : notificationWsDto.getNotificationDtoList()) {
            notificationRepository.deleteByRecordId((notificationDto.getRecordId()));

        }
        notificationWsDto.setBaseUrl(ADMIN_NOTIFICATION);
        notificationWsDto.setMessage("Data deleted successfully");
        return notificationWsDto;

    }

    @GetMapping("/getAdvancedSearch")
    @ResponseBody

    public List<SearchDto> getSearchAttributes() {
        return getGroupedParentAndChildAttributes(new Notification());
    }

}
