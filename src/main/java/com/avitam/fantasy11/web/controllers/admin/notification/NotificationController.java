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
import java.util.Optional;

@Controller
@RequestMapping("/admin/notification")
public class NotificationController {

    @Autowired
    NotificationRepository notificationRepository;
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
        //model.addAttribute("models", addressRepository.findAll().stream().filter(address -> address.getUserId() != null).collect(Collectors.toList()));
        model.addAttribute("models", notificationRepository.findAll());
        return "notification/notifications";
    }

    @GetMapping("/edit")
    public String editNotification(@RequestParam("id") ObjectId id, Model model) {
        NotificationForm notificationForm = null;
        Optional<Notification> notificationOptional = notificationRepository.findById(id);
        if (notificationOptional.isPresent()) {
            Notification notification = notificationOptional.get();
            notificationForm = modelMapper.map(notification, NotificationForm.class);
            model.addAttribute("editForm", notificationForm);
        }
        //model.addAttribute("nodes", addressRepository.findAll().stream().filter(node -> node.getParentNode() == null).collect(Collectors.toList()));
        return "notification/edit";
    }

    @PostMapping("/edit")
    public String handleEdit(@ModelAttribute("editForm") NotificationForm notificationForm, Model model, BindingResult result) {
       // new NotificationFormValidator().validate(notificationForm, result);
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

       /* if (StringUtils.isNotEmpty(interfaceForm.getParentNodeId())) {
            node.setParentNode(nodeRepository.getByIds(Long.valueOf(interfaceForm.getParentNodeId())));
        }*/

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
        form.setMobileNumber(coreService.getCurrentUser().getMobileNumber());
        model.addAttribute("editForm", form);
        return "notification/edit";
    }

    @GetMapping("/delete")
    public String deleteNotification(@RequestParam("id") String ids, Model model) {
        for (String id : ids.split(",")) {
            notificationRepository.deleteById(new ObjectId(id));
        }
        return "redirect:/admin/notification";
    }
}
