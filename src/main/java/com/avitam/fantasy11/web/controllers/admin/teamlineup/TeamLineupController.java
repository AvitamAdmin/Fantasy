package com.avitam.fantasy11.web.controllers.admin.teamlineup;

import com.avitam.fantasy11.api.dto.SearchDto;
import com.avitam.fantasy11.api.dto.TeamLineUpDto;
import com.avitam.fantasy11.api.dto.TeamLineUpWsDto;
import com.avitam.fantasy11.api.service.TeamLineUpService;
import com.avitam.fantasy11.model.TeamLineup;
import com.avitam.fantasy11.repository.TeamLineupRepository;
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
@RequestMapping("/admin/teamLineup")
public class TeamLineupController extends BaseController {

    public static final String ADMIN_TEAMLINEUP = "/admin/teamLineup";
    @Autowired
    private TeamLineupRepository teamLineupRepository;
    @Autowired
    private TeamLineUpService teamLineUpService;
    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    @ResponseBody
    public TeamLineUpWsDto getAllTeamLineUp(@RequestBody TeamLineUpWsDto teamLineUpWsDto) {
        Pageable pageable = getPageable(teamLineUpWsDto.getPage(), teamLineUpWsDto.getSizePerPage(), teamLineUpWsDto.getSortDirection(), teamLineUpWsDto.getSortField());
        TeamLineUpDto teamLineUpDto = CollectionUtils.isNotEmpty(teamLineUpWsDto.getTeamLineUpDtoList()) ? teamLineUpWsDto.getTeamLineUpDtoList().get(0) : new TeamLineUpDto();
        TeamLineup teamLineup = modelMapper.map(teamLineUpDto, TeamLineup.class);
        Page<TeamLineup> page = isSearchActive(teamLineup) == null ? teamLineupRepository.findAll(Example.of(teamLineup), pageable) : teamLineupRepository.findAll(pageable);
        teamLineUpWsDto.setTeamLineUpDtoList(modelMapper.map(page.getContent(), List.class));
        teamLineUpWsDto.setTotalPages(page.getTotalPages());
        teamLineUpWsDto.setTotalRecords(page.getTotalElements());
        teamLineUpWsDto.setBaseUrl(ADMIN_TEAMLINEUP);
        return teamLineUpWsDto;
    }

    @GetMapping("/get")
    @ResponseBody
    public TeamLineUpWsDto getActiveTeamLineUp() {
        TeamLineUpWsDto teamLineUpWsDto = new TeamLineUpWsDto();
        teamLineUpWsDto.setTeamLineUpDtoList(modelMapper.map(teamLineupRepository.findByStatusOrderByIdentifier(true), List.class));
        teamLineUpWsDto.setBaseUrl(ADMIN_TEAMLINEUP);
        return teamLineUpWsDto;
    }

    @PostMapping("/getedit")
    @ResponseBody
    public TeamLineUpWsDto editTeamLineup(@RequestBody TeamLineUpWsDto request) {

        TeamLineUpWsDto teamLineUpWsDto = new TeamLineUpWsDto();
        TeamLineup teamLineup = teamLineupRepository.findByRecordId(request.getTeamLineUpDtoList().get(0).getRecordId());
        teamLineUpWsDto.setTeamLineUpDtoList(List.of(modelMapper.map(teamLineup, TeamLineUpDto.class)));
        teamLineUpWsDto.setBaseUrl(ADMIN_TEAMLINEUP);
        return teamLineUpWsDto;
    }

    @PostMapping("/edit")
    @ResponseBody
    public TeamLineUpWsDto handleEdit(@RequestBody TeamLineUpWsDto request) {
        return teamLineUpService.handleEdit(request);
    }


    @PostMapping("/delete")
    @ResponseBody
    public TeamLineUpWsDto deleteTeamLineup(@RequestBody TeamLineUpWsDto teamLineUpWsDto) {
        for (TeamLineUpDto data : teamLineUpWsDto.getTeamLineUpDtoList()) {
            teamLineupRepository.deleteByRecordId(data.getRecordId());
        }
        teamLineUpWsDto.setMessage("Data deleted successfully");
        teamLineUpWsDto.setBaseUrl(ADMIN_TEAMLINEUP);
        return teamLineUpWsDto;
    }

    @GetMapping("/getAdvancedSearch")
    @ResponseBody

    public List<SearchDto> getSearchAttributes() {
        return getGroupedParentAndChildAttributes(new TeamLineup());
    }


}