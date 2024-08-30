package com.avitam.fantasy11.web.controllers.admin.notification;

import com.avitam.fantasy11.core.service.CoreService;
import com.avitam.fantasy11.form.NotificationForm;
import com.avitam.fantasy11.model.*;
//import com.avitam.fantasy11.validation.AddressFormValidator;
//import com.avitam.fantasy11.validation.NotificationFormValidator;
import org.bson.types.ObjectId;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/notification")
public class NotificationController {

    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private NodeRepository nodeRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CoreService coreService;

    @GetMapping
    public String getAllModels(Model model) {
        model.addAttribute("models", notificationRepository.findAll());
        return "notification/notifications";
    }

    @GetMapping("/edit")
    public String editNotification(@RequestParam("id") String id, Model model) {

        Optional<Notification> notificationOptional = notificationRepository.findByRecordId(id);
        if (notificationOptional.isPresent()) {
            Notification notification = notificationOptional.get();
            NotificationForm notificationForm = modelMapper.map(notification, NotificationForm.class);
            notificationForm.setId(String.valueOf(notification.getId()));
            model.addAttribute("editForm", notificationForm);
        }
        return "notification/edit";
    }

    @PostMapping("/edit")
    public String handleEdit(@ModelAttribute("editForm") NotificationForm notificationForm, Model model, BindingResult result) {
        if (result.hasErrors()) {
            model.addAttribute("message", result);
            return "notification/edit";
        }
            notificationForm.setLastModified(new Date());
        if (notificationForm.getId() == null) {
            notificationForm.setCreationTime(new Date());
            notificationForm.setCreator(coreService.getCurrentUser().getEmail());
            notificationForm.setMobileNumber(coreService.getCurrentUser().getMobileNumber());
        }
        Notification notification = modelMapper.map(notificationForm, Notification.class);

        Optional<Notification> notificationOptional = notificationRepository.findById(notificationForm.getId());
        if(notificationOptional.isPresent()){
            notification.setId(notificationOptional.get().getId());
        }

        notificationRepository.save(notification);
        if(notification.getRecordId()==null)
        {
            notification.setRecordId(String.valueOf(notification.getId().getTimestamp()));
        }
        notificationRepository.save(notification);
        model.addAttribute("editForm", notificationForm);
        return "redirect:/admin/notification";
    }

    @GetMapping("/add")
    public String addNotification(Model model) {
        NotificationForm form = new NotificationForm();
        form.setCreationTime(new Date());
        form.setLastModified(new Date());
        form.setStatus(true);
        form.setCreator(coreService.getCurrentUser().getEmail());
        model.addAttribute("editForm", form);
        return "notification/edit";
    }

    @GetMapping("/delete")
    public String deleteNotification(@RequestParam("id") String ids, Model model) {
        for (String id : ids.split(",")) {

            notificationRepository.deleteByRecordId(id);
        }
        return "redirect:/admin/notification";
    }
}
