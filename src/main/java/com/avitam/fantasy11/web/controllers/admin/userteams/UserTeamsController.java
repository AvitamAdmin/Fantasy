package com.avitam.fantasy11.web.controllers.admin.userTeams;

import com.avitam.fantasy11.core.service.CoreService;
import com.avitam.fantasy11.form.UserTeamsForm;
import com.avitam.fantasy11.model.*;
import org.bson.types.ObjectId;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin/userTeams")
public class UserTeamsController {

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


    @GetMapping
    public String getAllUserTeams(Model model) {
        model.addAttribute("models", userTeamsRepository.findAll().stream().filter(userTeam -> userTeam.getId() != null).collect(Collectors.toList()));
        return "userTeams/userTeam";
    }

    @ResponseBody
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
    public String editUserTeams(@RequestParam("id") String id, Model model) {

        Optional<UserTeams> userTeamsOptional = userTeamsRepository.findByRecordId(id);
        if (userTeamsOptional.isPresent()) {

            UserTeams userTeams = userTeamsOptional.get();

            modelMapper.getConfiguration().setAmbiguityIgnored(true);
            UserTeamsForm userTeamsForm=modelMapper.map(userTeams,UserTeamsForm.class);
            userTeamsForm.setId(String.valueOf(userTeams.getId()));

            model.addAttribute("editForm", userTeamsForm);
            model.addAttribute("matches",matchesRepository.findAll());
        }

        return "userTeams/edit";
    }

    @PostMapping("/edit")
    public String handleEdit(@ModelAttribute("editForm")  UserTeamsForm userTeamsForm, Model model, BindingResult result) {
        if (result.hasErrors()) {
            model.addAttribute("message", result);
            model.addAttribute("editForm",userTeamsForm);
            return "userTeams/edit";
        }
        userTeamsForm.setLastModified(new Date());

        if (userTeamsForm.getId() == null) {
            userTeamsForm.setCreationTime(new Date());
            userTeamsForm.setCreator(coreService.getCurrentUser().getEmail());
        }

        UserTeams userTeams = modelMapper.map(userTeamsForm, UserTeams.class);

        Optional<UserTeams> userTeamsOptional=userTeamsRepository.findById(userTeamsForm.getId());
        if(userTeamsOptional.isPresent()) {
            userTeams.setId(userTeamsOptional.get().getId());
        }

        Optional<Matches> matchesOptional=matchesRepository.findById(String.valueOf(userTeamsForm.getMatchId()));
        if(matchesOptional.isPresent()){
            userTeams.setMatchId(String.valueOf(matchesOptional.get().getId()));
        }

        userTeamsRepository.save(userTeams);
        if(userTeams.getRecordId()==null)
        {
            userTeams.setRecordId(String.valueOf(userTeams.getId().getTimestamp()));
        }
        userTeamsRepository.save(userTeams);
        model.addAttribute("editForm", userTeamsForm);
        return "redirect:/admin/userTeams";
    }

    @GetMapping("/add")
    public String addUserTeams( Model model) {
        UserTeamsForm form = new UserTeamsForm();
        form.setCreationTime(new Date());
        form.setLastModified(new Date());
        form.setStatus(true);
        form.setCreator(coreService.getCurrentUser().getEmail());
        model.addAttribute("editForm", form);
        model.addAttribute("matches",matchesRepository.findAll());

        return "userTeams/edit";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("id") String ids, Model model) {
        for (String id : ids.split(",")) {
            userTeamsRepository.deleteByRecordId(id);
        }
        return "redirect:/admin/userTeams";
    }
}
