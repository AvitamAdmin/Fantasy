package com.avitam.fantasy11.web.controllers.admin.contestjoined;

import com.avitam.fantasy11.core.service.CoreService;
import com.avitam.fantasy11.form.ContestJoinedForm;
import com.avitam.fantasy11.model.*;
import org.bson.types.ObjectId;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin/contestJoined")
public class ContestJoinedController {

    @Autowired
    private ContestRepository contestRepository;
    @Autowired
    private ContestJoinedRepository contestJoinedRepository;
    @Autowired
    private UserRepository  userRepository;
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private MatchesRepository matchesRepository;
    @Autowired
    private CoreService coreService;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public String getAllContestJoined(Model model) {
        List<ContestJoined> contestJoineds=contestJoinedRepository.findAll();
        model.addAttribute("models", contestJoineds);
        return "contestJoined/contestjoin";
    }

    @GetMapping("/edit")
    public String editContestJoined(@RequestParam("id") String id, Model model) {

        Optional<ContestJoined> contestJoinedOptional = contestJoinedRepository.findById(id);

        if (contestJoinedOptional.isPresent()) {
            ContestJoined contestJoined = contestJoinedOptional.get();

            modelMapper.getConfiguration().setAmbiguityIgnored(true);
           ContestJoinedForm contestJoinedForm=modelMapper.map(contestJoined,ContestJoinedForm.class);

           contestJoinedForm.setId(String.valueOf(contestJoined.getId()));


            model.addAttribute("editForm", contestJoinedForm);
            model.addAttribute("teams",teamRepository.findAll());
            model.addAttribute("matches",matchesRepository.findAll());
        }
        return "contestJoined/edit";
    }

    @PostMapping("/edit")
    public String handleEdit(@ModelAttribute("editForm") ContestJoinedForm contestJoinedForm, Model model, BindingResult result) {
        if (result.hasErrors()) {
            model.addAttribute("message", result);
            model.addAttribute("editForm",contestJoinedForm);
            return "contestJoined/edit";
        }
        contestJoinedForm.setLastModified(new Date());

        if (contestJoinedForm.getId() == null) {
            contestJoinedForm.setCreationTime(new Date());
            contestJoinedForm.setCreator(coreService.getCurrentUser().getEmail());
        }

        ContestJoined contestJoined = modelMapper.map(contestJoinedForm, ContestJoined.class);

        Optional<ContestJoined> contestJoinedOptional=contestJoinedRepository.findById(contestJoinedForm.getId());
        if(contestJoinedOptional.isPresent()) {
            contestJoined.setId(contestJoinedOptional.get().getId());
        }

        Optional<Matches> matchesOptional=matchesRepository.findById(contestJoinedForm.getMatchId());
        if(matchesOptional.isPresent()) {
            contestJoined.setMatchId(String.valueOf(matchesOptional.get().getId()));
        }

        Optional<Team> teamOptional=teamRepository.findById(contestJoinedForm.getUserTeamId());
        if(teamOptional.isPresent()) {
            contestJoined.setUserTeamId(String.valueOf(teamOptional.get().getId()));
        }

        contestJoinedRepository.save(contestJoined);
        model.addAttribute("editForm", contestJoinedForm);
        return "redirect:/admin/contestJoined";
    }

    @GetMapping("/add")
    public String addContestJoined(Model model) {
        ContestJoinedForm form = new ContestJoinedForm();
        form.setCreationTime(new Date());
        form.setLastModified(new Date());
        form.setStatus(true);
        form.setCreator(coreService.getCurrentUser().getEmail());
        form.setUserId(coreService.getCurrentUser().getMobileNumber());
        model.addAttribute("editForm", form);
        model.addAttribute("teams",teamRepository.findAll());
        model.addAttribute("matches",matchesRepository.findAll());

        return "contestJoined/edit";
    }

    @GetMapping("/delete")
    public String deleteContestJoined(@RequestParam("id") String ids, Model model) {
        for (String id : ids.split(",")) {

            contestJoinedRepository.deleteById(new ObjectId(id));
        }
        return "redirect:/admin/contestJoined";
    }
}
