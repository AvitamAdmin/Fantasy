package com.avitam.fantasy11.web.controllers.admin.teamlineup;

import com.avitam.fantasy11.api.dto.TeamLineUpDto;
import com.avitam.fantasy11.api.service.TeamLineUpService;
import com.avitam.fantasy11.core.service.CoreService;
import com.avitam.fantasy11.form.TeamLineUpForm;
import com.avitam.fantasy11.model.*;
import com.avitam.fantasy11.web.controllers.BaseController;
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

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin/teamLineup")
public class TeamLineupController extends BaseController {

    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private TeamLineupRepository teamLineupRepository;
    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private TeamLineUpService teamLineUpService;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CoreService coreService;

    public static final String ADMIN_TEAMLINEUP = "/admin/teamLineup";

    @PostMapping
    @ResponseBody
    public TeamLineUpDto getAll(@RequestBody TeamLineUpDto teamLineUpDto){
        Pageable pageable = getPageable(teamLineUpDto.getPage(), teamLineUpDto.getSizePerPage(), teamLineUpDto.getSortDirection(), teamLineUpDto.getSortField());
        TeamLineup teamLineup = teamLineUpDto.getTeamLineup();
        Page<TeamLineup> page = isSearchActive(teamLineup)!=null ? teamLineupRepository.findAll(Example.of(teamLineup), pageable) : teamLineupRepository.findAll(pageable);
        teamLineUpDto.setTeamLineupList(page.getContent());
        teamLineUpDto.setTotalPages(page.getTotalPages());
        teamLineUpDto.setTotalRecords(page.getTotalElements());
        teamLineUpDto.setBaseUrl(ADMIN_TEAMLINEUP);
        return teamLineUpDto;
    }

    @GetMapping("/get")
    @ResponseBody
    public TeamLineUpDto getActiveTeamLineUp(){
        TeamLineUpDto teamLineUpDto = new TeamLineUpDto();
        teamLineUpDto.setTeamLineupList(teamLineupRepository.findByStatusOrderByIdentifier(true));
        teamLineUpDto.setBaseUrl(ADMIN_TEAMLINEUP);
        return teamLineUpDto;
    }

    @GetMapping("/edit")
    @ResponseBody
    public TeamLineUpDto editTeamLineup (@RequestBody TeamLineUpDto request){

        TeamLineUpDto teamLineUpDto = new TeamLineUpDto();
        TeamLineup teamLineup = teamLineupRepository.findByRecordId(request.getRecordId());
        teamLineUpDto.setTeamLineup(teamLineup);
        teamLineUpDto.setBaseUrl(ADMIN_TEAMLINEUP);

            //model.addAttribute("editForm", teamLineUpForm);
            //model.addAttribute("teams",teamRepository.findAll().stream().filter(team -> team.getId()!=null).collect(Collectors.toList()));
            //model.addAttribute("players",playerRepository.findAll().stream().filter(player ->player.getId()!=null).collect(Collectors.toList()));

        return teamLineUpDto;
    }

    @PostMapping("/edit")
    @ResponseBody
    public TeamLineUpDto handleEdit(@RequestBody TeamLineUpDto request) {
        //model.addAttribute("editForm", teamLineUpForm);

        return teamLineUpService.handleEdit(request);
    }

    @GetMapping("/add")
    @ResponseBody
    public TeamLineUpDto addTeamLineup(@RequestBody TeamLineUpDto request) {

        TeamLineUpDto teamLineUpDto = new TeamLineUpDto();
        teamLineUpDto.setTeamLineupList(teamLineupRepository.findByStatusOrderByIdentifier(true));
        teamLineUpDto.setBaseUrl(ADMIN_TEAMLINEUP);
        //model.addAttribute("editForm", form);
        //model.addAttribute("teams",teamRepository.findAll().stream().filter(team -> team.getId()!=null).collect(Collectors.toList()));
        //model.addAttribute("players",playerRepository.findAll().stream().filter(player ->player.getId()!=null).collect(Collectors.toList()));

        return teamLineUpDto;
    }
    @PostMapping("/delete")
    @ResponseBody
    public TeamLineUpDto deleteTeamLineup(@RequestBody TeamLineUpDto teamLineUpDto) {
        for (String id : teamLineUpDto.getRecordId().split(",")) {
            teamLineupRepository.deleteByRecordId(id);
        }
        teamLineUpDto.setMessage("Data deleted successfully");
        teamLineUpDto.setBaseUrl(ADMIN_TEAMLINEUP);
        return teamLineUpDto;
    }
}
