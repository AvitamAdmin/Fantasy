package com.avitam.fantasy11.web.controllers.admin.withdrawalMethods;

import com.avitam.fantasy11.api.dto.WithdrawalMethodsDto;
import com.avitam.fantasy11.api.service.WithdrawalMethodsService;
import com.avitam.fantasy11.core.service.CoreService;
import com.avitam.fantasy11.form.GatewaysAutomaticForm;
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
import java.util.*;

@Controller
@RequestMapping("/admin/withdrawalMethods")
public class WithdrawalMethodsController extends BaseController {
    @Autowired
    private WithdrawalMethodsRepository withdrawalMethodsRepository;
    @Autowired
    private WithdrawalMethodsService withdrawalMethodsService;
    @Autowired
    private CoreService coreService;
    @Autowired
    private ModelMapper modelMapper;

    public static final String ADMIN_WITHDRAWALMETHODS = "/admin/withdrawalMethods";

    @PostMapping
    @ResponseBody
    public WithdrawalMethodsDto getAll(@RequestBody WithdrawalMethodsDto withdrawalMethodsDto){
        Pageable pageable = getPageable(withdrawalMethodsDto.getPage(), withdrawalMethodsDto.getSizePerPage(), withdrawalMethodsDto.getSortDirection(), withdrawalMethodsDto.getSortField());
        WithdrawalMethods withdrawalMethods = withdrawalMethodsDto.getWithdrawalMethods();
        Page<WithdrawalMethods> page = isSearchActive(withdrawalMethods)!=null ? withdrawalMethodsRepository.findAll(Example.of(withdrawalMethods), pageable) : withdrawalMethodsRepository.findAll(pageable);
        withdrawalMethodsDto.setWithdrawalMethodsList(page.getContent());
        withdrawalMethodsDto.setTotalPages(page.getTotalPages());
        withdrawalMethodsDto.setTotalRecords(page.getTotalElements());
        withdrawalMethodsDto.setBaseUrl(ADMIN_WITHDRAWALMETHODS);
        return withdrawalMethodsDto;
    }

    @GetMapping("/get")
    @ResponseBody
    public WithdrawalMethodsDto getActiveWithdrawalMethods(){
        WithdrawalMethodsDto withdrawalMethodsDto = new WithdrawalMethodsDto();
        withdrawalMethodsDto.setWithdrawalMethodsList(withdrawalMethodsRepository.findByStatusOrderByIdentifier(true));
        withdrawalMethodsDto.setBaseUrl(ADMIN_WITHDRAWALMETHODS);
        return withdrawalMethodsDto;
    }
    @GetMapping("/edit")
    @ResponseBody
    public WithdrawalMethodsDto editWithdrawalMethods (@RequestBody WithdrawalMethodsDto request){

        WithdrawalMethodsDto withdrawalMethodsDto = new WithdrawalMethodsDto();
        withdrawalMethodsDto.setWithdrawalMethods(withdrawalMethodsRepository.findByRecordId(request.getRecordId()));
        withdrawalMethodsDto.setBaseUrl(ADMIN_WITHDRAWALMETHODS);
        return withdrawalMethodsDto;
    }

    @PostMapping("/edit")
    @ResponseBody
    public WithdrawalMethodsDto handleEdit(@RequestBody WithdrawalMethodsDto request)  {
        return withdrawalMethodsService.handleEdit(request);
    }

    @GetMapping("/add")
    @ResponseBody
    public WithdrawalMethodsDto addWithdrawalMethods() {

        WithdrawalMethodsDto withdrawalMethodsDto = new WithdrawalMethodsDto();
        withdrawalMethodsDto.setWithdrawalMethodsList(withdrawalMethodsRepository.findByStatusOrderByIdentifier(true));
        withdrawalMethodsDto.setBaseUrl(ADMIN_WITHDRAWALMETHODS);
        return withdrawalMethodsDto;
    }
    @PostMapping("/delete")
    @ResponseBody
    public WithdrawalMethodsDto deleteWithdrawalMethods(@RequestBody WithdrawalMethodsDto withdrawalMethodsDto) {
        for (String id : withdrawalMethodsDto.getRecordId().split(",")) {
            withdrawalMethodsRepository.deleteById(new ObjectId(id));
        }
        withdrawalMethodsDto.setMessage("Data deleted successfully");
        withdrawalMethodsDto.setBaseUrl(ADMIN_WITHDRAWALMETHODS);
        return withdrawalMethodsDto;
    }
}
