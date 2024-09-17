package com.avitam.fantasy11.web.controllers.admin.team;

import com.avitam.fantasy11.api.dto.AddressDto;
import com.avitam.fantasy11.api.dto.TeamDto;
import com.avitam.fantasy11.api.service.TeamService;
import com.avitam.fantasy11.core.service.CoreService;
import com.avitam.fantasy11.form.TeamForm;
import com.avitam.fantasy11.model.Address;
import com.avitam.fantasy11.model.Team;
import com.avitam.fantasy11.model.TeamRepository;
import com.avitam.fantasy11.web.controllers.BaseController;
import org.apache.commons.math3.analysis.solvers.BaseSecantSolver;
import org.bson.types.Binary;
import org.bson.types.ObjectId;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.io.IOException;
import java.util.*;
import java.util.List;

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
    @GetMapping("/edit")
    @ResponseBody
    public TeamDto editTeam(@RequestBody AddressDto request) {
        TeamDto teamDto = new TeamDto();
        Team team = teamRepository.findByRecordId(request.getRecordId());
        teamDto.setTeam(team);
        teamDto.setBaseUrl(ADMIN_TEAM);
        return teamDto;
    }

    @PostMapping("/edit")
    @ResponseBody
    public TeamDto handleEdit(@RequestBody TeamDto request) {
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
