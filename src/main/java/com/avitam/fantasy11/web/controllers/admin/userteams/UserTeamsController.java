package com.avitam.fantasy11.web.controllers.admin.userteams;

import com.avitam.fantasy11.core.service.CoreService;
import com.avitam.fantasy11.form.ContestForm;
import com.avitam.fantasy11.form.UserTeamsForm;
import com.avitam.fantasy11.model.Contest;
import com.avitam.fantasy11.model.UserTeams;
import com.avitam.fantasy11.model.UserTeamsRepository;
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
@RequestMapping("/admin/userTeams")
public class UserTeamsController {
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

    @GetMapping("/edit")
    public String editContest(@RequestParam("id") ObjectId id, Model model) {

        Optional<UserTeams> userTeamsOptional = userTeamsRepository.findById(id);
        if (userTeamsOptional.isPresent()) {
            UserTeams userTeams = userTeamsOptional.get();
            UserTeamsForm userTeamsForm = modelMapper.map(userTeams, UserTeamsForm.class);
            model.addAttribute("editForm", userTeamsForm);
        }
        return "userTeams/edit";
    }

    @PostMapping("/edit")
    public String handleEdit(@ModelAttribute("editForm")  UserTeamsForm userTeamsForm, Model model, BindingResult result) {
        if (result.hasErrors()) {
            model.addAttribute("message", result);
            return "userTeams/edit";
        }
        userTeamsForm.setLastModified(new Date());

        if (userTeamsForm.getId() == null) {
            userTeamsForm.setCreationTime(new Date());
            userTeamsForm.setCreator(coreService.getCurrentUser().getEmail());
        }

        UserTeams userTeams = modelMapper.map(userTeamsForm, UserTeams.class);

        userTeamsRepository.save(userTeams);
        model.addAttribute("editForm", userTeamsForm);
        return "redirect:/admin/userTeams";
    }

    @GetMapping("/add")
    public String addUserTeams(Model model) {
        UserTeamsForm form = new UserTeamsForm();
        form.setCreationTime(new Date());
        form.setLastModified(new Date());
        form.setStatus(true);
        form.setCreator(coreService.getCurrentUser().getEmail());
        model.addAttribute("editForm", form);
        return "userTeams/edit";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("id") String id, Model model) {
        // for (String id : ids.split(",")) {
        //      contestRepository.deleteById(id);
        //}
        userTeamsRepository.deleteById(new ObjectId(id));
        return "redirect:/admin/userTeams";
    }
}
