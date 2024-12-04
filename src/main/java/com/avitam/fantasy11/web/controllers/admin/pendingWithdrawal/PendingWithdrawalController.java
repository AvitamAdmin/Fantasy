package com.avitam.fantasy11.web.controllers.admin.pendingWithdrawal;

import com.avitam.fantasy11.api.dto.PendingWithdrawalDto;
import com.avitam.fantasy11.api.dto.PendingWithdrawalWsDto;
import com.avitam.fantasy11.api.service.PendingWithdrawalService;
import com.avitam.fantasy11.core.service.CoreService;
import com.avitam.fantasy11.model.PendingWithdrawal;
import com.avitam.fantasy11.repository.PendingWithdrawalRepository;
import com.avitam.fantasy11.repository.UserWinningsRepository;
import com.avitam.fantasy11.repository.WithdrawalMethodsRepository;
import com.avitam.fantasy11.web.controllers.BaseController;
import org.apache.commons.collections4.CollectionUtils;
import org.bson.types.ObjectId;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public PendingWithdrawalWsDto getAll(@RequestBody PendingWithdrawalWsDto pendingWithdrawalWsDto){
        Pageable pageable = getPageable(pendingWithdrawalWsDto.getPage(), pendingWithdrawalWsDto.getSizePerPage(), pendingWithdrawalWsDto.getSortDirection(), pendingWithdrawalWsDto.getSortField());
        PendingWithdrawalDto pendingWithdrawalDto = CollectionUtils.isNotEmpty(pendingWithdrawalWsDto.getPendingWithdrawalDtoList()) ? pendingWithdrawalWsDto.getPendingWithdrawalDtoList().get(0) : new PendingWithdrawalDto();
        PendingWithdrawal pendingWithdrawal = modelMapper.map(pendingWithdrawalDto, PendingWithdrawal.class);
        Page<PendingWithdrawal> page = isSearchActive(pendingWithdrawal)!=null ? pendingWithdrawalRepository.findAll(Example.of(pendingWithdrawal), pageable) : pendingWithdrawalRepository.findAll(pageable);
        pendingWithdrawalWsDto.setPendingWithdrawalDtoList(modelMapper.map(page.getContent(),List.class));
        pendingWithdrawalWsDto.setTotalPages(page.getTotalPages());
        pendingWithdrawalWsDto.setTotalRecords(page.getTotalElements());
        pendingWithdrawalWsDto.setBaseUrl(ADMIN_PENDINGWITHDRAWAL);
        return pendingWithdrawalWsDto;
    }

    @GetMapping("/get")
    @ResponseBody
    public PendingWithdrawalWsDto getActivePendingWithdrawal(){
        PendingWithdrawalWsDto pendingWithdrawalWsDto = new PendingWithdrawalWsDto();
        pendingWithdrawalWsDto.setPendingWithdrawalDtoList(modelMapper.map(pendingWithdrawalRepository.findByStatusOrderByIdentifier(true), List.class));
        pendingWithdrawalWsDto.setBaseUrl(ADMIN_PENDINGWITHDRAWAL);
        return pendingWithdrawalWsDto;
    }


    @PostMapping("/getedit")
    @ResponseBody
    public PendingWithdrawalWsDto editPendingWithdrawal (@RequestBody PendingWithdrawalWsDto request){

        PendingWithdrawalWsDto pendingWithdrawalWsDto = new PendingWithdrawalWsDto();
        pendingWithdrawalWsDto.setBaseUrl(ADMIN_PENDINGWITHDRAWAL);
        PendingWithdrawal pendingWithdrawal = pendingWithdrawalRepository.findByRecordId(request.getPendingWithdrawalDtoList().get(0).getRecordId());
        if( pendingWithdrawal !=  null){
            pendingWithdrawalWsDto.setPendingWithdrawalDtoList(List.of(modelMapper.map(pendingWithdrawal, PendingWithdrawalDto.class)));
        }
        return pendingWithdrawalWsDto;
    }

    @PostMapping("/edit")
    @ResponseBody
    public PendingWithdrawalWsDto handleEdit(@RequestBody PendingWithdrawalWsDto request)  {
        return pendingWithdrawalService.handleEdit(request);
    }

    @GetMapping("/add")
    @ResponseBody
    public PendingWithdrawalWsDto addPendingWithdrawal() {

        PendingWithdrawalWsDto pendingWithdrawalWsDto = new PendingWithdrawalWsDto();
        pendingWithdrawalWsDto.setPendingWithdrawalDtoList(modelMapper.map(pendingWithdrawalRepository.findByStatusOrderByIdentifier(true),List.class));
        pendingWithdrawalWsDto.setBaseUrl(ADMIN_PENDINGWITHDRAWAL);
        return pendingWithdrawalWsDto;
    }
    @PostMapping("/delete")
    @ResponseBody
    public PendingWithdrawalWsDto deletePendingWithdrawal(@RequestBody PendingWithdrawalWsDto pendingWithdrawalWsDto) {
        for (PendingWithdrawalDto pendingWithdrawalDto :pendingWithdrawalWsDto.getPendingWithdrawalDtoList()){
            pendingWithdrawalRepository.deleteByRecordId(pendingWithdrawalDto.getRecordId());
        }
        pendingWithdrawalWsDto.setMessage("Data deleted successfully");
        pendingWithdrawalWsDto.setBaseUrl(ADMIN_PENDINGWITHDRAWAL);
        return pendingWithdrawalWsDto;

    }
}
