package com.avitam.fantasy11.web.controllers.admin.deposits;

import com.avitam.fantasy11.core.service.CoreService;
import com.avitam.fantasy11.form.DepositsForm;
import com.avitam.fantasy11.model.*;
import org.bson.types.ObjectId;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("/deposit/depositLog")
public class DepositsLogController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DepositsRepository depositsRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CoreService coreService;

    @GetMapping
    public String getAllModels(Model model) {
        model.addAttribute("models", depositsRepository.findAll());
        return "deposit/depositLog";
    }

    @GetMapping("/edit")
    public String edit(@RequestParam("id") String id, Model model) {

        Optional<Deposits> depositsLogOptional = depositsRepository.findById((id));
        if (depositsLogOptional.isPresent()) {
            Deposits depositsLog = depositsLogOptional.get();

            modelMapper.getConfiguration().setAmbiguityIgnored(true);
            DepositsForm depositsForm = modelMapper.map(depositsLog, DepositsForm.class);
            depositsLog.setId(depositsLog.getId());

            model.addAttribute("editForm", depositsForm);
        }
        return "deposit/edit";
    }

    @PostMapping("/edit")
    public String handleEdit(@ModelAttribute("editForm") DepositsForm depositsForm, Model model, BindingResult result) {
        if (result.hasErrors()) {
            model.addAttribute("message", result);
            return "deposit/edit";
        }
        depositsForm.setLastModified(new Date());
        if (depositsForm.getId() == null) {
            depositsForm.setCreationTime(new Date());
            depositsForm.setCreator(coreService.getCurrentUser().getEmail());
        }
        Deposits depositLog = modelMapper.map(depositsForm, Deposits.class);

        Optional<Deposits> depositsLogOptional = depositsRepository.findById(depositsForm.getId());
        if(depositsLogOptional.isPresent()){
            depositLog.setId(depositsLogOptional.get().getId());
        }

        depositsRepository.save(depositLog);
        model.addAttribute("editForm", depositsForm);
        return "redirect:/deposit/depositLog";
    }

    @GetMapping("/add")
    public String addDepositsLog(Model model) {
        DepositsForm form = new DepositsForm();
        form.setCreationTime(new Date());
        form.setLastModified(new Date());
        form.setStatus(true);
        form.setCreator(coreService.getCurrentUser().getEmail());
        model.addAttribute("editForm", form);
        model.addAttribute("users", userRepository.findAll());

        return "deposit/edit";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("id") String ids, Model model) {
        for (String id : ids.split(",")) {
            depositsRepository.deleteById(new ObjectId(id));
        }
        return "redirect:/deposit/depositLog";
    }
}