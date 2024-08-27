package com.avitam.fantasy11.web.controllers.admin.contest;

import com.avitam.fantasy11.core.service.CoreService;
import com.avitam.fantasy11.form.ContestForm;
import com.avitam.fantasy11.model.*;
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
@RequestMapping("/admin/contest")
public class ContestController {

    @Autowired
    private MainContestRepository mainContestRepository;
    @Autowired
    private ContestRepository contestRepository;
    @Autowired
    private CoreService coreService;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public String getAllContest(Model model) {
        model.addAttribute("contests", contestRepository.findAll().stream().filter(contest -> contest.getId() != null).collect(Collectors.toList()));
        return "contest/contests";
    }

    @GetMapping("/edit")
    public String editContest(@RequestParam("id") String id, Model model) {
        Optional<Contest> contestOptional = contestRepository.findByRecordId(id);
        if (contestOptional.isPresent()) {
            Contest contest = contestOptional.get();
            ContestForm contestForm = modelMapper.map(contest, ContestForm.class);
            model.addAttribute("editForm", contestForm);
            model.addAttribute("mainContests",mainContestRepository.findAll());
        }
        return "contest/edit";
    }


    @PostMapping("/edit")
    public String handleEdit(@ModelAttribute("editForm") ContestForm contestForm, Model model, BindingResult result) {
        if (result.hasErrors()) {
            model.addAttribute("message", result);
            model.addAttribute("editForm",contestForm);
            return "contest/edit";
        }
        contestForm.setLastModified(new Date());

        if (contestForm.getId() == null) {
            contestForm.setCreationTime(new Date());
            contestForm.setCreator(coreService.getCurrentUser().getEmail());
        }

        Contest contest = modelMapper.map(contestForm, Contest.class);
        Optional<Contest> contestOptional=contestRepository.findById(contestForm.getId());
        if(contestOptional.isPresent()) {
            contest.setId(contestOptional.get().getId());
        }

        Optional<MainContest> mainContestOptional=mainContestRepository.findById(contestForm.getMainContestId());
        if(mainContestOptional.isPresent()){
            contest.setMainContestId(String.valueOf(mainContestOptional.get().getId()));
        }

        contestRepository.save(contest);
        if (contest.getRecordId()==null)
        {
            contest.setRecordId(String.valueOf(contest.getId().getTimestamp()));
        }
        contestRepository.save(contest);
        model.addAttribute("editForm", contestForm);
        return "redirect:/admin/contest";
    }

    @GetMapping("/add")
    public String addContest(Model model) {
        ContestForm form = new ContestForm();
        form.setCreationTime(new Date());
        form.setLastModified(new Date());
        form.setStatus(true);
        form.setCreator(coreService.getCurrentUser().getEmail());
        model.addAttribute("editForm", form);
        model.addAttribute("mainContests",mainContestRepository.findAll());
        return "contest/edit";
    }

    @GetMapping("/delete")
    public String deleteContest(@RequestParam("id") String ids, Model model) {

        for (String id : ids.split(",")) {
           contestRepository.deleteByRecordId(id);
        }
        return "redirect:/admin/contest";
    }
}
