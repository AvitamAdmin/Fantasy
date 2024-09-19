package com.avitam.fantasy11.web.controllers.admin.withdrawalDetails;

import com.avitam.fantasy11.core.service.CoreService;
import com.avitam.fantasy11.form.WithdrawalDetailsForm;
import com.avitam.fantasy11.model.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Controller
@RequestMapping("/admin/pendingWithdrawal")
public class WithdrawalDetailsController {
    @Autowired
    private WithdrawalMethodsRepository withdrawalMethodsRepository;
    @Autowired
    private CoreService coreService;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    WithdrawalDetailsRepository withdrawalDetailsRepository;

    @Autowired
    private PendingWithdrawalRepository pendingWithdrawalRepository;


    @GetMapping("/action")
    public String getPendingWithdrawal(@RequestParam("id") String id, Model model) {

        Optional<PendingWithdrawal> pendingWithdrawalOptional = pendingWithdrawalRepository.findById(id);
        WithdrawalDetails withdrawalDetails = new WithdrawalDetails();
        if (pendingWithdrawalOptional.isPresent()) {
            PendingWithdrawal pendingWithdrawal = pendingWithdrawalOptional.get();
            modelMapper.getConfiguration().setAmbiguityIgnored(true);

            WithdrawalMethods withdrawalMethods = withdrawalMethodsRepository.findByMethodName(pendingWithdrawal.getMethodName());
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            withdrawalDetails.setId(pendingWithdrawal.getId());
            withdrawalDetails.setMemberName(coreService.getCurrentUser().getName());
            withdrawalDetails.setMemberEmail(coreService.getCurrentUser().getEmail());
            withdrawalDetails.setAmountOfWithdraw(pendingWithdrawal.getAmountOfWithdraw());
            withdrawalDetails.setChargeOfWithdraw(withdrawalMethods.getFixedCharge());
            withdrawalDetails.setWithdrawMethod(pendingWithdrawal.getMethodName());
            withdrawalDetails.setProcessingTime(withdrawalMethods.getProcessingTime());
            withdrawalDetails.setAmountInMethodCurrency(withdrawalMethods.getCurrency());
            withdrawalDetails.setDateOfCreate(String.valueOf(now));
            withdrawalDetails.setDetails(" ");
            withdrawalDetails.setStatus(pendingWithdrawal.getStatus());
        }
            WithdrawalDetailsForm withdrawalDetailsForm = modelMapper.map(withdrawalDetails, WithdrawalDetailsForm.class);
            model.addAttribute("withdrawalDetails", withdrawalDetailsForm);
            // model.addAttribute("editForm", withdrawalDetailsForm);

        return "pendingWithdrawal/withdrawalDetailss";
    }

    @PostMapping("/action")
    public String handleAction(@ModelAttribute("withdrawalDetails") WithdrawalDetailsForm withdrawalDetailsForm, Model model) throws IOException {

        WithdrawalDetails withdrawalDetails = modelMapper.map(withdrawalDetailsForm, WithdrawalDetails.class);
        Optional<PendingWithdrawal> pendingWithdrawalOptional = pendingWithdrawalRepository.findById(withdrawalDetailsForm.getId());
        if (pendingWithdrawalOptional.isPresent()) {
            PendingWithdrawal pendingWithdrawal = pendingWithdrawalOptional.get();
            modelMapper.getConfiguration().setAmbiguityIgnored(true);

            WithdrawalMethods withdrawalMethods = withdrawalMethodsRepository.findByMethodName(pendingWithdrawal.getMethodName());
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            withdrawalDetails.setId(pendingWithdrawal.getId());
            withdrawalDetails.setMemberName(coreService.getCurrentUser().getName());
            withdrawalDetails.setMemberEmail(coreService.getCurrentUser().getEmail());
            withdrawalDetails.setAmountOfWithdraw(pendingWithdrawal.getAmountOfWithdraw());
            withdrawalDetails.setChargeOfWithdraw(withdrawalMethods.getFixedCharge());
            withdrawalDetails.setWithdrawMethod(pendingWithdrawal.getMethodName());
            withdrawalDetails.setProcessingTime(withdrawalMethods.getProcessingTime());
            withdrawalDetails.setAmountInMethodCurrency(withdrawalMethods.getCurrency());
            withdrawalDetails.setDateOfCreate(String.valueOf(now));
            withdrawalDetails.setDetails(" ");
            withdrawalDetails.setStatus(pendingWithdrawal.getStatus());
        }

            withdrawalDetailsRepository.save(withdrawalDetails);
            model.addAttribute("withdrawalDetails", withdrawalDetailsForm);

            return "redirect:/admin/pendingWithdrawal";
    }
}
