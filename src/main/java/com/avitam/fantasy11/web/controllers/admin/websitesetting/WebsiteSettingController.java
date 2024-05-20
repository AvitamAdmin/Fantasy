package com.avitam.fantasy11.web.controllers.admin.websitesetting;

import com.avitam.fantasy11.model.WebsiteSetting;
import com.avitam.fantasy11.model.WebsiteSettingRepository;
import com.avitam.fantasy11.core.service.CoreService;
import com.avitam.fantasy11.form.WebsiteSettingForm;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/websitesetting")
public class WebsiteSettingController {

    @Autowired
    private WebsiteSettingRepository websiteSettingRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CoreService coreService;


    @GetMapping
    public String getAll(Model model) {
        List<WebsiteSetting> websiteSettings = websiteSettingRepository.findAll();
        model.addAttribute("models", websiteSettings);
        return "websitesetting/websitesettings";
    }

    @GetMapping("/edit")
    public String edit(@RequestParam("id") Long id, Model model) {
        Optional<WebsiteSetting> websiteSettingOptional = websiteSettingRepository.findById(id);
        if (websiteSettingOptional.isPresent()) {
            WebsiteSetting websitesetting = websiteSettingOptional.get();
            WebsiteSettingForm websiteSettingForm = modelMapper.map(websitesetting, WebsiteSettingForm.class);
            model.addAttribute("editForm", websiteSettingForm);
        }

        return "websitesetting/edit";
    }

    @PostMapping("/edit")
    public String handleEdit(@ModelAttribute("editForm") WebsiteSettingForm websiteSettingForm, Model model, BindingResult result) throws IOException {

        if (result.hasErrors()) {
            model.addAttribute("message", result);
            model.addAttribute("editForm", websiteSettingForm);
            return "websitesetting/edit";
        }
        websiteSettingForm.setLastModified(new Date());
        if (websiteSettingForm.getId() == null) {
            websiteSettingForm.setCreationTime(new Date());
            websiteSettingForm.setCreator(coreService.getCurrentUser().getUsername());
        }
        WebsiteSetting websitesetting = modelMapper.map(websiteSettingForm, WebsiteSetting.class);
        MultipartFile logo = websiteSettingForm.getLogo();
        String imagePath = System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "static" + File.separator + "images" + File.separator;
        if (StringUtils.isNotEmpty(logo.getOriginalFilename())) {
            Path path = Paths.get(imagePath + "logo.png");
            Files.copy(logo.getInputStream(), path,
                    StandardCopyOption.REPLACE_EXISTING);
            websitesetting.setLogoUrl("logo.png");
        }

        MultipartFile favicon = websiteSettingForm.getFavicon();
        if (StringUtils.isNotEmpty(favicon.getOriginalFilename())) {
            Path path = Paths.get(imagePath + "favicon.png");
            Files.copy(logo.getInputStream(), path,
                    StandardCopyOption.REPLACE_EXISTING);
            websitesetting.setFaviconUrl("favicon.png");
        }
        websiteSettingRepository.save(websitesetting);
        model.addAttribute("editForm", websiteSettingForm);

        return "redirect:/admin/websitesetting";
    }

    @GetMapping("/add")
    public String add(Model model) {
        WebsiteSettingForm form = new WebsiteSettingForm();
        form.setCreationTime(new Date());
        form.setLastModified(new Date());
        form.setStatus(true);
        form.setCreator(coreService.getCurrentUser().getUsername());

        model.addAttribute("editForm", form);
        return "websitesetting/edit";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("id") String ids, Model model) {
        for (String id : ids.split(",")) {
            websiteSettingRepository.deleteById(Long.valueOf(id));
        }
        return "redirect:/admin/websitesetting";
    }
}
