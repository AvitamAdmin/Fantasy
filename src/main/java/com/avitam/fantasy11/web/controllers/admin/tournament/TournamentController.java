package com.avitam.fantasy11.web.controllers.admin.tournament;

import com.avitam.fantasy11.core.service.CoreService;
import com.avitam.fantasy11.form.TeamForm;
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
    public String getAll(Model model){
        List<Tournament> tournaments = tournamentRepository.findAll();
        model.addAttribute("models", tournaments);
        return "tournament/tournaments";
    }
    @GetMapping("/edit")
    public String editTournament (@RequestParam("id") ObjectId id, Model model){
        List<SportType> sportTypes=sportTypeRepository.findAll().stream().filter(sport->sport.getId()!=null).collect(Collectors.toList());

        Optional<Tournament> tournamentOptional = tournamentRepository.findById(id);
        if (tournamentOptional.isPresent()) {
            Tournament tournament= tournamentOptional.get();
            TournamentForm tournamentForm = modelMapper.map(tournament, TournamentForm.class);
            model.addAttribute("editForm", tournamentForm);
            model.addAttribute("sportTypes",sportTypes);
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
        tournamentForm.setDateAndTime(new Date());

        Tournament tournament = modelMapper.map(tournamentForm, Tournament.class);

        Optional<Tournament> tournamentOptional=tournamentRepository.findById(new ObjectId(tournamentForm.getId()));
        if(tournamentOptional.isPresent()){
            tournament.setId(tournamentOptional.get().getId());
        }

        tournamentRepository.save(tournament);
        model.addAttribute("editForm", tournamentForm);

        return "redirect:/admin/tournament";
    }

    @GetMapping("/add")
    public String addTournament(Model model) {
        TeamForm form = new TeamForm();
        form.setCreationTime(new Date());
        form.setLastModified(new Date());
        form.setStatus(true);
        form.setCreator(coreService.getCurrentUser().getEmail());
        model.addAttribute("editForm", form);
        return "tournament/edit";
    }
    @GetMapping("/delete")
    public String deleteTournament(@RequestParam("id") String ids, Model model) {
        for (String id : ids.split(",")) {
            tournamentRepository.deleteById(new ObjectId(id));
        }
        return "redirect:/admin/tournament";
    }
}