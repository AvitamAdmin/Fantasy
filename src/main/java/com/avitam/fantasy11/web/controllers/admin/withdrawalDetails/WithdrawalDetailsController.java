package com.avitam.fantasy11.web.controllers.admin.withdrawalDetails;

import com.avitam.fantasy11.api.dto.SearchDto;
import com.avitam.fantasy11.api.dto.WithdrawalDetailsDto;
import com.avitam.fantasy11.api.dto.WithdrawalDetailsWsDto;
import com.avitam.fantasy11.api.service.WithdrawalDetailsService;
import com.avitam.fantasy11.model.WithdrawalDetails;
import com.avitam.fantasy11.repository.WithdrawalDetailsRepository;
import com.avitam.fantasy11.web.controllers.BaseController;
import org.apache.commons.collections4.CollectionUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/withdrawalDetails")
public class WithdrawalDetailsController extends BaseController {
    public static final String ADMIN_WITHDRAWALDETAILS = "/admin/withdrawalDetails";
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private WithdrawalDetailsService withdrawalDetailsService;
    @Autowired
    private WithdrawalDetailsRepository withdrawalDetailsRepository;

    @PostMapping
    @ResponseBody
    public WithdrawalDetailsWsDto getAllWithdrawalDetails(@RequestBody WithdrawalDetailsWsDto withdrawalDetailsWsDto) {
        Pageable pageable = getPageable(withdrawalDetailsWsDto.getPage(), withdrawalDetailsWsDto.getSizePerPage(), withdrawalDetailsWsDto.getSortDirection(), withdrawalDetailsWsDto.getSortField());
        WithdrawalDetailsDto withdrawalDetailsDto = CollectionUtils.isNotEmpty(withdrawalDetailsWsDto.getWithdrawalDetailsDtoList()) ? withdrawalDetailsWsDto.getWithdrawalDetailsDtoList().get(0) : new WithdrawalDetailsDto();
        WithdrawalDetails withdrawalDetails = modelMapper.map(withdrawalDetailsDto, WithdrawalDetails.class);
        Page<WithdrawalDetails> page = isSearchActive(withdrawalDetails) == null ? withdrawalDetailsRepository.findAll(Example.of(withdrawalDetails), pageable) : withdrawalDetailsRepository.findAll(pageable);
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
        withdrawalDetailsWsDto.setWithdrawalDetailsDtoList(modelMapper.map(withdrawalDetailsRepository.findByStatusOrderByIdentifier(true), List.class));
        withdrawalDetailsWsDto.setBaseUrl(ADMIN_WITHDRAWALDETAILS);
        return withdrawalDetailsWsDto;
    }


    @PostMapping("/getedit")
    @ResponseBody
    public WithdrawalDetailsWsDto editPendingWithdrawal(@RequestBody WithdrawalDetailsWsDto request) {
        WithdrawalDetails withdrawalDetails = withdrawalDetailsRepository.findByRecordId(request.getWithdrawalDetailsDtoList().get(0).getRecordId());
        request.setWithdrawalDetailsDtoList(List.of(modelMapper.map(withdrawalDetails, WithdrawalDetailsDto.class)));
        request.setBaseUrl(ADMIN_WITHDRAWALDETAILS);
        return request;
    }


    @PostMapping("/edit")
    @ResponseBody
    public WithdrawalDetailsWsDto handleEdit(@RequestBody WithdrawalDetailsWsDto request) {
        return withdrawalDetailsService.handleEdit(request);
    }


    @PostMapping("/delete")
    @ResponseBody
    public WithdrawalDetailsWsDto deleteWithdrawalDetails(@RequestBody WithdrawalDetailsWsDto withdrawalDetailsWsDto) {
        for (WithdrawalDetailsDto data : withdrawalDetailsWsDto.getWithdrawalDetailsDtoList()) {
            withdrawalDetailsRepository.deleteByRecordId(data.getRecordId());
        }
        withdrawalDetailsWsDto.setMessage("Data deleted successfully");
        withdrawalDetailsWsDto.setBaseUrl(ADMIN_WITHDRAWALDETAILS);
        return withdrawalDetailsWsDto;
    }

    @GetMapping("/getAdvancedSearch")
    @ResponseBody

    public List<SearchDto> getSearchAttributes() {
        return getGroupedParentAndChildAttributes(new WithdrawalDetails());
    }

}