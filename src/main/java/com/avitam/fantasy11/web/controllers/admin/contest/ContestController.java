package com.avitam.fantasy11.web.controllers.admin.contest;

import com.avitam.fantasy11.api.ContestCategory;
import com.avitam.fantasy11.api.dto.ContestDto;
import com.avitam.fantasy11.api.dto.ContestWsDto;
import com.avitam.fantasy11.api.dto.PlayerDto;
import com.avitam.fantasy11.api.dto.PlayerWsDto;
import com.avitam.fantasy11.api.service.ContestService;
import com.avitam.fantasy11.model.Contest;
import com.avitam.fantasy11.model.Matches;
import com.avitam.fantasy11.model.Player;
import com.avitam.fantasy11.repository.ContestRepository;
import com.avitam.fantasy11.repository.MatchesRepository;
import com.avitam.fantasy11.web.controllers.BaseController;
import org.apache.commons.collections4.CollectionUtils;
import org.checkerframework.checker.units.qual.A;
import org.checkerframework.checker.units.qual.C;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin/contest")
public class ContestController extends BaseController {

    private static final String ADMIN_CONTEST = "/admin/contest";
    @Autowired
    private ContestRepository contestRepository;
    @Autowired
    private ContestService contestService;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private MatchesRepository matchesRepository;


    @PostMapping
    @ResponseBody
    public ContestWsDto getAllContest(@RequestBody ContestWsDto contestwsDto) {
        Pageable pageable = getPageable(contestwsDto.getPage(), contestwsDto.getSizePerPage(), contestwsDto.getSortDirection(), contestwsDto.getSortField());
        ContestDto contestDto = CollectionUtils.isNotEmpty(contestwsDto.getContestDtos()) ? contestwsDto.getContestDtos().get(0) : new ContestDto();
        Contest contest = modelMapper.map(contestDto, Contest.class);
        Page<Contest> page = isSearchActive(contest) == null ? contestRepository.findAll(Example.of(contest), pageable) : contestRepository.findAll(pageable);
        contestwsDto.setContestDtos(modelMapper.map(page.getContent(), List.class));
        contestwsDto.setTotalPages(page.getTotalPages());
        contestwsDto.setTotalRecords(page.getTotalElements());
        contestwsDto.setBaseUrl(ADMIN_CONTEST);
        return contestwsDto;
    }

    @GetMapping("/get")
    @ResponseBody
    public ContestWsDto getActiveContest() {
        ContestWsDto contestwsDto = new ContestWsDto();
        contestwsDto.setBaseUrl(ADMIN_CONTEST);
        contestwsDto.setContestDtos(modelMapper.map(contestRepository.findByStatusOrderByIdentifier(true), List.class));
        return contestwsDto;
    }

    @PostMapping("/getedit")
    @ResponseBody
    public ContestWsDto editContest(@RequestBody ContestWsDto request) {
        request.setBaseUrl(ADMIN_CONTEST);
        Contest contest = contestRepository.findByRecordId(request.getContestDtos().get(0).getRecordId());
        request.setContestDtos(List.of(modelMapper.map(contest, ContestDto.class)));
        return request;
    }

    @PostMapping("/getContests")
    @ResponseBody
    public ContestWsDto getContestByMatchId(@RequestBody ContestWsDto request) {
        List<Contest> contestList = new ArrayList<>();
        List<ContestDto> contestDtos = request.getContestDtos();
        for (ContestDto contestDto : contestDtos) {
            List<Contest> contests = contestRepository.findContestByMatchId(contestDto.getMatchId());
            contestList.addAll(contests);
            contestList.addAll(contestRepository.findByCommonContest(true));
        }
        request.setContestDtos(modelMapper.map(contestList, List.class));
        return request;
    }

    @PostMapping("/edit")
    @ResponseBody
    public ContestWsDto handleEdit(@RequestBody ContestWsDto request) {
        return contestService.handleEdit(request);
    }

    @PostMapping("/delete")
    @ResponseBody
    public ContestWsDto deleteContest(@RequestBody ContestWsDto contestwsDto) {
        for (ContestDto contestDto : contestwsDto.getContestDtos()) {
            contestRepository.deleteByRecordId(contestDto.getRecordId());
        }
        contestwsDto.setMessage("Data deleted Successfully");
        contestwsDto.setBaseUrl(ADMIN_CONTEST);
        return contestwsDto;
    }


}
