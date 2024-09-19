package com.avitam.fantasy11.web.controllers.admin.pendingWithdrawal;

import com.avitam.fantasy11.api.dto.PendingWithdrawalDto;
import com.avitam.fantasy11.api.dto.WithdrawalMethodsDto;
import com.avitam.fantasy11.api.service.PendingWithdrawalService;
import com.avitam.fantasy11.core.service.CoreService;
import com.avitam.fantasy11.form.PendingWithdrawalForm;
import com.avitam.fantasy11.form.WithdrawalDetailsForm;
import com.avitam.fantasy11.form.WithdrawalMethodsForm;
import com.avitam.fantasy11.model.*;
import com.avitam.fantasy11.web.controllers.BaseController;
import org.bson.types.Binary;
import org.bson.types.ObjectId;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
public class PendingWithdrawalController extends BaseController {

    @Autowired
    private PendingWithdrawalRepository pendingWithdrawalRepository;
    @Autowired
    WithdrawalMethodsRepository withdrawalMethodsRepository;
    @Autowired
    UserWinningsRepository userWinningsRepository;
    @Autowired
    private PendingWithdrawalService pendingWithdrawalService;
    @Autowired
    private CoreService coreService;
    @Autowired
    private ModelMapper modelMapper;

    public static final String ADMIN_PENDINGWITHDRAWAL = "/admin/pendingWithdrawal";

    @PostMapping
    @ResponseBody
    public PendingWithdrawalDto getAll(@RequestBody PendingWithdrawalDto pendingWithdrawalDto){
        Pageable pageable = getPageable(pendingWithdrawalDto.getPage(), pendingWithdrawalDto.getSizePerPage(), pendingWithdrawalDto.getSortDirection(), pendingWithdrawalDto.getSortField());
        PendingWithdrawal pendingWithdrawal = pendingWithdrawalDto.getPendingWithdrawal();
        Page<PendingWithdrawal> page = isSearchActive(pendingWithdrawal)!=null ? pendingWithdrawalRepository.findAll(Example.of(pendingWithdrawal), pageable) : pendingWithdrawalRepository.findAll(pageable);
        pendingWithdrawalDto.setPendingWithdrawalList(page.getContent());
        pendingWithdrawalDto.setTotalPages(page.getTotalPages());
        pendingWithdrawalDto.setTotalRecords(page.getTotalElements());
        pendingWithdrawalDto.setBaseUrl(ADMIN_PENDINGWITHDRAWAL);
        return pendingWithdrawalDto;
    }

    @GetMapping("/get")
    @ResponseBody
    public PendingWithdrawalDto getActivePendingWithdrawal(){
        PendingWithdrawalDto pendingWithdrawalDto = new PendingWithdrawalDto();
        pendingWithdrawalDto.setPendingWithdrawalList(pendingWithdrawalRepository.findByStatusOrderByIdentifier(true));
        pendingWithdrawalDto.setBaseUrl(ADMIN_PENDINGWITHDRAWAL);
        return pendingWithdrawalDto;
    }


    @GetMapping("/edit")
    @ResponseBody
    public PendingWithdrawalDto editPendingWithdrawal (@RequestBody PendingWithdrawalDto request){

        PendingWithdrawalDto pendingWithdrawalDto = new PendingWithdrawalDto();
        pendingWithdrawalDto.setPendingWithdrawal(pendingWithdrawalRepository.findByRecordId(request.getRecordId()));
        pendingWithdrawalDto.setBaseUrl(ADMIN_PENDINGWITHDRAWAL);
        return pendingWithdrawalDto;
    }

    @PostMapping("/edit")
    @ResponseBody
    public PendingWithdrawalDto handleEdit(@RequestBody PendingWithdrawalDto request)  {
        return pendingWithdrawalService.handleEdit(request);
    }

    @GetMapping("/add")
    @ResponseBody
    public PendingWithdrawalDto addPendingWithdrawal() {

        PendingWithdrawalDto pendingWithdrawalDto = new PendingWithdrawalDto();
        pendingWithdrawalDto.setPendingWithdrawalList(pendingWithdrawalRepository.findByStatusOrderByIdentifier(true));
        pendingWithdrawalDto.setBaseUrl(ADMIN_PENDINGWITHDRAWAL);
        return pendingWithdrawalDto;
    }
    @PostMapping("/delete")
    @ResponseBody
    public PendingWithdrawalDto deletePendingWithdrawal(@RequestBody PendingWithdrawalDto pendingWithdrawalDto) {
        for (String id : pendingWithdrawalDto.getRecordId().split(",")) {
            withdrawalMethodsRepository.deleteById(new ObjectId(id));
        }
        pendingWithdrawalDto.setMessage("Data deleted successfully");
        pendingWithdrawalDto.setBaseUrl(ADMIN_PENDINGWITHDRAWAL);
        return pendingWithdrawalDto;
    }
}
