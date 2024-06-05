package com.avitam.fantasy11.web.controllers.admin.userteams;

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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin/userTeams")
public class UserTeamsController {

    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private MatchesRepository matchesRepository;
    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private UserTeamsRepository userTeamsRepository;
    @Autowired
    private CoreService coreService;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public String getAllUserTeams(Model model) {
        model.addAttribute("models", userTeamsRepository.findAll().stream().filter(userTeam -> userTeam.getId() != null).collect(Collectors.toList()));
        return "userTeams/userTeam";
    }

    @GetMapping
    public String getPlayers(Model model){
        return "matchId";
    }

    @RequestMapping(value="hiddenMatchId",method = RequestMethod.GET)
    @ResponseBody
    public List<Player>playerList(@RequestParam("hiddenMatchId")String hiddenMatchId,Model model){

        Optional<Matches> match=matchesRepository.findById(hiddenMatchId);

           String team1Id = match.get().getTeam1Id();
           String team2Id = match.get().getTeam2Id();

           List<Player> players1 = playerRepository.findByTeamId(team1Id);
           List<Player> players2 = playerRepository.findByTeamId(team2Id);

           List<Player> allPlayers = new ArrayList<>();
           allPlayers.addAll(players1);
           allPlayers.addAll(players2);

           return allPlayers;

      }

    @GetMapping("/edit")
    public String editUserTeams(@RequestParam("id") String id, Model model) {

        Optional<UserTeams> userTeamsOptional = userTeamsRepository.findById(id);
        if (userTeamsOptional.isPresent()) {
            UserTeams userTeams = userTeamsOptional.get();
            UserTeamsForm userTeamsForm = modelMapper.map(userTeams, UserTeamsForm.class);
            model.addAttribute("editForm", userTeamsForm);
        }
        model.addAttribute("matches",matchesRepository.findAll());
        model.addAttribute("players",playerRepository.findAll());

        return "userTeams/edit";
    }


    @PostMapping("/edit")
    public String handleEdit(@ModelAttribute("editForm")  UserTeamsForm userTeamsForm, Model model, BindingResult result) {
        if (result.hasErrors()) {
            model.addAttribute("message", result);
            model.addAttribute("editForm",userTeamsForm);
            return "userTeams/edit";
        }
        userTeamsForm.setUserId(coreService.getCurrentUser().getMobileNumber());
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
        form.setUserId(coreService.getCurrentUser().getMobileNumber());
        model.addAttribute("editForm", form);
        model.addAttribute("matches",matchesRepository.findAll());

        return "userTeams/edit";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("id") String ids, Model model) {
        for (String id : ids.split(",")) {
             userTeamsRepository.deleteById(new ObjectId(id));
        }
        return "redirect:/admin/userTeams";
    }
}
