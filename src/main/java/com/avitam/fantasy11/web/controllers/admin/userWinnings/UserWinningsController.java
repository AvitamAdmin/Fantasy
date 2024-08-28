package com.avitam.fantasy11.web.controllers.admin.userWinnings;

import com.avitam.fantasy11.core.service.CoreService;
import com.avitam.fantasy11.form.ContestJoinedForm;
import com.avitam.fantasy11.form.UserWinningsForm;
import com.avitam.fantasy11.model.*;
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


@Controller
@RequestMapping("/admin/userWinnings")
public class UserWinningsController {

    @Autowired
    private UserWinningsRepository userWinningsRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserTeamsRepository userTeamsRepository;
    @Autowired
    private MatchesRepository matchesRepository;
    @Autowired
    private CoreService coreService;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public String getAll(Model model){
        model.addAttribute("models",userWinningsRepository.findAll());
        return "userWinnings/userWins";
    }

    @GetMapping("/edit")
    public String editUserWinnings(@RequestParam("id") String id,Model model){

        Optional<UserWinnings> userWinningsOptional = userWinningsRepository.findByRecordId(id);
        if (userWinningsOptional.isPresent()) {
            UserWinnings userWinnings = userWinningsOptional.get();

            modelMapper.getConfiguration().setAmbiguityIgnored(true);
            UserWinningsForm userWinningsForm = modelMapper.map(userWinnings, UserWinningsForm.class);
            userWinningsForm.setId(String.valueOf(userWinnings.getId()));

            model.addAttribute("editForm", userWinningsForm);
        }
        model.addAttribute("userTeams",userTeamsRepository.findAll());
        model.addAttribute("matches",matchesRepository.findAll());

        return "userWinnings/edit";
    }

    @PostMapping("/edit")
    public String handleEdit(@ModelAttribute("editForm") UserWinningsForm userWinningsForm, Model model, BindingResult result) {
        if (result.hasErrors()) {
            model.addAttribute("message", result);
            model.addAttribute("editForm",userWinningsForm);
            return "userWinnings/edit";
        }

        userWinningsForm.setLastModified(new Date());

        if (userWinningsForm.getId() == null) {
            userWinningsForm.setCreationTime(new Date());
            userWinningsForm.setCreator(coreService.getCurrentUser().getEmail());
        }

        UserWinnings userWinnings = modelMapper.map(userWinningsForm, UserWinnings.class);

        Optional<UserWinnings>userWinningsOptional=userWinningsRepository.findById(userWinningsForm.getId());
        if(userWinningsOptional.isPresent()) {
            userWinnings.setId(userWinningsOptional.get().getId());
        }
        
        Optional<Matches> matchesOptional=matchesRepository.findById(userWinningsForm.getMatchId());
        if(matchesOptional.isPresent()) {
            userWinnings.setMatchId(String.valueOf(matchesOptional.get().getId()));
        }

        Optional<UserTeams> userTeamOptional=userTeamsRepository.findById(userWinningsForm.getUserTeamId());
        if(userTeamOptional.isPresent()) {
            userWinnings.setUserTeamId(String.valueOf(userTeamOptional.get().getId()));
        }

        userWinningsRepository.save(userWinnings);
        if(userWinnings.getRecordId()==null)
        {
            userWinnings.setRecordId(String.valueOf(userWinnings.getId().getTimestamp()));
        }
        userWinningsRepository.save(userWinnings);
        model.addAttribute("editForm", userWinningsForm);
        return "redirect:/admin/userWinnings";
    }

    @GetMapping("/add")
    public String addUserWinnings(Model model) {
        UserWinningsForm form = new UserWinningsForm();
        form.setCreationTime(new Date());
        form.setLastModified(new Date());
        form.setStatus(true);
        form.setCreator(coreService.getCurrentUser().getEmail());
        model.addAttribute("editForm", form);
        model.addAttribute("userTeams", userTeamsRepository.findAll());
        model.addAttribute("matches",matchesRepository.findAll());
        return "userWinnings/edit";
    }

    @GetMapping("/delete")
    public String deleteUserWinnings(@RequestParam("id") String ids, Model model) {
        for (String id : ids.split(",")) {
            userWinningsRepository.deleteByRecordId(id);
        }
        return "redirect:/admin/userWinnings";
    }
   }
