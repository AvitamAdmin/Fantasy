package com.avitam.fantasy11.web.controllers.admin.advertise;

import com.avitam.fantasy11.core.service.CoreService;
import com.avitam.fantasy11.form.BannerForm;
import com.avitam.fantasy11.model.Banner;
import com.avitam.fantasy11.model.BannerRepository;
import com.avitam.fantasy11.model.Script;
import com.avitam.fantasy11.model.ScriptRepository;
import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
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
@RequestMapping("/advertise")
public class AdvertiseController {
    @Autowired
    private BannerRepository bannerRepository;
    @Autowired
    private ScriptRepository scriptRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CoreService coreService;


    @GetMapping("/banner")
    public String getAllBanner(Model model) {
        List<Banner> bannerList = bannerRepository.findAll();

        model.addAttribute("banners", bannerList);
        return "advertise/banner";
    }
    @GetMapping("/script")
    public String getAllScript(Model model) {
        List<Script> scriptList = scriptRepository.findAll();
        model.addAttribute("script", scriptList);
        return "advertise/scriptAdvertise";
    }
    @GetMapping("/edit")
    public String edit(@RequestParam("id") String id, Model model) {

        Optional<Banner> bannerOptional = bannerRepository.findById(id);
        if (bannerOptional.isPresent()) {
            Banner banner = bannerOptional.get();
            BannerForm bannerForm = modelMapper.map(banner, BannerForm.class);
            bannerForm.setId(String.valueOf(banner.getId()));
            model.addAttribute("editForm", bannerForm);
        }
        return "banner/edit";
    }

    @PostMapping("/edit")
    public String handleEdit(@ModelAttribute("editForm") BannerForm bannerForm, Model model, BindingResult result) throws IOException {

        if (result.hasErrors()) {
            model.addAttribute("message", result);
            model.addAttribute("editForm", bannerForm);
            return "websitesetting/edit";
        }
        bannerForm.setLastModified(new Date());
        if (bannerForm.getId() == null) {
            bannerForm.setCreationTime(new Date());
            bannerForm.setCreator(coreService.getCurrentUser().getEmail());
        }
        Banner banner = modelMapper.map(bannerForm, Banner.class);
        MultipartFile logo = bannerForm.getLogo();
        String imagePath = System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "static" + File.separator + "images" + File.separator;
        if (StringUtils.isNotEmpty(logo.getOriginalFilename())) {
            Path path = Paths.get(imagePath + "logo.png");
            Files.copy(logo.getInputStream(), path,
                    StandardCopyOption.REPLACE_EXISTING);
            banner.setLogoUrl("logo.png");
        }

        MultipartFile favicon = bannerForm.getFavicon();
        if (StringUtils.isNotEmpty(favicon.getOriginalFilename())) {
            Path path = Paths.get(imagePath + "favicon.png");
            Files.copy(logo.getInputStream(), path,
                    StandardCopyOption.REPLACE_EXISTING);
            banner.setFaviconUrl("favicon.png");
        }
        bannerRepository.save(banner);
        model.addAttribute("editForm", bannerForm);

        return "redirect:/advertise/banner";
    }

    @GetMapping({"/banner/add","/addAdvertise"})
    public String add(Model model) {
        BannerForm form = new BannerForm();
        form.setCreationTime(new Date());
        form.setLastModified(new Date());
        form.setStatus(true);
        form.setCreator(coreService.getCurrentUser().getEmail());

        model.addAttribute("editForm", form);
        return "advertise/edit";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("id") String ids, Model model) {
        for (String id : ids.split(",")) {
            bannerRepository.deleteById(new ObjectId(id));
        }
        return "redirect:/advertise/banner";
    }
}
