package com.avitam.fantasy11.web.controllers.admin.mobiletoken;

import com.avitam.fantasy11.core.service.CoreService;
import com.avitam.fantasy11.form.InterfaceForm;
import com.avitam.fantasy11.form.MobileTokenForm;
import com.avitam.fantasy11.form.TeamForm;
import com.avitam.fantasy11.model.*;
import com.avitam.fantasy11.validation.InterfaceFormValidator;
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
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin/mobileToken")
public class MobileTokenController {
    @Autowired
    private MobileTokenRepository mobileTokenRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CoreService coreService;

    @GetMapping
    public String getAll(Model model) {
        List<MobileToken> mobileTokens= mobileTokenRepository.findAll().stream().filter(mobile-> mobile.getId()!=null).collect(Collectors.toList());
        model.addAttribute("tokens", mobileTokens);
        return "mobileToken/mobileTokens";
    }

    @GetMapping("/edit")
    public String edit(@RequestParam("id") ObjectId id, Model model) {

        Optional<MobileToken> mobileTokenOptional = mobileTokenRepository.findById(id);
        if (mobileTokenOptional.isPresent()) {
            MobileToken mobileToken = mobileTokenOptional.get();
            MobileTokenForm mobileTokenForm = modelMapper.map(mobileToken, MobileTokenForm.class);
            model.addAttribute("editForm", mobileTokenForm);
        }
        return "mobileToken/edit";
    }

    @PostMapping("/edit")
    public String handleEdit(@ModelAttribute("editForm") MobileTokenForm mobileTokenForm, Model model, BindingResult result) {
        if (result.hasErrors()) {
            model.addAttribute("message", result);
            model.addAttribute("editForm", mobileTokenForm);
            return "mobileToken/edit";
        }
        mobileTokenForm.setLastModified(new Date());
        if (mobileTokenForm.getId() == null) {
            mobileTokenForm.setCreationTime(new Date());
            mobileTokenForm.setCreator(coreService.getCurrentUser().getEmail());
        }
        MobileToken mobileToken=modelMapper.map(mobileTokenForm,MobileToken.class);
        Optional<MobileToken> teamOptional=mobileTokenRepository.findById(mobileTokenForm.getId());
        if(teamOptional.isPresent()){
            mobileToken.setId(teamOptional.get().getId());
        }
        mobileTokenRepository.save(mobileToken);
        model.addAttribute("editForm", mobileTokenForm);
        return "redirect:/admin/mobileToken";
    }

    @GetMapping("/add")
    public String addMobileToken(Model model) {
        MobileTokenForm form = new MobileTokenForm();
        form.setCreationTime(new Date());
        form.setLastModified(new Date());
        form.setStatus(true);
        form.setCreator(coreService.getCurrentUser().getEmail());
        model.addAttribute("editForm", form);
        return "mobileToken/edit";
    }

    @GetMapping("/delete")
    public String deleteMobileToken(@RequestParam("id") String ids, Model model) {
        for (String id : ids.split(",")) {
            mobileTokenRepository.deleteById(new ObjectId(id));
        }
        return "redirect:/admin/mobileToken";
    }
}
