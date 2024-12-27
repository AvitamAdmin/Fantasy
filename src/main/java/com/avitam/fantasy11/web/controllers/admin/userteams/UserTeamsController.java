package com.avitam.fantasy11.web.controllers.admin.userteams;

import com.avitam.fantasy11.api.dto.UserTeamWsDto;
import com.avitam.fantasy11.api.dto.UserTeamsDto;
import com.avitam.fantasy11.api.service.UserTeamsService;
import com.avitam.fantasy11.model.UserTeams;
import com.avitam.fantasy11.repository.MatchesRepository;
import com.avitam.fantasy11.repository.PlayerRepository;
import com.avitam.fantasy11.repository.UserTeamsRepository;
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
    private UserTeamsService userTeamsService;

    public static final String ADMIN_USERTEAMS = "/admin/userTeams";

    @PostMapping
    @ResponseBody
    public UserTeamWsDto getAllUserTeams(@RequestBody UserTeamWsDto userTeamWsDto) {

        Pageable pageable = getPageable(userTeamWsDto.getPage(), userTeamWsDto.getSizePerPage(), userTeamWsDto.getSortDirection(), userTeamWsDto.getSortField());
        UserTeamsDto userTeamsDto = CollectionUtils.isNotEmpty(userTeamWsDto.getUserTeamsDtoList()) ? userTeamWsDto.getUserTeamsDtoList().get(0) : new UserTeamsDto();
        UserTeams userTeams = modelMapper.map(userTeamsDto, UserTeams.class);
        Page<UserTeams> page = isSearchActive(userTeams) != null ? userTeamsRepository.findAll(Example.of(userTeams), pageable) : userTeamsRepository.findAll(pageable);
        userTeamWsDto.setUserTeamsDtoList(modelMapper.map(page.getContent(), List.class));
        userTeamWsDto.setTotalPages(page.getTotalPages());
        userTeamWsDto.setTotalRecords(page.getTotalElements());
        userTeamWsDto.setBaseUrl(ADMIN_USERTEAMS);
        return userTeamWsDto;
    }

    @GetMapping("/get")
    @ResponseBody
    public UserTeamWsDto getActiveUserTeams() {
        UserTeamWsDto userTeamWsDto = new UserTeamWsDto();
        userTeamWsDto.setUserTeamsDtoList(modelMapper.map(userTeamsRepository.findByStatusOrderByIdentifier(true), List.class));
        userTeamWsDto.setBaseUrl(ADMIN_USERTEAMS);
        return userTeamWsDto;
    }


    @PostMapping("/getedit")
    @ResponseBody
    public UserTeamWsDto editUserTeams(@RequestBody UserTeamWsDto request) {
        List<UserTeamsDto> userTeamsDtos = request.getUserTeamsDtoList();
        UserTeams userTeams = userTeamsRepository.findByRecordId(request.getUserTeamsDtoList().get(0).getRecordId());
        request.setUserTeamsDtoList(List.of(modelMapper.map(userTeams, UserTeamsDto.class)));
        request.setBaseUrl(ADMIN_USERTEAMS);
        return request;
    }

    @PostMapping("/edit")
    @ResponseBody
    public UserTeamWsDto handleEdit(@RequestBody UserTeamWsDto request) {

        return userTeamsService.handleEdit(request);
    }

    @GetMapping("/add")
    @ResponseBody
    public UserTeamWsDto addUserTeams() {
        UserTeamWsDto userTeamWsDto = new UserTeamWsDto();
        userTeamWsDto.setUserTeamsDtoList(modelMapper.map(userTeamsRepository.findByStatusOrderByIdentifier(true), List.class));
        userTeamWsDto.setBaseUrl(ADMIN_USERTEAMS);
        return userTeamWsDto;
    }

    @PostMapping("/delete")
    @ResponseBody
    public UserTeamWsDto delete(@RequestBody UserTeamWsDto userTeamWsDto) {
        for (UserTeamsDto id : userTeamWsDto.getUserTeamsDtoList()) {
            userTeamsRepository.deleteByRecordId(id.getRecordId());
        }
        userTeamWsDto.setMessage("Data deleted successfully");
        userTeamWsDto.setBaseUrl(ADMIN_USERTEAMS);
        return userTeamWsDto;
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