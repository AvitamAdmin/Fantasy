package com.avitam.fantasy11.web.controllers.admin.deposits;

import com.avitam.fantasy11.api.dto.DepositsDto;
import com.avitam.fantasy11.api.service.DepositsService;
import com.avitam.fantasy11.model.*;
import com.avitam.fantasy11.repository.DepositsRepository;
import com.avitam.fantasy11.web.controllers.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/depositLog")
public class DepositsLogController extends BaseController {

    @Autowired
    private DepositsRepository depositsRepository;
    @Autowired
    private DepositsService depositService;
    private static final String ADMIN_DEPOSIT="/admin/depositLog";

    @PostMapping
    @ResponseBody
    public DepositsDto getAllDeposits(@RequestBody DepositsDto depositsDto) {
        Pageable pageable=getPageable(depositsDto.getPage(),depositsDto.getSizePerPage(),depositsDto.getSortDirection(),depositsDto.getSortField());
        Deposits deposits=depositsDto.getDeposits();
        Page<Deposits> page=isSearchActive(deposits) !=null ? depositsRepository.findAll(Example.of(deposits),pageable) : depositsRepository.findAll(pageable);
        depositsDto.setDepositsList(page.getContent());
        depositsDto.setBaseUrl(ADMIN_DEPOSIT);
        depositsDto.setTotalPages(page.getTotalPages());
        depositsDto.setTotalRecords(page.getTotalElements());
        return depositsDto;
    }

    @PostMapping("/getDepositStatus")
    @ResponseBody
    public DepositsDto getDepositsStatus(@RequestBody DepositsDto request)
    {
        DepositsDto depositsDto=new DepositsDto();
        String depositStatus=request.getDeposits().getDepositStatus();
        if(depositStatus.equals("Approved"))
        {
            depositsDto.setDepositsList(depositsRepository.findByDepositStatus(depositStatus));
        }
        else if(depositStatus.equals("Pending"))
        {
            depositsDto.setDepositsList(depositsRepository.findByDepositStatus(depositStatus));
        }
        else if(depositStatus.equals("Rejected"))
        {
            depositsDto.setDepositsList(depositsRepository.findByDepositStatus(depositStatus));
        }
        depositsDto.setBaseUrl(ADMIN_DEPOSIT);

        return depositsDto;
    }

    @GetMapping("/get")
    @ResponseBody
    public DepositsDto getActiveDeposit(){
        DepositsDto depositsDto=new DepositsDto();
        depositsDto.setDepositsList(depositsRepository.findByStatusOrderByIdentifier(true));
        depositsDto.setBaseUrl(ADMIN_DEPOSIT);
        return depositsDto;
    }
    @PostMapping("/getedit")
    @ResponseBody
    public DepositsDto edit(@RequestBody DepositsDto request) {
        DepositsDto depositsDto=new DepositsDto();
        Deposits deposits=depositsRepository.findByRecordId(request.getRecordId());
        depositsDto.setDeposits(deposits);
        depositsDto.setBaseUrl(ADMIN_DEPOSIT);
        return depositsDto;
    }

    @PostMapping("/edit")
    @ResponseBody
    public DepositsDto handleEdit(@RequestBody DepositsDto request) {
        return depositService.handleEdit(request);
    }

    @GetMapping("/add")
    @ResponseBody
    public DepositsDto add() {
        DepositsDto depositsDto = new DepositsDto();
        depositsDto.setDepositsList(depositsRepository.findByStatusOrderByIdentifier(true));
        depositsDto.setBaseUrl(ADMIN_DEPOSIT);
        return depositsDto;
    }

    @PostMapping("/delete")
    @ResponseBody
    public DepositsDto delete(@RequestBody DepositsDto depositsDto) {
        for (String id : depositsDto.getRecordId().split(",")) {
            depositsRepository.deleteByRecordId(id);
        }
        depositsDto.setMessage("Data deleted Successfully");
        depositsDto.setBaseUrl(ADMIN_DEPOSIT);
        return depositsDto;
    }

}