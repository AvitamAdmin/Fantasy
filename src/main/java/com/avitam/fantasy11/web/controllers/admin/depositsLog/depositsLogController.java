package com.avitam.fantasy11.web.controllers.admin.depositsLog;

import com.avitam.fantasy11.core.service.CoreService;
import com.avitam.fantasy11.form.DepositsLogForm;
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
@RequestMapping("/deposit")
public class depositsLogController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DepositsLogRepository depositsLogRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CoreService coreService;

    @GetMapping("/depositLog")
    public String getAllModels(Model model) {
        model.addAttribute("models", depositsLogRepository.findAll());
        return "deposit/depositLog";
    }

    @GetMapping("/pendingDeposits")
    public String getAllPendingDeposit(Model model) {
        model.addAttribute("models", depositsLogRepository.findByDepositStatus("Pending"));
        return "deposit/depositLog";
    }

    @GetMapping("/approvedDeposits")
    public String getAllApprovedDeposit(Model model) {
        model.addAttribute("models", depositsLogRepository.findByDepositStatus("Approved"));
        return "deposit/depositLog";
    }

    @GetMapping({"/depositLog/edit","/pendingDeposits/edit" ,"/approvedDeposits/edit" })
    public String editPlaying11(@RequestParam("id") String id, Model model) {

        Optional<DepositsLog> depositsLogOptional = depositsLogRepository.findById((id));
        if (depositsLogOptional.isPresent()) {
            DepositsLog depositsLog = depositsLogOptional.get();

            modelMapper.getConfiguration().setAmbiguityIgnored(true);
            DepositsLogForm depositsLogForm = modelMapper.map(depositsLog, DepositsLogForm.class);
            depositsLog.setId(depositsLog.getId());

            model.addAttribute("editForm", depositsLogForm);
        }
        return "deposit/edit";
    }

    @PostMapping("/depositLog/edit")
    public String handleEdit(@ModelAttribute("editForm") DepositsLogForm depositsLogForm, Model model, BindingResult result) {
        if (result.hasErrors()) {
            model.addAttribute("message", result);
            return "deposit/edit";
        }
        depositsLogForm.setLastModified(new Date());
        if (depositsLogForm.getId() == null) {
            depositsLogForm.setCreationTime(new Date());
            depositsLogForm.setCreator(coreService.getCurrentUser().getEmail());
        }
        DepositsLog depositLog = modelMapper.map(depositsLogForm, DepositsLog.class);

        Optional<DepositsLog> depositsLogOptional = depositsLogRepository.findById(depositsLogForm.getId());
        if(depositsLogOptional.isPresent()){
            depositLog.setId(depositsLogOptional.get().getId());
        }

        depositsLogRepository.save(depositLog);
        model.addAttribute("editForm", depositsLogForm);
        return "redirect:/deposit/depositLog";
    }

    @GetMapping("/depositLog/add")
    public String addDepositsLog(Model model) {
        DepositsLogForm form = new DepositsLogForm();
        form.setCreationTime(new Date());
        form.setLastModified(new Date());
        form.setStatus(true);
        form.setCreator(coreService.getCurrentUser().getEmail());
        model.addAttribute("editForm", form);
        model.addAttribute("depositsLog", depositsLogRepository.findAll());

        return "deposit/edit";
    }

    @GetMapping("/delete")
    public String deleteDepositsLog(@RequestParam("id") String ids, Model model) {
        for (String id : ids.split(",")) {
            depositsLogRepository.deleteById(new ObjectId(id));
        }
        return "redirect:/deposit/depositLog";
    }
}