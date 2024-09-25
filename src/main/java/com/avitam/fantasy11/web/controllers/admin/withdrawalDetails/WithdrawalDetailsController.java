package com.avitam.fantasy11.web.controllers.admin.withdrawalDetails;

import com.avitam.fantasy11.api.dto.PendingWithdrawalDto;
import com.avitam.fantasy11.api.dto.WithdrawalDetailsDto;
import com.avitam.fantasy11.api.service.WithdrawalDetailsService;
import com.avitam.fantasy11.core.service.CoreService;
import com.avitam.fantasy11.form.WithdrawalDetailsForm;
import com.avitam.fantasy11.model.*;
import com.avitam.fantasy11.web.controllers.BaseController;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Controller
@RequestMapping("/admin/withdrawalDetails")
public class WithdrawalDetailsController extends BaseController {
    @Autowired
    private WithdrawalMethodsRepository withdrawalMethodsRepository;
    @Autowired
    private CoreService coreService;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private WithdrawalDetailsService withdrawalDetailsService;
    @Autowired
    WithdrawalDetailsRepository withdrawalDetailsRepository;

    @Autowired
    private PendingWithdrawalRepository pendingWithdrawalRepository;
    public static final String ADMIN_WITHDRAWALDETAILS = "/admin/withdrawalDetails";

    @PostMapping
    @ResponseBody
    public WithdrawalDetailsDto getAll(@RequestBody WithdrawalDetailsDto withdrawalDetailsDto) {
        Pageable pageable = getPageable(withdrawalDetailsDto.getPage(), withdrawalDetailsDto.getSizePerPage(), withdrawalDetailsDto.getSortDirection(), withdrawalDetailsDto.getSortField());
        WithdrawalDetails withdrawalDetails = withdrawalDetailsDto.getWithdrawalDetails();
        Page<WithdrawalDetails> page = isSearchActive(withdrawalDetails) != null ? withdrawalDetailsRepository.findAll(Example.of(withdrawalDetails), pageable) : withdrawalDetailsRepository.findAll(pageable);
        withdrawalDetailsDto.setWithdrawalDetailsList(page.getContent());
        withdrawalDetailsDto.setTotalPages(page.getTotalPages());
        withdrawalDetailsDto.setTotalRecords(page.getTotalElements());
        withdrawalDetailsDto.setBaseUrl(ADMIN_WITHDRAWALDETAILS);
        return withdrawalDetailsDto;
    }

    @GetMapping("/get")
    @ResponseBody
    public WithdrawalDetailsDto getActiveWithdrwalDetails() {
        WithdrawalDetailsDto withdrawalDetailsDto = new WithdrawalDetailsDto();
        withdrawalDetailsDto.setWithdrawalDetailsList(withdrawalDetailsRepository.findByStatusOrderByIdentifier(true));
        withdrawalDetailsDto.setBaseUrl(ADMIN_WITHDRAWALDETAILS);
        return withdrawalDetailsDto;
    }

    @PostMapping("/getedit")
    @ResponseBody
    public WithdrawalDetailsDto editPendingWithdrawal(@RequestBody WithdrawalDetailsDto request) {

        WithdrawalDetailsDto withdrawalDetailsDto = new WithdrawalDetailsDto();
        withdrawalDetailsDto.setWithdrawalDetails(withdrawalDetailsRepository.findByRecordId(request.getRecordId()));
        withdrawalDetailsDto.setBaseUrl(ADMIN_WITHDRAWALDETAILS);
        return withdrawalDetailsDto;
    }

    @PostMapping("/edit")
    @ResponseBody
    public WithdrawalDetailsDto handleEdit(@RequestBody WithdrawalDetailsDto request) {
        return withdrawalDetailsService.handleEdit(request);
    }

    @GetMapping("/add")
    @ResponseBody
    public WithdrawalDetailsDto addWithdrawalDetailsDto() {

        WithdrawalDetailsDto withdrawalDetailsDto = new WithdrawalDetailsDto();
        withdrawalDetailsDto.setWithdrawalDetailsList(withdrawalDetailsRepository.findByStatusOrderByIdentifier(true));
        withdrawalDetailsDto.setBaseUrl(ADMIN_WITHDRAWALDETAILS);
        return withdrawalDetailsDto;
    }

    @PostMapping("/delete")
    @ResponseBody
    public WithdrawalDetailsDto deleteWithdrawalDetails(@RequestBody WithdrawalDetailsDto withdrawalDetailsDto) {
        for (String id : withdrawalDetailsDto.getRecordId().split(",")) {
            withdrawalDetailsRepository.deleteByRecordId(id);
        }
        withdrawalDetailsDto.setMessage("Data deleted successfully");
        withdrawalDetailsDto.setBaseUrl(ADMIN_WITHDRAWALDETAILS);
        return withdrawalDetailsDto;
    }
}



/*@GetMapping("/action")
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

        return "withdrawalDetails/withdrawalDetailss";
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
    }*/

