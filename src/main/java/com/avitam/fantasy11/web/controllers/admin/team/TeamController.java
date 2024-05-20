package com.avitam.fantasy11.web.controllers.admin.team;

import com.avitam.fantasy11.core.service.CoreService;
import com.avitam.fantasy11.form.TeamForm;
import com.avitam.fantasy11.model.Team;
import com.avitam.fantasy11.model.TeamRepository;
import org.bson.types.Binary;
import org.bson.types.ObjectId;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.io.IOException;
import java.util.*;
import java.util.List;

@Controller
@RequestMapping("/admin/team")
public class TeamController {
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private CoreService coreService;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public String getAll(Model model){
        List<Team> teams = teamRepository.findAll();
        List<Team> datas=new ArrayList<>();
        for(Team team:teams){
            byte[] image= team.getLogo().getData();
            team.setPic(Base64.getEncoder().encodeToString(image));
            datas.add(team);
        }
        model.addAttribute("models", datas);
        return "team/teams";
    }
    @GetMapping("/edit")
    public String editTeam (@RequestParam("id")ObjectId id, Model model){

        Optional<Team> teamOptional = teamRepository.findById(id);
        if (teamOptional.isPresent()) {
            Team team = teamOptional.get();
            TeamForm teamForm = modelMapper.map(team, TeamForm.class);
            model.addAttribute("editForm", teamForm);
        }
        return "team/edit";
    }

    @PostMapping("/edit")
    public String handleEdit(@ModelAttribute("editForm")TeamForm teamForm,String id, Model model, BindingResult result) throws IOException {

        if (result.hasErrors()) {
            model.addAttribute("message", result);
            model.addAttribute("editForm", teamForm);
            return "team/edit";
        }

        byte[] fig= teamForm.getLogo().getBytes();
        Binary binary=new Binary(fig);

        teamForm.setLastModified(new Date());
        if (teamForm.getId() == null) {
            teamForm.setCreationTime(new Date());
            teamForm.setCreator(coreService.getCurrentUser().getEmail());
        }

        Team team = modelMapper.map(teamForm, Team.class);
        Optional<Team> teamOptional=teamRepository.findById(new ObjectId(id));
        if(teamOptional.isPresent()){
            team.setId(teamOptional.get().getId());
        }
         team.setLogo(binary);
        teamRepository.save(team);
        model.addAttribute("editForm", teamForm);

        return "redirect:/admin/team";
    }

    @GetMapping("/add")
    public String addTeam(Model model) {
        TeamForm form = new TeamForm();
        form.setCreationTime(new Date());
        form.setLastModified(new Date());
        form.setStatus(true);
        form.setCreator(coreService.getCurrentUser().getEmail());
        model.addAttribute("editForm", form);
        return "team/edit";
    }
    @GetMapping("/delete")
    public String deleteTeam(@RequestParam("id") String ids, Model model) {
        for (String id : ids.split(",")) {
            teamRepository.deleteById(new ObjectId(id));
        }
        return "redirect:/admin/team";
    }
}
