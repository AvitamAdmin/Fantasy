package com.avitam.fantasy11.web.controllers.admin.kyc;

import com.avitam.fantasy11.model.NodeRepository;
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
import java.util.Date;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin/kyc")
public class KYCController {

    @Autowired
    private NodeRepository nodeRepository;
    @Autowired
    private KYCRepository kycRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CoreService coreService;

    @GetMapping
    public String getAllModels(Model model) {
        model.addAttribute("models", kycRepository.findAll().stream().filter(kyc -> kyc.getId() != null).collect(Collectors.toList()));
        return "kyc/kycs";
    }

    @GetMapping("/edit")
    public String editKyc(@RequestParam("id") KYCForm kycForm ,ObjectId id, Model model) {

        Optional<KYC> kycOptional = kycRepository.findById(id);
        if (kycOptional.isPresent()) {
            KYC kyc = kycOptional.get();
            kycForm = modelMapper.map(kyc, KYCForm.class);
            model.addAttribute("editForm", kycForm);
        }
        return "kyc/edit";
    }


    @PostMapping("/edit")
    public String handleEdit(@ModelAttribute("editForm") KYCForm kycForm, Model model, BindingResult result) throws IOException {
        // new KycFormValidator().validate(kycForm, result);
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
        Optional<KYC> kycOptional = kycRepository.findById(new ObjectId(kycForm.getId()));
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
        //   model.addAttribute("nodes", kycRepository.findAll().stream().filter(kyc -> kyc.getUserId()  == null).collect(Collectors.toList()));
        return "kyc/edit";
    }

    @GetMapping("/delete")
    public String deleteKyc(@RequestParam("id") ObjectId id, Model model) {
        //  for (String id : ids.split(",")) {
        //     nodeRepository.deleteById(Integer.valueOf(id));
        //  }
        kycRepository.deleteById(id);
        return "redirect:/admin/kyc";
    }
}


