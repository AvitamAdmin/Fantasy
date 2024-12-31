package com.avitam.fantasy11.web.controllers.admin.withdrawalDetails;

import com.avitam.fantasy11.api.dto.UserWinningsDto;
import com.avitam.fantasy11.api.dto.WithdrawalDetailsDto;
import com.avitam.fantasy11.api.dto.WithdrawalDetailsWsDto;
import com.avitam.fantasy11.api.service.WithdrawalDetailsService;
import com.avitam.fantasy11.core.service.CoreService;
import com.avitam.fantasy11.model.UserWinnings;
import com.avitam.fantasy11.model.WithdrawalDetails;
import com.avitam.fantasy11.repository.PendingWithdrawalRepository;
import com.avitam.fantasy11.repository.WithdrawalDetailsRepository;
import com.avitam.fantasy11.repository.WithdrawalMethodsRepository;
import com.avitam.fantasy11.web.controllers.BaseController;
import org.apache.commons.collections4.CollectionUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

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
    public WithdrawalDetailsWsDto getAll(@RequestBody WithdrawalDetailsWsDto withdrawalDetailsWsDto) {
        Pageable pageable = getPageable(withdrawalDetailsWsDto.getPage(), withdrawalDetailsWsDto.getSizePerPage(), withdrawalDetailsWsDto.getSortDirection(), withdrawalDetailsWsDto.getSortField());
        WithdrawalDetailsDto withdrawalDetailsDto= CollectionUtils.isNotEmpty(withdrawalDetailsWsDto.getWithdrawalDetailsDtoList())?withdrawalDetailsWsDto.getWithdrawalDetailsDtoList() .get(0) : new WithdrawalDetailsDto() ;
        WithdrawalDetails withdrawalDetails = modelMapper.map(withdrawalDetailsWsDto, WithdrawalDetails.class);
        Page<WithdrawalDetails> page = isSearchActive(withdrawalDetails) != null ? withdrawalDetailsRepository.findAll(Example.of(withdrawalDetails), pageable) : withdrawalDetailsRepository.findAll(pageable);
        withdrawalDetailsWsDto.setWithdrawalDetailsDtoList(modelMapper.map(page.getContent(), List.class));
        withdrawalDetailsWsDto.setTotalPages(page.getTotalPages());
        withdrawalDetailsWsDto.setTotalRecords(page.getTotalElements());
        withdrawalDetailsWsDto.setBaseUrl(ADMIN_WITHDRAWALDETAILS);
        return withdrawalDetailsWsDto;
    }

    @GetMapping("/get")
    @ResponseBody
    public WithdrawalDetailsWsDto getActiveWithdrwalDetails() {
        WithdrawalDetailsWsDto withdrawalDetailsWsDto = new WithdrawalDetailsWsDto();
        withdrawalDetailsWsDto.setWithdrawalDetailsDtoList(modelMapper.map(withdrawalDetailsRepository.findByStatusOrderByIdentifier(true),List.class));
        withdrawalDetailsWsDto.setBaseUrl(ADMIN_WITHDRAWALDETAILS);
        return withdrawalDetailsWsDto;
    }

    @PostMapping("/getedit")
    @ResponseBody
    public WithdrawalDetailsWsDto editPendingWithdrawal(@RequestBody WithdrawalDetailsWsDto request) {
        WithdrawalDetailsWsDto withdrawalDetailsWsDto = new WithdrawalDetailsWsDto();
        withdrawalDetailsWsDto.setWithdrawalDetailsDtoList(modelMapper.map(withdrawalDetailsRepository.findByRecordId(request.getWithdrawalDetailsDtoList().get(0).getRecordId()),List.class));
        withdrawalDetailsWsDto.setBaseUrl(ADMIN_WITHDRAWALDETAILS);
        return withdrawalDetailsWsDto;
    }

    @PostMapping("/edit")
    @ResponseBody
    public WithdrawalDetailsWsDto handleEdit(@RequestBody WithdrawalDetailsWsDto request) {
        return withdrawalDetailsService.handleEdit(request);
    }

    @GetMapping("/add")
    @ResponseBody
    public WithdrawalDetailsWsDto addWithdrawalDetailsDto() {

        WithdrawalDetailsWsDto withdrawalDetailsWsDto = new WithdrawalDetailsWsDto();
        withdrawalDetailsWsDto.setWithdrawalDetailsDtoList(modelMapper.map(withdrawalDetailsRepository.findByStatusOrderByIdentifier(true),List.class));
        withdrawalDetailsWsDto.setBaseUrl(ADMIN_WITHDRAWALDETAILS);
        return withdrawalDetailsWsDto;
    }

    @PostMapping("/delete")
    @ResponseBody
    public WithdrawalDetailsWsDto deleteWithdrawalDetails(@RequestBody WithdrawalDetailsWsDto withdrawalDetailsWsDto) {
        for (String id : withdrawalDetailsWsDto.getWithdrawalDetailsDtoList().get(0).getRecordId().split(",")) {
            withdrawalDetailsRepository.deleteByRecordId(id);
        }
        withdrawalDetailsWsDto.setMessage("Data deleted successfully");
        withdrawalDetailsWsDto.setBaseUrl(ADMIN_WITHDRAWALDETAILS);
        return withdrawalDetailsWsDto;
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

