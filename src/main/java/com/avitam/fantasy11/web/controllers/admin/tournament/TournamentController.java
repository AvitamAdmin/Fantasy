package com.avitam.fantasy11.web.controllers.admin.tournament;

import com.avitam.fantasy11.core.service.CoreService;
import com.avitam.fantasy11.form.TournamentForm;
import com.avitam.fantasy11.model.SportType;
import com.avitam.fantasy11.model.SportTypeRepository;
import com.avitam.fantasy11.model.Tournament;
import com.avitam.fantasy11.model.TournamentRepository;
import org.bson.types.ObjectId;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin/tournament")
public class TournamentController {

    @Autowired
    private SportTypeRepository sportTypeRepository;
    @Autowired
    private TournamentRepository tournamentRepository;
    @Autowired
    private CoreService coreService;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public String getAllTournament(Model model){
        List<Tournament> tournaments = tournamentRepository.findAll();
        model.addAttribute("models", tournaments);
        return "tournament/tournaments";
    }
    @GetMapping("/edit")
    public String editTournament (@RequestParam("id") String id, Model model){

        Optional<Tournament> tournamentOptional = tournamentRepository.findById(id);
        if (tournamentOptional.isPresent()) {
            Tournament tournament = tournamentOptional.get();
            modelMapper.getConfiguration().setAmbiguityIgnored(true);
            TournamentForm tournamentForm = modelMapper.map(tournament, TournamentForm.class);
            tournamentForm.setId(String.valueOf(tournament.getId()));
            tournamentForm.setId(String.valueOf(tournament.getId()));
            model.addAttribute("editForm", tournamentForm);
            model.addAttribute("sportTypes",sportTypeRepository.findAll());
        }
        return "tournament/edit";
    }

    @PostMapping("/edit")
    public String handleEdit(@ModelAttribute("editForm")TournamentForm tournamentForm, Model model, BindingResult result) throws IOException {

        if (result.hasErrors()) {
            model.addAttribute("message", result);
            model.addAttribute("editForm", tournamentForm);
            return "tournament/edit";
        }

        tournamentForm.setLastModified(new Date());
        if (tournamentForm.getId() == null) {
            tournamentForm.setCreationTime(new Date());
            tournamentForm.setCreator(coreService.getCurrentUser().getEmail());
        }

        Tournament tournament = modelMapper.map(tournamentForm, Tournament.class);

        Optional<Tournament> tournamentOptional=tournamentRepository.findById(tournamentForm.getId());
        if(tournamentOptional.isPresent()) {
            tournament.setId(tournamentOptional.get().getId());
        }
        Optional<SportType> sportTypeOptional=sportTypeRepository.findById(tournamentForm.getSportId());
        if(sportTypeOptional.isPresent()) {
            tournament.setSportId(String.valueOf(sportTypeOptional.get().getId()));
        }

        tournamentRepository.save(tournament);
        model.addAttribute("editForm", tournamentForm);

        return "redirect:/matches/tournament";
    }

    @GetMapping("/add")
    public String addTournament(Model model) {
        TournamentForm form = new TournamentForm();
        form.setCreationTime(new Date());
        form.setLastModified(new Date());
        form.setStatus(true);
        form.setCreator(coreService.getCurrentUser().getEmail());
        model.addAttribute("editForm", form);
        model.addAttribute("sportTypes",sportTypeRepository.findAll());

        return "tournament/edit";
    }
    @GetMapping("/delete")
    public String delete (@RequestParam("id") String ids, Model model) {
        for (String id : ids.split(",")) {
            tournamentRepository.deleteById(new ObjectId(id));
        }
        return "redirect:/matches/tournament";
    }
}
