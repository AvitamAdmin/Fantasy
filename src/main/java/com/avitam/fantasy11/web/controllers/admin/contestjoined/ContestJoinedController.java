package com.avitam.fantasy11.web.controllers.admin.contestjoined;

import com.avitam.fantasy11.core.service.CoreService;
import com.avitam.fantasy11.form.ContestJoinedForm;
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
    public String getAll(Model model) {
        model.addAttribute("models", contestJoinedRepository.findAll().stream().filter(contest -> contest.getId() != null).collect(Collectors.toList()));
        return "contestjoined/contestjoin";
    }

    @GetMapping("/edit")
    public String editContestJoined(@RequestParam("id") ObjectId id, Model model) {

        Optional<ContestJoined> contestJoinedOptional = contestJoinedRepository.findById(id);
        if (contestJoinedOptional.isPresent()) {
            ContestJoined contestJoined = contestJoinedOptional.get();
            ContestJoinedForm contestJoinedForm = modelMapper.map(contestJoined, ContestJoinedForm.class);
            model.addAttribute("editForm", contestJoinedForm);
            model.addAttribute("teams",teamRepository.findAll().stream().filter(team -> team.getId()!=null).collect(Collectors.toList()));
        }
        return "contestjoined/edit";
    }

    @PostMapping("/edit")
    public String handleEdit(@ModelAttribute("editForm") ContestJoinedForm contestJoinedForm, Model model, BindingResult result) {
        if (result.hasErrors()) {
            model.addAttribute("message", result);
            return "contestjoined/edit";
        }
        contestJoinedForm.setLastModified(new Date());

        if (contestJoinedForm.getId() == null) {
            contestJoinedForm.setCreationTime(new Date());
            contestJoinedForm.setCreator(coreService.getCurrentUser().getEmail());
        }

        ContestJoined contestJoined = modelMapper.map(contestJoinedForm, ContestJoined.class);

        contestJoinedRepository.save(contestJoined);
        model.addAttribute("editForm", contestJoinedForm);
        return "redirect:/admin/contestjoin";
    }

    @GetMapping("/add")
    public String addInterface(Model model) {
        ContestJoinedForm form = new ContestJoinedForm();
        form.setCreationTime(new Date());
        form.setLastModified(new Date());
        form.setStatus(true);
        form.setCreator(coreService.getCurrentUser().getEmail());
        model.addAttribute("editForm", form);
        return "contestjoined/edit";
    }

    @GetMapping("/delete")
    public String deleteContestJoined(@RequestParam("id") String ids, Model model) {
        for (String id : ids.split(",")) {
            contestJoinedRepository.deleteById(new ObjectId(id));
        }
        return "redirect:/admin/contestjoin";
    }
}
