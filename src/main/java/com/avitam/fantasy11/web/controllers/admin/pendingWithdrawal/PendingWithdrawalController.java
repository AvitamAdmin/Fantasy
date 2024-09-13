package com.avitam.fantasy11.web.controllers.admin.pendingWithdrawal;

import com.avitam.fantasy11.core.service.CoreService;
import com.avitam.fantasy11.form.PendingWithdrawalForm;
import com.avitam.fantasy11.form.WithdrawalDetailsForm;
import com.avitam.fantasy11.form.WithdrawalMethodsForm;
import com.avitam.fantasy11.model.*;
import org.bson.types.Binary;
import org.bson.types.ObjectId;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Controller
@RequestMapping("/admin/pendingWithdrawal")
public class PendingWithdrawalController {

    @Autowired
    private PendingWithdrawalRepository pendingWithdrawalRepository;
    @Autowired
    WithdrawalMethodsRepository withdrawalMethodsRepository;
    @Autowired
    UserWinningsRepository userWinningsRepository;

    @Autowired
    private CoreService coreService;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public String getAll(Model model){
       /* List<WithdrawalMethods> withdrawalMethods = withdrawalMethodsRepository.findAll();
        List<WithdrawalMethods> datas=new ArrayList<>();
        for(WithdrawalMethods withdrawalMethod:withdrawalMethods){
            if(withdrawalMethod.getId()!=null) {
                byte[] image = withdrawalMethod.getLogo().getData();
                withdrawalMethod.setPic(Base64.getEncoder().encodeToString(image));
                datas.add(withdrawalMethod);
            }
        }
        model.addAttribute("models", datas);*/
        model.addAttribute("models", pendingWithdrawalRepository.findAll());
        return "pendingWithdrawal/pendingWithdrawals";
    }


    @GetMapping("/edit")
    public String editPendingWithdrawal (@RequestParam("id")ObjectId id, Model model){

        Optional<PendingWithdrawal> pendingWithdrawalOptional = pendingWithdrawalRepository.findById(id);
        if (pendingWithdrawalOptional.isPresent()) {
            PendingWithdrawal pendingWithdrawal = pendingWithdrawalOptional.get();
            modelMapper.getConfiguration().setAmbiguityIgnored(true);
            PendingWithdrawalForm pendingWithdrawalForm = modelMapper.map(pendingWithdrawal, PendingWithdrawalForm.class);
            pendingWithdrawalForm.setId(String.valueOf(pendingWithdrawal.getId()));
            model.addAttribute("editForm", pendingWithdrawalForm);
            model.addAttribute("WithdrawalMethods", withdrawalMethodsRepository.findAll());
        }
        return "pendingWithdrawal/edit";
    }

    @PostMapping("/edit")
    public String handleEdit(@ModelAttribute("editForm")PendingWithdrawalForm pendingWithdrawalForm,String id, Model model, BindingResult result) throws IOException {

        if (result.hasErrors()) {
            model.addAttribute("message", result);
            model.addAttribute("editForm", pendingWithdrawalForm);
            return "pendingWithdrawal/edit";
        }

        pendingWithdrawalForm.setLastModified(new Date());
        if (pendingWithdrawalForm.getId() == null) {
            pendingWithdrawalForm.setCreationTime(new Date());
            pendingWithdrawalForm.setCreator(coreService.getCurrentUser().getEmail());
        }

        PendingWithdrawal pendingWithdrawal = modelMapper.map(pendingWithdrawalForm, PendingWithdrawal.class);

        Optional<PendingWithdrawal> pendingWithdrawalOptional=pendingWithdrawalRepository.findById(pendingWithdrawalForm.getId());
        if(pendingWithdrawalOptional.isPresent()){
            pendingWithdrawal.setId(pendingWithdrawalOptional.get().getId());
        }

        pendingWithdrawalRepository.save(pendingWithdrawal);
        model.addAttribute("editForm", pendingWithdrawalForm);

        return "redirect:/admin/pendingWithdrawal";
    }

    @GetMapping("/add")
    public String addPendingWithdrawal(Model model) {
        PendingWithdrawalForm form = new PendingWithdrawalForm();
        form.setCreationTime(new Date());
        form.setLastModified(new Date());
        form.setStatus(true);
        form.setName(coreService.getCurrentUser().getName());
        form.setCreator(coreService.getCurrentUser().getEmail());
        model.addAttribute("editForm", form);
        model.addAttribute("WithdrawalMethods", withdrawalMethodsRepository.findAll());
        model.addAttribute("Userwinnings", userWinningsRepository.findAll());
        return "pendingWithdrawal/edit";
    }
    @GetMapping("/delete")
    public String deletePendingWithdrawal(@RequestParam("id") String ids, Model model) {
        for (String id : ids.split(",")) {
            pendingWithdrawalRepository.deleteById(new ObjectId(id));
        }
        return "redirect:/admin/pendingWithdrawal";
    }
}
