package com.avitam.fantasy11.web.controllers.admin.matches;
import com.avitam.fantasy11.core.service.CoreService;
import com.avitam.fantasy11.form.MatchesForm;
import com.avitam.fantasy11.model.*;
import org.apache.commons.lang3.ObjectUtils;
import org.bson.types.ObjectId;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin/matches")
public class MatchesController {

    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private SportTypeRepository sportTypeRepository;
    @Autowired
    private ContestRepository contestRepository;
    @Autowired
    private TournamentRepository tournamentRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CoreService coreService;
    @Autowired
    private MatchesRepository matchesRepository;

    @GetMapping
    public String getAllModels(Model model) {
        model.addAttribute("models", matchesRepository.findAll().stream().filter(matches -> matches.getId() != null).collect(Collectors.toList()));
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
        //  model.addAttribute("nodes", nodeRepository.findAll().stream().filter(node -> node.getParentId() == null).collect(Collectors.toList()));
        model.addAttribute("teams", teamRepository.findAll().stream().filter(team -> team.getId() != null).collect(Collectors.toList()));
        model.addAttribute("sportType", sportTypeRepository.findAll().stream().filter(sportTypes -> sportTypes.getId() != null).collect(Collectors.toList()));
        model.addAttribute("tournament", tournamentRepository.findAll().stream().filter(tournament -> tournament.getId() != null).collect(Collectors.toList()));
        model.addAttribute("contest", contestRepository.findAll().stream().filter(contest -> contest.getId() != null).collect(Collectors.toList()));

        return "matches/edit";
    }

    @PostMapping("/edit")
    public String handleEdit(@ModelAttribute("editForm") MatchesForm matchesForm, Model model, BindingResult result) {
        // new KycFormValidator().validate(kycForm, result);
        if (result.hasErrors()) {
            model.addAttribute("message", result);
            return "matches/edit";
        }
        matchesForm.setLastModified(new Date());
        if (matchesForm.getId() == null) {
            matchesForm.setCreationTime(new Date());
            matchesForm.setCreator(coreService.getCurrentUser().getEmail());
        }
        Matches match = modelMapper.map(matchesForm, Matches.class);
        if (ObjectUtils.isNotEmpty(matchesForm.getId())) {
            //node.setParentNode(nodeRepository.getById(interfaceForm.getId()));
            // node.setParentNodeId(kycRepository.getById(kycForm.getId()).getParentNodeId());
            match.setCreator(coreService.getCurrentUser().getEmail());

        }
//        if (kyc.getDisplayPriority() == null) {
//            kyc.setDisplayPriority(1000);
//        }
        matchesRepository.save(match);
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
        model.addAttribute("editForm", form);
        //   model.addAttribute("nodes", kycRepository.findAll().stream().filter(kyc -> kyc.getUserId()  == null).collect(Collectors.toList()));
        return "matches/edit";
    }

    @GetMapping("/delete")
    public String deleteMatches(@RequestParam("id") ObjectId id, Model model) {
        //  for (String id : ids.split(",")) {
        //     nodeRepository.deleteById(Integer.valueOf(id));
        //  }
        matchesRepository.deleteById(id);
        return "redirect:/admin/matches";
    }
}


