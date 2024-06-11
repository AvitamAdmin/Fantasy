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

    @GetMapping("/players")
    @ResponseBody
    public List<Player> getPlayersByMatchId(@RequestParam("matchId")String matchId){
              List<Player> allPlayers=new ArrayList<>();
              Optional<Matches> matchesOptional=matchesRepository.findById(matchId);
              if(matchesOptional.isPresent()){
                  Matches matches=matchesOptional.get();

                  String team1Id=matches.getTeam1Id();
                  String team2Id=matches.getTeam2Id();

                  List<Player>team1Players=playerRepository.findByTeamId(team1Id);
                  List<Player>team2Players=playerRepository.findByTeamId(team2Id);

                  allPlayers.addAll(team1Players);
                  allPlayers.addAll(team2Players);
              }
        return allPlayers;
    }
    @GetMapping("/edit")
    public String editUserTeams(@RequestParam("id") String id, Model model) {

        Optional<UserTeams> userTeamsOptional = userTeamsRepository.findById(id);
        if (userTeamsOptional.isPresent()) {

            UserTeams userTeams = userTeamsOptional.get();

            modelMapper.getConfiguration().setAmbiguityIgnored(true);
            UserTeamsForm userTeamsForm=modelMapper.map(userTeams,UserTeamsForm.class);
            userTeamsForm.setId(String.valueOf(userTeams.getId()));

            model.addAttribute("editForm", userTeamsForm);
        }
        model.addAttribute("matches",matchesRepository.findAll());

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
             userTeamsRepository.deleteById(new ObjectId(id));
        }
        return "redirect:/admin/userTeams";
    }
}
