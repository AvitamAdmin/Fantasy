package com.avitam.fantasy11.web.controllers.admin.team;

import com.avitam.fantasy11.api.dto.AddressDto;
import com.avitam.fantasy11.api.dto.TeamDto;
import com.avitam.fantasy11.api.service.TeamService;
import com.avitam.fantasy11.model.Team;
import com.avitam.fantasy11.repository.TeamRepository;
import com.avitam.fantasy11.web.controllers.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/team")
public class TeamController extends BaseController {
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private TeamService teamService;
    public static final String ADMIN_TEAM = "/admin/team";

    @PostMapping
    @ResponseBody
    public TeamDto getAllTeams(@RequestBody TeamDto teamDto){

        Pageable pageable=getPageable(teamDto.getPage(),teamDto.getSizePerPage(),teamDto.getSortDirection(),teamDto.getSortField());
        Team team=teamDto.getTeam();
        Page<Team> page=isSearchActive(team)!=null ? teamRepository.findAll(Example.of(team),pageable) : teamRepository.findAll(pageable);
        teamDto.setTeamList(page.getContent());
        teamDto.setTotalPages(page.getTotalPages());
        teamDto.setTotalRecords(page.getTotalElements());
        teamDto.setBaseUrl(ADMIN_TEAM);
        return teamDto;
    }

    @GetMapping("/get")
    @ResponseBody
    public TeamDto getActiveTeamList() {
        TeamDto teamDto = new TeamDto();
        teamDto.setTeamList(teamRepository.findByStatusOrderByIdentifier(true));
        teamDto.setBaseUrl(ADMIN_TEAM);
        return teamDto;
    }
    @PostMapping("/getedit")
    @ResponseBody
    public TeamDto editTeam(@RequestBody AddressDto request) {
        TeamDto teamDto = new TeamDto();
        Team team = teamRepository.findByRecordId(request.getRecordId());
        teamDto.setTeam(team);
        teamDto.setBaseUrl(ADMIN_TEAM);
        return teamDto;
    }

    @PostMapping(value="/edit", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public TeamDto handleEdit(@ModelAttribute TeamDto request) {

        return teamService.handleEdit(request);
    }

    @GetMapping("/add")
    @ResponseBody
    public TeamDto addTeam(Model model) {
        TeamDto teamDto = new TeamDto();
        teamDto.setTeamList(teamRepository.findByStatusOrderByIdentifier(true));
        teamDto.setBaseUrl(ADMIN_TEAM);
        return teamDto;
    }
    @PostMapping("/delete")
    @ResponseBody
    public TeamDto deleteTeam(@RequestBody TeamDto teamDto) {
        for (String id : teamDto.getRecordId().split(",")) {
            teamRepository.deleteByRecordId(id);
        }
        teamDto.setMessage("Data deleted successfully");
        teamDto.setBaseUrl(ADMIN_TEAM);
        return teamDto;
    }
}
