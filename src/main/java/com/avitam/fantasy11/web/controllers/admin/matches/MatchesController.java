package com.avitam.fantasy11.web.controllers.admin.matches;

import com.avitam.fantasy11.core.service.CoreService;
import com.avitam.fantasy11.form.MatchesForm;
import com.avitam.fantasy11.model.*;
import org.bson.types.ObjectId;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Optional;

@Controller
@RequestMapping("/admin/matches")
public class MatchesController {
    @Autowired
    MatchesRepository matchesRepository;
    @Autowired
    TeamRepository teamRepository;
    @Autowired
    TournamentRepository tournamentRepository;

    @Autowired
    SportTypeRepository sportTypeRepository;

    @Autowired
    ContestRepository contestRepository;
    @Autowired
    MatchTypeRepository matchTypeRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CoreService coreService;

    @GetMapping
    public String getAllModels(Model model) {
        //model.addAttribute("models", addressRepository.findAll().stream().filter(address -> address.getUserId() != null).collect(Collectors.toList()));
        model.addAttribute("models", matchesRepository.findAll());
        return "matches/matchess";
    }

    @GetMapping("/edit")
    public String editMatches(@RequestParam("id") ObjectId id, Model model) {
        MatchesForm matchesForm = null;
        Optional<Matches> matchesOptional = matchesRepository.findById(id);
        if (matchesOptional.isPresent()) {
            Matches matches = matchesOptional.get();
            matchesForm = modelMapper.map(matches, MatchesForm.class);
            model.addAttribute("editForm", matchesForm);
        }
        model.addAttribute("teams", teamRepository.findAll());
        model.addAttribute("tournaments", tournamentRepository.findAll());
        model.addAttribute("sportTypes", sportTypeRepository.findAll());
        model.addAttribute("contests", contestRepository.findAll());
        model.addAttribute("matchTypes", matchTypeRepository.findAll());
        //model.addAttribute("nodes", addressRepository.findAll().stream().filter(node -> node.getParentNode() == null).collect(Collectors.toList()));
        return "matches/edit";
    }

    @PostMapping("/edit")
    public String handleEdit(@ModelAttribute("editForm") MatchesForm matchesForm, Model model, BindingResult result) {
        if (result.hasErrors()) {
            model.addAttribute("message", result);
            return "matches/edit";
        }
        matchesForm.setLastModified(new Date());
        if (matchesForm.getId() == null) {
            matchesForm.setCreationTime(new Date());
            matchesForm.setCreator(coreService.getCurrentUser().getEmail());
        }
        Matches matches = modelMapper.map(matchesForm, Matches.class);

        Optional<Matches> matchesOptional = matchesRepository.findById(matchesForm.getId());
        if(matchesOptional.isPresent()){
            matches.setId(matchesOptional.get().getId());
        }

        Optional<Team> teamOptional1 = teamRepository.findById(matchesForm.getTeam1Id());
        if(teamOptional1.isPresent()){
            matches.setTeam1Id(String.valueOf(teamOptional1.get().getId()));
        }

        Optional<Team> teamOptional2 = teamRepository.findById(matchesForm.getTeam2Id());
        if(teamOptional2.isPresent()){
            matches.setTeam2Id(String.valueOf(teamOptional2.get().getId()));
        }

        Optional<Tournament> tournamentOptional = tournamentRepository.findById(matchesForm.getTournamentId());
        if(tournamentOptional.isPresent()){
            matches.setTournamentId(String.valueOf(tournamentOptional.get().getId()));
        }

        Optional<SportType> sportTypeOptional = sportTypeRepository.findById(matchesForm.getSportTypeId());
        if(sportTypeOptional.isPresent()){
            matches.setSportTypeId(String.valueOf(sportTypeOptional.get().getId()));
        }

        Optional<Contest> contestOptional = contestRepository.findById(matchesForm.getContestId());
        if(contestOptional.isPresent()){
            matches.setContestId(String.valueOf(contestOptional.get().getId()));
        }

        Optional<MatchType> matchTypeOptional = matchTypeRepository.findById(matchesForm.getMatchTypeId());
        if(matchTypeOptional.isPresent()){
            matches.setMatchTypeId(String.valueOf(matchTypeOptional.get().getId()));
        }

        matchesRepository.save(matches);
        model.addAttribute("editForm", matchesForm);
        return "redirect:/admin/matches";
    }

    @GetMapping("/add")
    public String addMatches(Model model) {
        MatchesForm form = new MatchesForm();
        form.setCreationTime(new Date());
        form.setLastModified(new Date());
        form.setStatus(true);
        form.setCreator(coreService.getCurrentUser().getEmail());
        form.setMatchStatus(true);
        model.addAttribute("editForm", form);
        model.addAttribute("teams", teamRepository.findAll());
        model.addAttribute("tournaments", tournamentRepository.findAll());
        model.addAttribute("sportTypes", sportTypeRepository.findAll());
        model.addAttribute("contests", contestRepository.findAll());
        model.addAttribute("matchTypes", matchTypeRepository.findAll());
        return "matches/edit";
    }

    @GetMapping("/delete")
    public String deleteMatches(@RequestParam("id") String ids, Model model) {
        for (String id : ids.split(",")) {

                matchesRepository.deleteById(new ObjectId(id));
        }
        return "redirect:/admin/matches";
    }
}
