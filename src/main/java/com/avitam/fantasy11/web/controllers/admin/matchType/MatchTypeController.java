package com.avitam.fantasy11.web.controllers.admin.matchType;

import com.avitam.fantasy11.core.service.CoreService;
import com.avitam.fantasy11.form.MatchTypeForm;
import com.avitam.fantasy11.form.TeamForm;
import com.avitam.fantasy11.form.TournamentForm;
import com.avitam.fantasy11.model.MatchType;
import com.avitam.fantasy11.model.MatchTypeRepository;
import com.avitam.fantasy11.model.SportType;
import com.avitam.fantasy11.model.Tournament;
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
@RequestMapping("/admin/matchType")
public class MatchTypeController {

    @Autowired
    private MatchTypeRepository matchTypeRepository;
    @Autowired
    private CoreService coreService;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public String getAll(Model model){
        List<MatchType> matchTypes = matchTypeRepository.findAll();
        model.addAttribute("models", matchTypes);
        return "matchType/matchTypes";
    }
    @GetMapping("/edit")
    public String editTournament (@RequestParam("id") String id, Model model){

        Optional<MatchType> matchTypeOptional = matchTypeRepository.findById(id);
        if (matchTypeOptional.isPresent()) {
            MatchType matchType= matchTypeOptional.get();
            MatchTypeForm matchTypeForm= modelMapper.map(matchType, MatchTypeForm.class);
            model.addAttribute("editForm", matchTypeForm);
        }
        return "matchType/edit";
    }

    @PostMapping("/edit")
    public String handleEdit(@ModelAttribute("editForm")MatchTypeForm matchTypeForm, Model model, BindingResult result) throws IOException {

        if (result.hasErrors()) {
            model.addAttribute("message", result);
            model.addAttribute("editForm", matchTypeForm);
            return "matchType/edit";
        }

            matchTypeForm.setLastModified(new Date());
        if (matchTypeForm.getId() == null) {
            matchTypeForm.setCreationTime(new Date());
            matchTypeForm.setCreator(coreService.getCurrentUser().getEmail());
        }

        MatchType matchType = modelMapper.map(matchTypeForm, MatchType.class);
        if(matchTypeForm.getId()!=null) {
            Optional<MatchType> matchTypeOptional = matchTypeRepository.findById(matchTypeForm.getId());
            if (matchTypeOptional.isPresent()) {
                matchType.setId(matchTypeOptional.get().getId());
            }
        }
        matchTypeRepository.save(matchType);
        model.addAttribute("editForm", matchTypeForm);

        return "redirect:/matches/matchType";
    }

    @GetMapping("/add")
    public String addMatchType(Model model) {
        MatchTypeForm form = new MatchTypeForm();
        form.setCreationTime(new Date());
        form.setLastModified(new Date());
        form.setStatus(true);
        form.setCreator(coreService.getCurrentUser().getEmail());
        model.addAttribute("editForm", form);
        return "matchType/edit";
    }
    @GetMapping("/delete")
    public String deleteMatchType(@RequestParam("id") String ids, Model model) {
        for (String id : ids.split(",")) {
            matchTypeRepository.deleteById(new ObjectId(id));
        }
        return "redirect:/matches/matchType";
    }
}
