package com.avitam.fantasy11.web.controllers.admin.withdrawalMethods;

import com.avitam.fantasy11.api.dto.SearchDto;
import com.avitam.fantasy11.api.dto.WithdrawalMethodsDto;
import com.avitam.fantasy11.api.dto.WithdrawalMethodsWsDto;
import com.avitam.fantasy11.api.service.WithdrawalMethodsService;
import com.avitam.fantasy11.model.WithdrawalMethods;
import com.avitam.fantasy11.repository.WithdrawalMethodsRepository;
import com.avitam.fantasy11.web.controllers.BaseController;
import org.apache.commons.collections4.CollectionUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/withdrawalMethods")
public class WithdrawalMethodsController extends BaseController {
    public static final String ADMIN_WITHDRAWALMETHODS = "/admin/withdrawalMethods";
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    private WithdrawalMethodsRepository withdrawalMethodsRepository;
    @Autowired
    private WithdrawalMethodsService withdrawalMethodsService;

    @PostMapping
    @ResponseBody
    public WithdrawalMethodsWsDto getAllWithdrawalMethod(@RequestBody WithdrawalMethodsWsDto withdrawalMethodsWsDto) {
        Pageable pageable = getPageable(withdrawalMethodsWsDto.getPage(), withdrawalMethodsWsDto.getSizePerPage(), withdrawalMethodsWsDto.getSortDirection(), withdrawalMethodsWsDto.getSortField());
        WithdrawalMethodsDto withdrawalMethodsDto = CollectionUtils.isNotEmpty(withdrawalMethodsWsDto.getWithdrawalMethodsDtoList()) ? withdrawalMethodsWsDto.getWithdrawalMethodsDtoList().get(0) : new WithdrawalMethodsDto();
        WithdrawalMethods withdrawalMethods = modelMapper.map(withdrawalMethodsDto, WithdrawalMethods.class);
        Page<WithdrawalMethods> page = isSearchActive(withdrawalMethods) == null ? withdrawalMethodsRepository.findAll(Example.of(withdrawalMethods), pageable) : withdrawalMethodsRepository.findAll(pageable);
        withdrawalMethodsWsDto.setWithdrawalMethodsDtoList(modelMapper.map(page.getContent(), List.class));
        withdrawalMethodsWsDto.setTotalPages(page.getTotalPages());
        withdrawalMethodsWsDto.setTotalRecords(page.getTotalElements());
        withdrawalMethodsWsDto.setBaseUrl(ADMIN_WITHDRAWALMETHODS);
        return withdrawalMethodsWsDto;
    }


    @GetMapping("/get")
    @ResponseBody
    public WithdrawalMethodsWsDto getActiveWithdrawalMethods() {
        WithdrawalMethodsWsDto withdrawalMethodsWsDto = new WithdrawalMethodsWsDto();
        withdrawalMethodsWsDto.setWithdrawalMethodsDtoList(modelMapper.map(withdrawalMethodsRepository.findByStatusOrderByIdentifier(true), List.class));
        withdrawalMethodsWsDto.setBaseUrl(ADMIN_WITHDRAWALMETHODS);
        return withdrawalMethodsWsDto;
    }


    @PostMapping("/getedit")
    @ResponseBody
    public WithdrawalMethodsWsDto editWithdrawalMethods(@RequestBody WithdrawalMethodsWsDto request) {
        WithdrawalMethods withdrawalMethods = withdrawalMethodsRepository.findByRecordId(request.getWithdrawalMethodsDtoList().get(0).getRecordId());
        request.setWithdrawalMethodsDtoList(List.of(modelMapper.map(withdrawalMethods, WithdrawalMethodsDto.class)));
        request.setBaseUrl(ADMIN_WITHDRAWALMETHODS);
        return request;
    }


    @PostMapping(value = "/edit", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public WithdrawalMethodsWsDto handleEdit(@ModelAttribute WithdrawalMethodsWsDto request) {
        return withdrawalMethodsService.handleEdit(request);
    }


    @PostMapping("/delete")
    @ResponseBody
    public WithdrawalMethodsWsDto deleteWithdrawalMethods(@RequestBody WithdrawalMethodsWsDto withdrawalMethodsWsDto) {
        for (WithdrawalMethodsDto data : withdrawalMethodsWsDto.getWithdrawalMethodsDtoList()) {
            withdrawalMethodsRepository.deleteByRecordId(data.getRecordId());
        }
        withdrawalMethodsWsDto.setMessage("Data deleted successfully");
        withdrawalMethodsWsDto.setBaseUrl(ADMIN_WITHDRAWALMETHODS);
        return withdrawalMethodsWsDto;
    }

    @GetMapping("/getAdvancedSearch")
    @ResponseBody

    public List<SearchDto> getSearchAttributes() {
        return getGroupedParentAndChildAttributes(new WithdrawalMethods());
    }


}