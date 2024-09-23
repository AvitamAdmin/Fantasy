package com.avitam.fantasy11.web.controllers.admin.contestjoined;

import com.avitam.fantasy11.api.dto.ContestJoinedDto;
import com.avitam.fantasy11.api.service.ContestJoinedService;
import com.avitam.fantasy11.model.*;
import com.avitam.fantasy11.web.controllers.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/admin/contestJoined")
public class ContestJoinedController extends BaseController {

    @Autowired
    private ContestJoinedRepository contestJoinedRepository;
    @Autowired
    private ContestJoinedService contestJoinedService;
    private static final String ADMIN_CONTESTJOINED="/admin/contestJoined";

    @PostMapping
    @ResponseBody
    public ContestJoinedDto getAllContestJoined(@RequestBody ContestJoinedDto contestJoinedDto) {
        Pageable pageable=getPageable(contestJoinedDto.getPage(),contestJoinedDto.getSizePerPage(),contestJoinedDto.getSortDirection(),contestJoinedDto.getSortField());
        ContestJoined contestJoined=contestJoinedDto.getContestJoined();
        Page<ContestJoined> page=isSearchActive(contestJoined) !=null ? contestJoinedRepository.findAll(Example.of(contestJoined),pageable) :contestJoinedRepository.findAll(pageable);
        contestJoinedDto.setContestJoinedList(page.getContent());
        contestJoinedDto.setBaseUrl(ADMIN_CONTESTJOINED);
        contestJoinedDto.setTotalPages(page.getTotalPages());
        contestJoinedDto.setTotalRecords(page.getTotalElements());
        return contestJoinedDto;
    }
    @GetMapping("/get")
    @ResponseBody
    public ContestJoinedDto getContestJoined(){
        ContestJoinedDto contestJoinedDto=new ContestJoinedDto();
        contestJoinedDto.setContestJoinedList(contestJoinedRepository.findByStatusOrderByIdentifier(true));
        contestJoinedDto.setBaseUrl(ADMIN_CONTESTJOINED);
        return contestJoinedDto;
    }

    @PostMapping("/getedit")
    @ResponseBody
    public ContestJoinedDto edit(@RequestBody ContestJoinedDto request) {
        ContestJoinedDto contestJoinedDto=new ContestJoinedDto();
        ContestJoined contestJoined=contestJoinedRepository.findByRecordId(request.getRecordId());
        contestJoinedDto.setContestJoined(contestJoined);
        contestJoinedDto.setBaseUrl(ADMIN_CONTESTJOINED);
        return contestJoinedDto;
    }

    @PostMapping("/edit")
    @ResponseBody
    public ContestJoinedDto handleEdit(@RequestBody ContestJoinedDto request) {

        return contestJoinedService.handleEdit(request);
    }

    @GetMapping("/add")
    @ResponseBody
    public ContestJoinedDto add() {
        ContestJoinedDto contestJoinedDto = new ContestJoinedDto();
        contestJoinedDto.setContestJoinedList(contestJoinedRepository.findByStatusOrderByIdentifier(true));
        contestJoinedDto.setBaseUrl(ADMIN_CONTESTJOINED);
        return contestJoinedDto;
    }

    @PostMapping("/delete")
    @ResponseBody
    public ContestJoinedDto delete(@RequestBody ContestJoinedDto contestJoinedDto) {
        for (String id : contestJoinedDto.getRecordId().split(",")) {
            contestJoinedRepository.deleteByRecordId(id);
        }
        contestJoinedDto.setMessage("Data deleted Successfully");
        contestJoinedDto.setBaseUrl(ADMIN_CONTESTJOINED);
        return contestJoinedDto;
    }
}
