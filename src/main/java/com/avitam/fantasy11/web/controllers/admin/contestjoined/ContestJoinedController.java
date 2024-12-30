package com.avitam.fantasy11.web.controllers.admin.contestjoined;

import com.avitam.fantasy11.api.dto.ContestJoinedDto;
import com.avitam.fantasy11.api.dto.ContestJoinedWsDto;
import com.avitam.fantasy11.api.service.ContestJoinedService;
import com.avitam.fantasy11.model.ContestJoined;
import com.avitam.fantasy11.repository.ContestJoinedRepository;
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
@RequestMapping("/admin/contestJoined")
public class ContestJoinedController extends BaseController {

    @Autowired
    private ContestJoinedRepository contestJoinedRepository;
    @Autowired
    private ContestJoinedService contestJoinedService;
    @Autowired
    private ModelMapper modelMapper;

    private static final String ADMIN_CONTESTJOINED = "/admin/contestJoined";

    @PostMapping
    @ResponseBody
    public ContestJoinedWsDto getAllContestJoined(@RequestBody ContestJoinedWsDto contestJoinedWsDto) {
        Pageable pageable = getPageable(contestJoinedWsDto.getPage(), contestJoinedWsDto.getSizePerPage(), contestJoinedWsDto.getSortDirection(), contestJoinedWsDto.getSortField());
        ContestJoinedDto contestJoinedDto = CollectionUtils.isNotEmpty(contestJoinedWsDto.getContestJoinedDtoList()) ? contestJoinedWsDto.getContestJoinedDtoList().get(0) : new ContestJoinedDto();
        ContestJoined contestJoined = modelMapper.map(contestJoinedDto, ContestJoined.class);
        Page<ContestJoined> page = isSearchActive(contestJoined) != null ? contestJoinedRepository.findAll(Example.of(contestJoined), pageable) : contestJoinedRepository.findAll(pageable);
        contestJoinedWsDto.setContestJoinedDtoList(modelMapper.map(page.getContent(), List.class));
        contestJoinedWsDto.setBaseUrl(ADMIN_CONTESTJOINED);
        contestJoinedWsDto.setTotalPages(page.getTotalPages());
        contestJoinedWsDto.setTotalRecords(page.getTotalElements());
        return contestJoinedWsDto;

    }

    @GetMapping("/get")
    @ResponseBody
    public ContestJoinedWsDto getContestJoined() {
        ContestJoinedWsDto contestJoinedwsDto = new ContestJoinedWsDto();
        contestJoinedwsDto.setBaseUrl(ADMIN_CONTESTJOINED);
        contestJoinedwsDto.setContestJoinedDtoList(modelMapper.map(contestJoinedRepository.findByStatusOrderByIdentifier(true), List.class));
        return contestJoinedwsDto;
    }

    @PostMapping("/getedit")
    @ResponseBody
    public ContestJoinedWsDto edit(@RequestBody ContestJoinedWsDto request) {
        ContestJoinedWsDto contestJoinedwsDto = new ContestJoinedWsDto();
        contestJoinedwsDto.setBaseUrl(ADMIN_CONTESTJOINED);
        contestJoinedwsDto.setContestJoinedDtoList((modelMapper.map(contestJoinedRepository.findByRecordId(request.getContestJoinedDtoList().get(0).getRecordId()), List.class)));
        return contestJoinedwsDto;
    }

    @PostMapping("/edit")
    @ResponseBody
    public ContestJoinedWsDto handleEdit(@RequestBody ContestJoinedWsDto request) {

        return contestJoinedService.handleEdit(request);
    }

    @PostMapping("/delete")
    @ResponseBody
    public ContestJoinedWsDto delete(@RequestBody ContestJoinedWsDto contestJoinedWsDto) {
        for (ContestJoinedDto contestJoinedDto : contestJoinedWsDto.getContestJoinedDtoList()) {
            contestJoinedRepository.deleteByRecordId(contestJoinedDto.getRecordId());
        }
        contestJoinedWsDto.setMessage("Data deleted Successfully");
        contestJoinedWsDto.setBaseUrl(ADMIN_CONTESTJOINED);
        return contestJoinedWsDto;
    }
}
