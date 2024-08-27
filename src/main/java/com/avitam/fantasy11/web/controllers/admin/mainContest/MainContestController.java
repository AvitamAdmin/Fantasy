package com.avitam.fantasy11.web.controllers.admin.mainContest;

import com.avitam.fantasy11.core.service.CoreService;
import com.avitam.fantasy11.form.MainContestForm;
import com.avitam.fantasy11.model.Contest;
import com.avitam.fantasy11.model.MainContest;
import com.avitam.fantasy11.model.MainContestRepository;
import org.bson.types.ObjectId;
import org.modelmapper.ModelMapper;
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
@RequestMapping("/admin/mainContest")
public class MainContestController {

    @Autowired
    private CoreService coreService;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private MainContestRepository mainContestRepository;

    @GetMapping
    public String getAllContest(Model model) {
        model.addAttribute("contests", mainContestRepository.findAll().stream().filter(contest -> contest.getId() != null).collect(Collectors.toList()));
        return "mainContest/mainContests";
    }



    @GetMapping("/edit")
    public String editContest(@RequestParam("id") String id, Model model) {
        Optional<MainContest> mainContestOptional = mainContestRepository.findByRecordId(id);
        if (mainContestOptional.isPresent()) {
            MainContest mainContest = mainContestOptional.get();
            modelMapper.getConfiguration().setAmbiguityIgnored(true);
            MainContestForm mainContestForm = modelMapper.map(mainContest, MainContestForm.class);
            mainContestForm.setId(String.valueOf(mainContest.getId()));
            model.addAttribute("editForm", mainContestForm);
        }
        return "mainContest/edit";
    }

    @PostMapping("/edit")
    public String handleEdit(@ModelAttribute("editForm") MainContestForm mainContestForm, Model model, BindingResult result) {
        if (result.hasErrors()) {
            model.addAttribute("message", result);
            model.addAttribute("editForm",mainContestForm);
            return "mainContest/edit";
        }
        mainContestForm.setLastModified(new Date());

        if (mainContestForm.getId() == null) {
            mainContestForm.setCreationTime(new Date());
            mainContestForm.setCreator(coreService.getCurrentUser().getEmail());
        }

        MainContest mainContest = modelMapper.map(mainContestForm, MainContest.class);
        Optional<MainContest> mainContestOptional1=mainContestRepository.findByMainContestId(null);
        if(mainContestOptional1.isPresent()) {
            mainContest.setMainContestId(String.valueOf(mainContestOptional1.get().getId()));
        }
        Optional<MainContest> mainContestOptional=mainContestRepository.findById(mainContestForm.getId());

        if(mainContestOptional.isPresent()) {
            mainContest.setId(mainContestOptional.get().getId());
        }

        mainContestRepository.save(mainContest);
        if(mainContest.getRecordId()==null)
        {
            mainContest.setRecordId(String.valueOf(mainContest.getId().getTimestamp()));
        }
        mainContestRepository.save(mainContest);
        model.addAttribute("editForm", mainContestForm);
        return "redirect:/admin/mainContest";
    }

    @GetMapping("/add")
    public String addContest(Model model) {
        MainContestForm form = new MainContestForm();
        form.setCreationTime(new Date());
        form.setLastModified(new Date());
        form.setStatus(true);
        form.setCreator(coreService.getCurrentUser().getEmail());
        model.addAttribute("editForm", form);
        return "mainContest/edit";
    }

    @GetMapping("/delete")
    public String deleteContest(@RequestParam("id") String ids, Model model) {

        for (String id : ids.split(",")) {
           mainContestRepository.deleteByRecordId(id);
        }
        return "redirect:/admin/mainContest";
    }
}
