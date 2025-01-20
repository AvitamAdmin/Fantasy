package com.avitam.fantasy11.web.controllers.admin.team;

import com.avitam.fantasy11.api.dto.TeamDto;
import com.avitam.fantasy11.api.dto.TeamWsDto;
import com.avitam.fantasy11.api.service.TeamService;
import com.avitam.fantasy11.model.Team;
import com.avitam.fantasy11.repository.TeamRepository;
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
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin/team")
public class TeamController extends BaseController {
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private TeamService teamService;
    @Autowired
    private ModelMapper modelMapper;
    public static final String ADMIN_TEAM = "/admin/team";

    @PostMapping
    @ResponseBody
    public TeamWsDto getAllTeams(@RequestBody TeamWsDto teamWsDto) {

        Pageable pageable = getPageable(teamWsDto.getPage(), teamWsDto.getSizePerPage(), teamWsDto.getSortDirection(), teamWsDto.getSortField());
        TeamDto teamDto = CollectionUtils.isNotEmpty(teamWsDto.getTeamDtoList()) ? teamWsDto.getTeamDtoList().get(0) : new TeamDto();
        Team team = modelMapper.map(teamDto, Team.class);
        Page<Team> page = isSearchActive(team) != null ? teamRepository.findAll(Example.of(team), pageable) : teamRepository.findAll(pageable);
        teamWsDto.setTeamDtoList(modelMapper.map(page.getContent(), List.class));
        teamWsDto.setTotalPages(page.getTotalPages());
        teamWsDto.setTotalRecords(page.getTotalElements());
        teamWsDto.setBaseUrl(ADMIN_TEAM);
        return teamWsDto;
    }

    @GetMapping("/get")
    @ResponseBody
    public TeamWsDto getActiveTeamList() {
        TeamWsDto teamWsDto = new TeamWsDto();
        teamWsDto.setTeamDtoList(modelMapper.map(teamRepository.findByStatusOrderByIdentifier(true), List.class));
        teamWsDto.setBaseUrl(ADMIN_TEAM);
        return teamWsDto;
    }

    @PostMapping("/getedit")
    @ResponseBody
    public TeamWsDto editTeam(@RequestBody TeamWsDto request) {
        List<Team> teamList = new ArrayList<>();
        for (TeamDto teamDto : request.getTeamDtoList()) {
            Team team = teamRepository.findByRecordId(teamDto.getRecordId());
            teamList.add(team);
        }
        request.setTeamDtoList(modelMapper.map(teamList, List.class));
        return request;
    }

    @PostMapping(value = "/edit", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public TeamWsDto handleEdit(@RequestParam("identifier") String identifier, @RequestParam("logo") MultipartFile logo,
                                @RequestParam("name") String name, @RequestParam("shortName") String shortName) {
        TeamWsDto request = new TeamWsDto();
        TeamDto teamDto = new TeamDto();
        teamDto.setLogo(logo);
        teamDto.setShortName(shortName);
        teamDto.setIdentifier(identifier);
        teamDto.setName(name);
        request.setTeamDtoList(List.of(teamDto));
        return teamService.handleEdit(request);
    }

    @PostMapping("/delete")
    @ResponseBody
    public TeamWsDto deleteTeam(@RequestBody TeamWsDto teamWsDto) {
        for (TeamDto teamDto : teamWsDto.getTeamDtoList()) {
            teamRepository.deleteByRecordId(teamDto.getRecordId());
        }
        teamWsDto.setMessage("Data deleted successfully");
        teamWsDto.setBaseUrl(ADMIN_TEAM);
        return teamWsDto;
    }
}
