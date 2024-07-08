package com.avitam.fantasy11.web.controllers.admin.kyc;

import com.avitam.fantasy11.model.NodeRepository;
import com.avitam.fantasy11.model.SportType;
import com.avitam.fantasy11.validation.KYCFormValidator;
import org.springframework.beans.factory.annotation.Autowired;
import com.avitam.fantasy11.core.service.CoreService;
import com.avitam.fantasy11.form.KYCForm;
import com.avitam.fantasy11.model.KYC;
import com.avitam.fantasy11.model.KYCRepository;
import org.bson.types.Binary;
import org.bson.types.ObjectId;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin/kyc")
public class KYCController {

    @Autowired
    private KYCRepository kycRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CoreService coreService;

    @GetMapping
    public String getAllModels(Model model) {
        List<KYC> kycs = kycRepository.findAll();
        List<KYC> datas = new ArrayList<>();
        for (KYC data : kycs) {
            if (data.getId() != null) {
                byte[] image = data.getPanImage().getData();
                data.setPic(Base64.getEncoder().encodeToString(image));
                datas.add(data);
            }
        }
            model.addAttribute("models", datas);
            return "kyc/kycs";
        }

    @GetMapping("/edit")
    public String editKyc(@RequestParam("id") String id, Model model) {

        Optional<KYC> kycOptional = kycRepository.findById(id);
        if (kycOptional.isPresent()) {
            KYC kyc = kycOptional.get();
            KYCForm kycForm = modelMapper.map(kyc, KYCForm.class);
            kycForm.setId(String.valueOf(kyc.getId()));
            model.addAttribute("editForm", kycForm);
        }
        return "kyc/edit";
    }


    @PostMapping("/edit")
    public String handleEdit(@ModelAttribute("editForm") KYCForm kycForm, Model model, BindingResult result) throws IOException {
       new KYCFormValidator().validate(kycForm,result);
        if (result.hasErrors()) {
            model.addAttribute("message", result);
            return "kyc/edit";
        }

        byte[] img = kycForm.getPanImage().getBytes();
        Binary binary = new Binary(img);

        kycForm.setLastModified(new Date());
        if (kycForm.getId() == null) {
            kycForm.setCreationTime(new Date());
            kycForm.setCreator(coreService.getCurrentUser().getEmail());
        }

        KYC kyc = modelMapper.map(kycForm, KYC.class);
        Optional<KYC> kycOptional = kycRepository.findById(kycForm.getId());
        if (kycOptional.isPresent()) {
            kyc.setId(kycOptional.get().getId());
        }

        kyc.setPanImage(binary);
        kycRepository.save(kyc);
        model.addAttribute("editForm", kycForm);
        return "redirect:/admin/kyc";
    }

    @GetMapping("/add")
    public String addKyc(Model model) {
        KYCForm form = new KYCForm();
        form.setCreationTime(new Date());
        form.setLastModified(new Date());
        form.setStatus(true);
        form.setCreator(coreService.getCurrentUser().getEmail());
        model.addAttribute("editForm", form);
        return "kyc/edit";
    }

    @GetMapping("/delete")
    public String deleteKyc(@RequestParam("id") String ids, Model model) {

        for (String id : ids.split(",")) {
           kycRepository.deleteById(new ObjectId(id));
        }

        return "redirect:/admin/kyc";
    }
}
