package com.avitam.fantasy11.web.controllers.admin.advertise;

import com.avitam.fantasy11.core.service.CoreService;
import com.avitam.fantasy11.form.BannerForm;
import com.avitam.fantasy11.model.*;
import org.apache.commons.lang3.StringUtils;
import org.bson.types.Binary;
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
import java.security.PrivateKey;
import java.util.*;

@Controller
@RequestMapping("/advertise/banner")
public class AdvertiseController {
    @Autowired
    private BannerRepository bannerRepository;
    @Autowired
    private ScriptRepository scriptRepository;
    @Autowired
    private BannerSizeRepository bannerSizeRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CoreService coreService;


    @GetMapping
    public String getAllBanner(Model model){
        List<Banner> datas=new ArrayList<>();
        List<Banner> banners = bannerRepository.findAll();
        for(Banner banner:banners){
            if(banner.getId()!=null) {
                byte[] image = banner.getImage().getData();
                banner.setPic(Base64.getEncoder().encodeToString(image));
                datas.add(banner);
            }
        }
        model.addAttribute("banners", datas);
        return "advertise/banners";
    }

    @GetMapping("/edit")
    public String edit(@RequestParam("id") String id, Model model) {

        Optional<Banner> bannerOptional = bannerRepository.findById(id);
        if (bannerOptional.isPresent()) {
            Banner banner = bannerOptional.get();
            BannerForm bannerForm = modelMapper.map(banner, BannerForm.class);
            bannerForm.setId(String.valueOf(banner.getId()));

            byte[] image = banner.getImage().getData();
            banner.setPic(Base64.getEncoder().encodeToString(image));
            bannerForm.setPic(banner.getPic());

            model.addAttribute("editForm", bannerForm);
            model.addAttribute("sizes",bannerSizeRepository.findAll());
        }
        return "advertise/edit";
    }

    @PostMapping("/edit")
    public String handleEdit(@ModelAttribute("editForm") BannerForm bannerForm, Model model, BindingResult result) throws IOException {

        if (result.hasErrors()) {
            model.addAttribute("message", result);
            model.addAttribute("editForm", bannerForm);
            return "advertise/edit";
        }

        byte[] fig= bannerForm.getImage().getBytes();
        Binary binary=new Binary(fig);

        bannerForm.setLastModified(new Date());
        if (bannerForm.getId() == null) {
            bannerForm.setCreationTime(new Date());
            bannerForm.setCreator(coreService.getCurrentUser().getEmail());
        }
        Banner banner = modelMapper.map(bannerForm, Banner.class);

        Optional<Banner> bannerOptional=bannerRepository.findById(bannerForm.getId());
        if(bannerOptional.isPresent()){
            banner.setId(bannerOptional.get().getId());
        }

        banner.setImage(binary);

        bannerRepository.save(banner);
        model.addAttribute("editForm", bannerForm);

        return "redirect:/advertise/banner";
    }

    @GetMapping("/add")
    public String add(Model model) {
        BannerForm form = new BannerForm();
        form.setCreationTime(new Date());
        form.setLastModified(new Date());
        form.setStatus(true);
        form.setCreator(coreService.getCurrentUser().getEmail());
        model.addAttribute("editForm", form);
        model.addAttribute("sizes",bannerSizeRepository.findAll());
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
