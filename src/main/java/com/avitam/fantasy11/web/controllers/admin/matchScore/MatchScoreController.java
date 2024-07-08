package com.avitam.fantasy11.web.controllers.admin.matchScore;

import com.avitam.fantasy11.core.service.CoreService;
import com.avitam.fantasy11.form.MatchScoreForm;
import com.avitam.fantasy11.form.TournamentForm;
import com.avitam.fantasy11.model.*;
import org.bson.types.ObjectId;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin/matchScore")
public class MatchScoreController {
    @Autowired
    private MatchesRepository matchesRepository;
    @Autowired
    private MatchScoreRepository matchScoreRepository;
    @Autowired
    private CoreService coreService;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public String getAll(Model model){
        List<MatchScore> matchScores = matchScoreRepository.findAll();
        model.addAttribute("models", matchScores);
        return "matchScore/matchScores";
    }
    @GetMapping("/edit")
    public String editMatchScore (@RequestParam("id") ObjectId id, Model model){

        Optional<MatchScore> matchScoreOptional = matchScoreRepository.findById(id);
        if (matchScoreOptional.isPresent()) {
            MatchScore matchScore = matchScoreOptional.get();
            MatchScoreForm matchScoreForm = modelMapper.map(matchScore, MatchScoreForm.class);
            matchScoreForm.setId(String.valueOf(matchScore.getId()));
            model.addAttribute("editForm", matchScoreForm);
            model.addAttribute("matches",matchesRepository.findAll().stream().filter(team -> team.getId()!=null).collect(Collectors.toList()));
        }
        return "matchScore/edit";
    }

    @PostMapping("/edit")
    public String handleEdit(@ModelAttribute("editForm")MatchScoreForm matchScoreForm, Model model, BindingResult result) throws IOException {

        if (result.hasErrors()) {
            model.addAttribute("message", result);
            model.addAttribute("editForm", matchScoreForm);
            return "matchScore/edit";
        }

        matchScoreForm.setLastModified(new Date());
        if (matchScoreForm.getId() == null) {
            matchScoreForm.setCreationTime(new Date());
            matchScoreForm.setCreator(coreService.getCurrentUser().getEmail());
        }

        MatchScore matchScore = modelMapper.map(matchScoreForm, MatchScore.class);

        Optional<MatchScore> matchScoreOptional=matchScoreRepository.findById(matchScoreForm.getId());
        if(matchScoreOptional.isPresent()) {
            matchScore.setId(matchScoreOptional.get().getId());
        }
        Optional<Matches> matchesOptional=matchesRepository.findById(matchScoreForm.getMatchId());
        if(matchesOptional.isPresent()) {
            matchScore.setMatchId(String.valueOf(matchesOptional.get().getId()));
        }

        matchScoreRepository.save(matchScore);
        model.addAttribute("editForm", matchScoreForm);

        return "redirect:/admin/matchScore";
    }

    @GetMapping("/add")
    public String addTournament(Model model) {
        MatchScoreForm form = new MatchScoreForm();
        form.setCreationTime(new Date());
        form.setLastModified(new Date());
        form.setStatus(true);
        form.setCreator(coreService.getCurrentUser().getEmail());
        model.addAttribute("editForm", form);
        model.addAttribute("matches",matchesRepository.findAll().stream().filter(sportType -> sportType.getId()!=null).collect(Collectors.toList()));

        return "matchScore/edit";
    }
    @GetMapping("/delete")
    public String deleteTeam(@RequestParam("id") String ids, Model model) {
        for (String id : ids.split(",")) {
            matchScoreRepository.deleteById(new ObjectId(id));
        }
        return "redirect:/admin/matchScore";
    }
}
