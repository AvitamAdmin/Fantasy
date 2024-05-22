package com.avitam.fantasy11.web.controllers.admin.teamlineup;

import com.avitam.fantasy11.core.service.CoreService;
import com.avitam.fantasy11.form.TeamForm;
import com.avitam.fantasy11.form.TeamLineUpForm;
import com.avitam.fantasy11.model.Team;
import com.avitam.fantasy11.model.TeamLineup;
import com.avitam.fantasy11.model.TeamLineupRepository;
import com.avitam.fantasy11.model.TeamRepository;
import org.bson.types.Binary;
import org.bson.types.ObjectId;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/admin/teamLineup")
public class TeamLineupController {

    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private TeamLineupRepository teamLineupRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CoreService coreService;

    @GetMapping
    public String getAll(Model model){
        model.addAttribute("models", teamLineupRepository.findAll());
        return "teamLineup/teamLineups";
    }
    @GetMapping("/edit")
    public String editTeamLineup (@RequestParam("id") ObjectId id, Model model){

        Optional<TeamLineup> teamlineupOptional = teamLineupRepository.findById(id);
        if (teamlineupOptional.isPresent()) {
            TeamLineup teamLineUp = teamlineupOptional.get();
            TeamLineUpForm teamLineUpForm = modelMapper.map(teamLineUp, TeamLineUpForm.class);
            model.addAttribute("editForm", teamLineUpForm);
        }
        return "teamLineup/edit";
    }

    @PostMapping("/edit")
    public String handleEdit(@ModelAttribute("editForm")TeamLineUpForm teamLineUpForm, String id, Model model, BindingResult result) throws IOException {

        if (result.hasErrors()) {
            model.addAttribute("message", result);
            model.addAttribute("editForm", teamLineUpForm);
            return "teamLineup/edit";
        }

            teamLineUpForm.setLastModified(new Date());
        if (teamLineUpForm.getId() == null) {
            teamLineUpForm.setCreationTime(new Date());
            teamLineUpForm.setCreator(coreService.getCurrentUser().getEmail());
        }

        TeamLineup teamLineup = modelMapper.map(teamLineUpForm, TeamLineup.class);
        Optional<TeamLineup> teamLineupOptional=teamLineupRepository.findById(new ObjectId());
        if(teamLineupOptional.isPresent()){
            teamLineup.setId(teamLineupOptional.get().getId());
        }
        teamLineupRepository.save(teamLineup);
        model.addAttribute("editForm", teamLineUpForm);

        return "redirect:/admin/teamLineup";
    }

    @GetMapping("/add")
    public String addTeamLineup(Model model) {
        TeamLineUpForm form = new TeamLineUpForm();
        form.setCreationTime(new Date());
        form.setLastModified(new Date());
        form.setStatus(true);
        form.setCreator(coreService.getCurrentUser().getEmail());
        model.addAttribute("editForm", form);
        return "teamLineup/edit";
    }
    @GetMapping("/delete")
    public String deleteTeamLineup(@RequestParam("id") String ids, Model model) {
        for (String id : ids.split(",")) {
            teamLineupRepository.deleteById(new ObjectId(id));
        }
        return "redirect:/admin/teamLineup";
    }
}
