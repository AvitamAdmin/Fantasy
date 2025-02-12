package com.avitam.fantasy11.web.controllers;

import com.avitam.fantasy11.core.service.NodeService;
import com.avitam.fantasy11.core.service.UserService;
import com.avitam.fantasy11.model.WebsiteSetting;
import com.avitam.fantasy11.repository.WebsiteSettingRepository;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.UUID;

@RestController
public class HomeController {

    @Autowired
    private NodeService nodeService;
    @Autowired
    private UserService userService;


    @Autowired
    private WebsiteSettingRepository websiteSettingRepository;

    @GetMapping("/home")
    public ModelAndView home(HttpSession session, Model model) {
        model.addAttribute("nodes", userService.isAdminRole() ? nodeService.getAllNodes() : nodeService.getNodesForRoles());
        String currentUserSession = (String) session.getAttribute("currentUserSession");
        UUID uuid = UUID.randomUUID();
        model.addAttribute("currentUserSession", StringUtils.isNotEmpty(currentUserSession) ? currentUserSession : uuid.toString());
        List<WebsiteSetting> siteSettings = websiteSettingRepository.findAll();
        if (CollectionUtils.isNotEmpty(siteSettings)) {
            model.addAttribute("siteSetting", siteSettings.get(0));
        }
        return new ModelAndView("home");
    }

}