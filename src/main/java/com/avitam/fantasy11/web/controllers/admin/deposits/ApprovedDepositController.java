package com.avitam.fantasy11.web.controllers.admin.deposits;

import com.avitam.fantasy11.api.dto.DepositsDto;
import com.avitam.fantasy11.api.service.DepositsService;
import com.avitam.fantasy11.model.Deposits;
import com.avitam.fantasy11.model.DepositsRepository;
import com.avitam.fantasy11.web.controllers.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/approvedDeposits")
public class ApprovedDepositController extends BaseController {

    @Autowired
    private DepositsRepository depositsRepository;
    @Autowired
    private DepositsService depositService;
    private static final String ADMIN_APPROVEDDEPOSIT="/admin/approvedDeposits";

    @PostMapping
    @ResponseBody
    public DepositsDto getAllModels(@RequestBody DepositsDto depositsDto) {
        Pageable pageable=getPageable(depositsDto.getPage(),depositsDto.getSizePerPage(),depositsDto.getSortDirection(),depositsDto.getSortField());
        Deposits deposits=depositsDto.getDeposits();
        Page<Deposits> page=isSearchActive(deposits) !=null ? depositsRepository.findAll(Example.of(deposits),pageable) : depositsRepository.findAll(pageable);
        depositsDto.setDepositsList(page.getContent());
        depositsDto.setBaseUrl(ADMIN_APPROVEDDEPOSIT);
        depositsDto.setTotalPages(page.getTotalPages());
        depositsDto.setTotalRecords(page.getTotalElements());
        return depositsDto;
    }

    @GetMapping("/get")
    @ResponseBody
    public DepositsDto getActiveDeposit(){
        DepositsDto depositsDto=new DepositsDto();
        depositsDto.setDepositsList(depositsRepository.findByStatusAndDepositStatusOrderByIdentifier(true,"Approved"));
        depositsDto.setBaseUrl(ADMIN_APPROVEDDEPOSIT);
        return depositsDto;
    }
    @PostMapping("/getedit")
    @ResponseBody
    public DepositsDto editDeposits(@RequestBody DepositsDto request) {
        DepositsDto depositsDto=new DepositsDto();
        Deposits deposits=depositsRepository.findByRecordId(request.getRecordId());
        depositsDto.setDeposits(deposits);
        depositsDto.setBaseUrl(ADMIN_APPROVEDDEPOSIT);
        return depositsDto;
    }

    @PostMapping("/edit")
    @ResponseBody
    public DepositsDto handleEdit(@RequestBody DepositsDto request) {
        int flag = 1;
        return depositService.handleEdit(request, flag);
    }

    @GetMapping("/add")
    @ResponseBody
    public DepositsDto addDeposit() {
        DepositsDto depositsDto = new DepositsDto();
        depositsDto.setDepositsList(depositsRepository.findByStatusAndDepositStatusOrderByIdentifier(true,"Approved"));
        depositsDto.setBaseUrl(ADMIN_APPROVEDDEPOSIT);
        return depositsDto;
    }

    @PostMapping("/delete")
    @ResponseBody
    public DepositsDto deleteDeposit(@RequestBody DepositsDto depositsDto) {
        for (String id : depositsDto.getRecordId().split(",")) {
            depositsRepository.deleteByRecordId(id);
        }
        depositsDto.setMessage("Data deleted Successfully");
        depositsDto.setBaseUrl(ADMIN_APPROVEDDEPOSIT);
        return depositsDto;
    }


}