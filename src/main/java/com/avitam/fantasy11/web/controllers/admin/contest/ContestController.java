package com.avitam.fantasy11.web.controllers.admin.contest;

import com.avitam.fantasy11.api.dto.ContestDto;
import com.avitam.fantasy11.api.service.ContestService;
import com.avitam.fantasy11.model.*;
import com.avitam.fantasy11.web.controllers.BaseController;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/contest")
public class ContestController extends BaseController {

    @Autowired
    private ContestRepository contestRepository;

    @Autowired
    private ContestService contestService;
    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    @ResponseBody
    public ContestDto getAllContest( ContestDto contestDto) {
        Pageable pageable = getPageable(contestDto.getPage(),contestDto.getSizePerPage(),contestDto.getSortDirection(),contestDto.getSortField());
        Contest contest=contestDto.getContest();
        Page<Contest> page=isSearchActive(contest)!=null?contestRepository.findAll(Example.of(contest),pageable):contestRepository.findAll(pageable);
        contestDto.setContestList(page.getContent());
        contestDto.setTotalPages(page.getTotalPages());
        contestDto.setTotalRecords(page.getTotalElements());
        return contestDto;
    }

    @GetMapping("/edit")
    @ResponseBody
    public ContestDto editContest(@RequestBody ContestDto request) {
        ContestDto contestDto = new ContestDto();
        Contest contest = contestRepository.findByRecordId(request.getRecordId());
        return contestDto;
    }

    @PostMapping("/edit")
    @ResponseBody
    public ContestDto handleEdit(@RequestBody ContestDto request) {

        return contestService.handleEdit(request);
    }

    @GetMapping("/add")
    @ResponseBody
    public ContestDto addContest() {
        ContestDto contestDto = new ContestDto();
        contestDto.setContestList(contestRepository.findStatusOrderByIdentifier(true));
        return contestDto;
    }

    @PostMapping("/delete")
    @ResponseBody
    public ContestDto deleteContest(@RequestBody ContestDto contestDto) {

        for (String id : contestDto.getRecordId().split(",")) {
            contestRepository.deleteByRecordId(id);
        }
        contestDto.setMessage("Data deleted Successfully");
        return contestDto;
    }
}
