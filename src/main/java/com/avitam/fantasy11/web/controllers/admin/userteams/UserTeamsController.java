package com.avitam.fantasy11.web.controllers.admin.userteams;

import com.avitam.fantasy11.api.dto.UserTeamsDto;
import com.avitam.fantasy11.api.service.UserTeamsService;
import com.avitam.fantasy11.core.service.CoreService;
import com.avitam.fantasy11.form.UserTeamsForm;
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

import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin/userTeams")
public class UserTeamsController extends BaseController {

    @Autowired
    private MatchesRepository matchesRepository;
    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private UserTeamsRepository userTeamsRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CoreService coreService;
    @Autowired
    private UserTeamsService userTeamsService;

    public static final String ADMIN_USERTEAMS = "/admin/userTeams";

    @PostMapping
    @ResponseBody
    public UserTeamsDto getAllUserTeams(UserTeamsDto userTeamsDto) {

        Pageable pageable = getPageable(userTeamsDto.getPage(), userTeamsDto.getSizePerPage(), userTeamsDto.getSortDirection(), userTeamsDto.getSortField());
        UserTeams userTeams = userTeamsDto.getUserTeams();
        Page<UserTeams> page = isSearchActive(userTeams)!=null? userTeamsRepository.findAll(Example.of(userTeams), pageable) : userTeamsRepository.findAll(pageable);
        userTeamsDto.setUserTeamsList(page.getContent());
        userTeamsDto.setTotalPages(page.getTotalPages());
        userTeamsDto.setTotalRecords(page.getTotalElements());
        userTeamsDto.setBaseUrl(ADMIN_USERTEAMS);
        return userTeamsDto;
    }

    @GetMapping("/get")
    @ResponseBody
    public UserTeamsDto getActiveUserTeams(){
        UserTeamsDto userTeamsDto = new UserTeamsDto();
        userTeamsDto.setUserTeamsList(userTeamsRepository.findByStatusOrderByIdentifier(true));
        userTeamsDto.setBaseUrl(ADMIN_USERTEAMS);

        return userTeamsDto;
    }

    @GetMapping("/migrate")
    public void migrate()
    {
        List<UserTeams> userTeams=userTeamsRepository.findAll();
        for(UserTeams userTeams1:userTeams)
        {
            userTeams1.setRecordId(String.valueOf(userTeams1.getId().getTimestamp()));
            userTeamsRepository.save(userTeams1);
        }
    }

    @GetMapping("/edit")
    @ResponseBody
    public UserTeamsDto editUserTeams(@RequestBody UserTeamsDto request) {

        UserTeamsDto userTeamsDto = new UserTeamsDto();
        UserTeams userTeams = userTeamsRepository.findByRecordId(request.getRecordId());
        userTeamsDto.setUserTeams(userTeams);
        userTeamsDto.setBaseUrl(ADMIN_USERTEAMS);
        return userTeamsDto;
    }

    @PostMapping("/edit")
    @ResponseBody
    public UserTeamsDto handleEdit(@RequestBody UserTeamsDto request) {
        return userTeamsService.handleEdit(request);
    }

    @GetMapping("/add")
    @ResponseBody
    public String addUserTeams() {
        UserTeamsDto userTeamsDto = new UserTeamsDto();
        userTeamsDto.setUserTeamsList(userTeamsRepository.findByStatusOrderByIdentifier(true));
        userTeamsDto.setBaseUrl(ADMIN_USERTEAMS);

        return "userTeams/edit";
    }

    @PostMapping("/delete")
    @ResponseBody
    public UserTeamsDto delete(@RequestBody UserTeamsDto userTeamsDto) {
        for (String id : userTeamsDto.getRecordId().split(",")) {
            userTeamsRepository.deleteByRecordId(id);
        }
        userTeamsDto.setMessage("Data deleted successfully");
        userTeamsDto.setBaseUrl(ADMIN_USERTEAMS);
        return userTeamsDto;
    }
}

/* @ResponseBody
    @GetMapping("/getPlayersByMatchId/{matchId}")
    public Map<Integer,List<String>> getMatchDetails(@PathVariable String matchId, Model model){
        Map<Integer, List<String>> userTeamPlayers = new HashMap<>();

        List<Player> playersList1 = new ArrayList<>();
        List<Player> playersList2 = new ArrayList<>();

        List<String> playersId = new ArrayList<>();
        List<String> playersName = new ArrayList<>();

        Optional<Matches> matchesOptional = matchesRepository.findById(matchId);
        if(matchesOptional.isPresent()){
            String team1 = matchesOptional.get().getTeam1Id();
            playersList1 = playerRepository.findByTeamId(team1);

            String team2 = matchesOptional.get().getTeam2Id();
            playersList2 = playerRepository.findByTeamId(team2);

        }

        for(Player player: playersList1){
            playersId.add(String.valueOf(player.getId()));
            playersName.add(player.getName());
        }

        for(Player player: playersList2){
            playersId.add(String.valueOf(player.getId()));
            playersName.add(player.getName());
        }

        model.addAttribute("playerIds", playersId);
        model.addAttribute("playerNames", playersName);

        userTeamPlayers.put(1, playersId);
        userTeamPlayers.put(2, playersName);

        return userTeamPlayers;
    }*/