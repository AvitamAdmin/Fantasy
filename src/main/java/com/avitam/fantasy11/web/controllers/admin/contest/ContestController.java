package com.avitam.fantasy11.web.controllers.admin.contest;

import com.avitam.fantasy11.api.dto.ContestDto;
import com.avitam.fantasy11.api.dto.PaginationDto;
import com.avitam.fantasy11.api.service.ContestService;
import com.avitam.fantasy11.core.service.CoreService;
import com.avitam.fantasy11.model.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/contest")
public class ContestController {

    @Autowired
    private ContestRepository contestRepository;

    @Autowired(required = false)
    private ContestService contestService;

    @GetMapping("/get")
    @ResponseBody
    public ContestDto getAllContest(PaginationDto paginationDto) {
        ContestDto contestDto = new ContestDto();
        contestDto.setContestList(contestRepository.findAll(Pageable.unpaged()).getContent());
        Pageable pageable = PageRequest.of(paginationDto.getPage(), paginationDto.getSizePerPage(), paginationDto.getSortDirection(), "identifier");
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
