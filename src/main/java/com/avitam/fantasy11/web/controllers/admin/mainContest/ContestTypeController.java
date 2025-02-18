package com.avitam.fantasy11.web.controllers.admin.mainContest;


import com.avitam.fantasy11.api.dto.ContestTypeDto;
import com.avitam.fantasy11.api.dto.ContestTypeWsDto;
import com.avitam.fantasy11.api.dto.SearchDto;
import com.avitam.fantasy11.api.service.ContestTypeService;
import com.avitam.fantasy11.model.ContestType;
import com.avitam.fantasy11.repository.ContestTypeRepository;
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
@RequestMapping("/admin/contestType")
public class ContestTypeController extends BaseController {

    private static final String ADMIN_MAINCONTEST = "/admin/contestType";
    @Autowired
    ContestTypeService contestTypeService;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    private ContestTypeRepository contestTypeRepository;

    @PostMapping
    @ResponseBody
    public ContestTypeWsDto getAllContest(@RequestBody ContestTypeWsDto contestTypeWsDto) {
        Pageable pageable = getPageable(contestTypeWsDto.getPage(), contestTypeWsDto.getSizePerPage(), contestTypeWsDto.getSortDirection(), contestTypeWsDto.getSortField());
        ContestTypeDto contestTypeDto = CollectionUtils.isNotEmpty(contestTypeWsDto.getContestTypeDtoList()) ? contestTypeWsDto.getContestTypeDtoList().get(0) : new ContestTypeDto();
        ContestType contestType = modelMapper.map(contestTypeDto, ContestType.class);
        Page<ContestType> page = isSearchActive(contestType) == null ? contestTypeRepository.findAll(Example.of(contestType),pageable): contestTypeRepository.findAll(pageable);
        contestTypeWsDto.setContestTypeDtoList(modelMapper.map(page.getContent(), List.class));
        contestTypeWsDto.setBaseUrl(ADMIN_MAINCONTEST);
        contestTypeWsDto.setTotalPages(page.getTotalPages());
        contestTypeWsDto.setTotalRecords(page.getTotalElements());
        return contestTypeWsDto;
    }

    @GetMapping("/get")
    @ResponseBody
    public ContestTypeWsDto getMainContest() {
        ContestTypeWsDto contestTypeWsDto = new ContestTypeWsDto();
        contestTypeWsDto.setContestTypeDtoList(modelMapper.map(contestTypeRepository.findByStatusOrderByIdentifier(true), List.class));
        contestTypeWsDto.setBaseUrl(ADMIN_MAINCONTEST);
        return contestTypeWsDto;
    }


    @PostMapping("/getedit")
    @ResponseBody
    public ContestTypeWsDto editMainContest(@RequestBody ContestTypeWsDto request) {
        ContestType contestType = contestTypeRepository.findByRecordId(request.getContestTypeDtoList().get(0).getRecordId());
        request.setContestTypeDtoList(List.of(modelMapper.map(contestType, ContestTypeDto.class)));
        request.setBaseUrl(ADMIN_MAINCONTEST);
        return request;
    }

    @PostMapping("/edit")
    @ResponseBody
    public ContestTypeWsDto handleEdit(@RequestBody ContestTypeWsDto request) {
        return contestTypeService.handleEdit(request);
    }


    @PostMapping("/delete")
    @ResponseBody
    public ContestTypeWsDto deleteContest(@RequestBody ContestTypeWsDto contestTypeWsDto) {

        for (ContestTypeDto contestTypeDto : contestTypeWsDto.getContestTypeDtoList()) {
            contestTypeRepository.deleteByRecordId(contestTypeDto.getRecordId());
        }
        contestTypeWsDto.setMessage("Data deleted Successfully");
        contestTypeWsDto.setBaseUrl(ADMIN_MAINCONTEST);
        return contestTypeWsDto;
    }

    @GetMapping("/getAdvancedSearch")
    @ResponseBody
    public List<SearchDto> getSearchAttributes() {
        return getGroupedParentAndChildAttributes(new ContestType());
    }
}
