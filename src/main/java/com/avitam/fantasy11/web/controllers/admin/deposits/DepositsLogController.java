package com.avitam.fantasy11.web.controllers.admin.deposits;

import com.avitam.fantasy11.api.dto.DepositsDto;
import com.avitam.fantasy11.api.dto.DepositsWsDto;
import com.avitam.fantasy11.api.dto.SearchDto;
import com.avitam.fantasy11.api.service.DepositsService;
import com.avitam.fantasy11.model.Contest;
import com.avitam.fantasy11.model.Deposits;
import com.avitam.fantasy11.repository.DepositsRepository;
import com.avitam.fantasy11.web.controllers.BaseController;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.modelmapper.ModelMapper;
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
    @Autowired
    private ModelMapper modelMapper;
    private static final String ADMIN_DEPOSIT = "/admin/depositLog";

    @PostMapping
    @ResponseBody
    public DepositsWsDto getAllDeposits(@RequestBody DepositsWsDto depositsWsDto) {
        Pageable pageable = getPageable(depositsWsDto.getPage(), depositsWsDto.getSizePerPage(), depositsWsDto.getSortDirection(), depositsWsDto.getSortField());
        DepositsDto depositsDto = CollectionUtils.isNotEmpty(depositsWsDto.getDepositsDtoList()) ? depositsWsDto.getDepositsDtoList().get(0) : null;
        Deposits deposits = depositsDto != null ? modelMapper.map(depositsDto, Deposits.class) : null;
        Page<Deposits> page = isSearchActive(deposits) != null ? depositsRepository.findAll(Example.of(deposits), pageable) : depositsRepository.findAll(pageable);
        depositsWsDto.setDepositsDtoList(modelMapper.map(page.getContent(), List.class));
        depositsWsDto.setBaseUrl(ADMIN_DEPOSIT);
        depositsWsDto.setTotalPages(page.getTotalPages());
        depositsWsDto.setTotalRecords(page.getTotalElements());
        return depositsWsDto;
    }

    @PostMapping("/getDepositStatus")
    @ResponseBody
    public DepositsWsDto getDepositsStatus(@RequestBody DepositsWsDto depositsWsDto) {
        DepositsDto depositsDto = new DepositsDto();
        String depositStatus = depositsDto.getDepositStatus();
        if (depositStatus.equals("Approved")) {
            depositsDto.setDepositsList(depositsRepository.findByDepositStatus(depositStatus));
        } else if (depositStatus.equals("Pending")) {
            depositsDto.setDepositsList(depositsRepository.findByDepositStatus(depositStatus));
        } else if (depositStatus.equals("Rejected")) {
            depositsDto.setDepositsList(depositsRepository.findByDepositStatus(depositStatus));
        }
        depositsWsDto.setBaseUrl(ADMIN_DEPOSIT);

        return depositsWsDto;
    }

    @GetMapping("/get")
    @ResponseBody
    public DepositsWsDto getActiveDeposit() {
        DepositsWsDto depositsWsDto = new DepositsWsDto();
        depositsWsDto.setDepositsDtoList(modelMapper.map(depositsRepository.findByStatusOrderByIdentifier(true), List.class));
        depositsWsDto.setBaseUrl(ADMIN_DEPOSIT);
        return depositsWsDto;
    }

    @PostMapping("/getedit")
    @ResponseBody
    public DepositsWsDto edit(@RequestBody DepositsWsDto depositsWsDto) {
        depositsWsDto.setDepositsDtoList(modelMapper.map(depositsRepository.findByRecordId(depositsWsDto.getDepositsDtoList().get(0).getRecordId()), List.class));
        depositsWsDto.setBaseUrl(ADMIN_DEPOSIT);
        return depositsWsDto;
    }

    @PostMapping("/edit")
    @ResponseBody
    public DepositsWsDto handleEdit(@RequestBody DepositsWsDto depositsWsDto) {
        return depositService.handleEdit(depositsWsDto);
    }

    @PostMapping("/delete")
    @ResponseBody
    public DepositsWsDto delete(@RequestBody DepositsWsDto depositsWsDto) {
        for (DepositsDto depositsDto : depositsWsDto.getDepositsDtoList()) {
            depositsRepository.deleteByRecordId(depositsDto.getRecordId());
        }
        depositsWsDto.setMessage("Data deleted Successfully");
        depositsWsDto.setBaseUrl(ADMIN_DEPOSIT);
        return depositsWsDto;
    }

    @GetMapping("/getAdvancedSearch")
    @ResponseBody
    public List<SearchDto> getSearchAttributes() {
        return getGroupedParentAndChildAttributes(new Deposits());
    }

}