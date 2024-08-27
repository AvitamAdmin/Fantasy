package com.avitam.fantasy11.web.controllers.admin.settings;

import com.avitam.fantasy11.core.service.CoreService;
import com.avitam.fantasy11.form.LanguageForm;
import com.avitam.fantasy11.form.SportsApiForm;
import com.avitam.fantasy11.model.Language;
import com.avitam.fantasy11.model.LanguageRepository;
import com.avitam.fantasy11.model.SportsApi;
import com.avitam.fantasy11.model.SportsApiRepository;
import org.bson.types.ObjectId;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Optional;

@Controller
@RequestMapping("/admin/sportsApi")
public class SportAPIController {

    @Autowired
    private SportsApiRepository sportsApiRepository;
    @Autowired
    private CoreService coreService;
    @Autowired
    private ModelMapper modelMapper;


    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("models", sportsApiRepository.findAll());
        return "sportsApi/apis";
    }

    @GetMapping("/edit")
    public String edit(@RequestParam("id") String id, Model model) {

        Optional<SportsApi> sportsApiOptional = sportsApiRepository.findById(id);
        if (sportsApiOptional.isPresent()) {
            SportsApi sportsApi =sportsApiOptional.get();

            SportsApiForm sportsApiForm = modelMapper.map(sportsApi, SportsApiForm.class);
            model.addAttribute("editForm", sportsApiForm);
        }
        return "sportsApi/edit";
    }

    @PostMapping("/edit")
    public String handleEdit(@ModelAttribute("editForm") SportsApiForm sportsApiForm, Model model, BindingResult result) {
        if (result.hasErrors()) {
            model.addAttribute("message", result);
            return "sportsApi/edit";
        }
        sportsApiForm.setLastModified(new Date());
        if (sportsApiForm.getId() == null) {
            sportsApiForm.setCreationTime(new Date());
            sportsApiForm.setCreator(coreService.getCurrentUser().getEmail());
        }
        SportsApi sportsApi = modelMapper.map(sportsApiForm, SportsApi.class);

        Optional<SportsApi> sportsApiOptional = sportsApiRepository.findById(sportsApiForm.getId());
        if(sportsApiOptional.isPresent()){
            sportsApi.setId(sportsApiOptional.get().getId());
        }

        sportsApiRepository.save(sportsApi);
        model.addAttribute("editForm", sportsApiForm);
        return "redirect:/settings/sportsApi";
    }

    @GetMapping("/add")
    public String add(Model model) {
        SportsApiForm form = new SportsApiForm();
        form.setCreationTime(new Date());
        form.setLastModified(new Date());
        form.setStatus(true);
        form.setCreator(coreService.getCurrentUser().getEmail());
        model.addAttribute("editForm", form);
        return "sportsApi/edit";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("id") String ids, Model model) {
        for (String id : ids.split(",")) {

            sportsApiRepository.deleteById(new ObjectId(id));
        }
        return "redirect:/settings/sportsApi";
    }
}
