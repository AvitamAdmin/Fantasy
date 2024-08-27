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

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/matches")
public class MatchesController {
    @Autowired
    private MatchesRepository matchesRepository;
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private TournamentRepository tournamentRepository;
    @Autowired
    private SportTypeRepository sportTypeRepository;
    @Autowired
    private MainContestRepository mainContestRepository;
    @Autowired
    private MatchTypeRepository matchTypeRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CoreService coreService;

    @GetMapping
    public String getAllModel(Model model) {
        List<Matches> matches= matchesRepository.findAll();
        for(Matches match:matches){
        LocalDateTime currentTime=LocalDateTime.now();
        LocalDateTime startTime=LocalDateTime.parse(match.getStartDateAndTime());
        LocalDateTime endTime=LocalDateTime.parse(match.getEndDateAndTime());

        if(currentTime.isAfter(endTime))
        {
            match.setEvent("Closed");
        } else if (currentTime.isAfter(startTime)&&currentTime.isBefore(endTime)) {
            match.setEvent("Live");
        }
        else if(currentTime.isBefore(startTime))
        {
            match.setEvent("Upcoming");
        }}
        model.addAttribute("models", matches);
        return "matches/matchess";
    }


    @GetMapping("/edit")
    public String editMatches(@RequestParam("id") String id, Model model) {

        Optional<Matches> matchesOptional = matchesRepository.findByRecordId(id);
        if (matchesOptional.isPresent()) {
            Matches matches = matchesOptional.get();

            modelMapper.getConfiguration().setAmbiguityIgnored(true);
            MatchesForm  matchesForm = modelMapper.map(matches, MatchesForm.class);
            matchesForm.setId(String.valueOf(matches.getId()));

            model.addAttribute("editForm", matchesForm);
        }
        model.addAttribute("teams", teamRepository.findAll());
        model.addAttribute("tournaments", tournamentRepository.findAll());
        model.addAttribute("sportTypes", sportTypeRepository.findAll());
        model.addAttribute("mainContests", mainContestRepository.findAll());
        model.addAttribute("matchTypes", matchTypeRepository.findAll());
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

        LocalDateTime currentTime=LocalDateTime.now();
        LocalDateTime startTime=LocalDateTime.parse(matches.getStartDateAndTime());
        LocalDateTime endTime=LocalDateTime.parse(matches.getEndDateAndTime());

        if(currentTime.isAfter(endTime))
        {
            matches.setEvent("Closed");
        } else if (currentTime.isAfter(startTime)&&currentTime.isBefore(endTime)) {
            matches.setEvent("Live");
        }
        else if(currentTime.isBefore(startTime))
        {
            matches.setEvent("Upcoming");
        }

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
        if(tournamentOptional.isPresent()) matches.setTournamentId(String.valueOf(tournamentOptional.get().getName()));

        Optional<SportType> sportTypeOptional = sportTypeRepository.findById(matchesForm.getSportTypeId());
        if(sportTypeOptional.isPresent()){
            matches.setSportTypeId(String.valueOf(sportTypeOptional.get().getId()));
        }

        Optional<MainContest> contestOptional = mainContestRepository.findById(matchesForm.getParentMainContestId());
        if(contestOptional.isPresent()){
            matches.setParentMainContestId(String.valueOf(contestOptional.get().getId()));
        }

        Optional<MatchType> matchTypeOptional = matchTypeRepository.findById(matchesForm.getMatchTypeId());
        if(matchTypeOptional.isPresent()){
            matches.setMatchTypeId(String.valueOf(matchTypeOptional.get().getId()));
        }

        matchesRepository.save(matches);
        if(matches.getRecordId()==null)
        {
            matches.setRecordId(String.valueOf(matches.getId().getTimestamp()));
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
        Optional<MainContest> contestOptional = mainContestRepository.findByMainContestId(null);
        if(contestOptional.isPresent()){
            form.setParentMainContestId(String.valueOf(contestOptional.get().getId()));
        }

        form.setMatchStatus(true);
        model.addAttribute("editForm", form);
        model.addAttribute("teams", teamRepository.findAll());
        model.addAttribute("tournaments", tournamentRepository.findAll());
        model.addAttribute("sportTypes", sportTypeRepository.findAll());
        model.addAttribute("mainContests", mainContestRepository.findAll());
        model.addAttribute("matchTypes", matchTypeRepository.findAll());
        return "matches/edit";
    }

    @GetMapping("/delete")
    public String deleteMatches(@RequestParam("id") String ids, Model model) {
        for (String id : ids.split(",")) {
            matchesRepository.deleteByRecordId(id);
        }
        return "redirect:/admin/matches";
    }
}