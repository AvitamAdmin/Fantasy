package com.avitam.fantasy11.web.controllers.admin.environments;

import com.avitam.fantasy11.core.model.Environment;
import com.avitam.fantasy11.core.model.EnvironmentRepository;
import com.avitam.fantasy11.core.service.CoreService;
import com.avitam.fantasy11.form.EnvironmentForm;
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
@RequestMapping("/admin/environment")
public class EnvironmentController {

    @Autowired
    private EnvironmentRepository environmentRepository;

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CoreService coreService;


    @GetMapping
    public String getAll(Model model) {
        List<Environment> environment = environmentRepository.findAll();
        model.addAttribute("models", environment);
        return "environment/environments";
    }

    @GetMapping("/edit")
    public String edit(@RequestParam("id") Long id, Model model) {
        EnvironmentForm environmentForm = null;
        Optional<Environment> environmentOptional = environmentRepository.findById(id);
        if (environmentOptional.isPresent()) {
            Environment environment = environmentOptional.get();
            environmentForm = modelMapper.map(environment, EnvironmentForm.class);
            model.addAttribute("editForm", environmentForm);
        }

        return "environment/edit";
    }

    @PostMapping("/edit")
    public String handleEdit(@ModelAttribute("editForm") EnvironmentForm environmentForm, Model model, BindingResult result) {

        if (result.hasErrors()) {
            model.addAttribute("message", result);
            model.addAttribute("editForm", environmentForm);
            return "environment/edit";
        }
        environmentForm.setLastModified(new Date());
        if (environmentForm.getId() == null) {
            environmentForm.setCreationTime(new Date());
            environmentForm.setCreator(coreService.getCurrentUser().getUsername());
        }
        Environment environment = modelMapper.map(environmentForm, Environment.class);
        environmentRepository.save(environment);
        model.addAttribute("editForm", environmentForm);

        return "redirect:/admin/environment";
    }

    @GetMapping("/add")
    public String add(Model model) {
        EnvironmentForm form = new EnvironmentForm();
        form.setCreationTime(new Date());
        form.setLastModified(new Date());
        form.setStatus(true);
        form.setCreator(coreService.getCurrentUser().getUsername());

        model.addAttribute("editForm", form);
        return "environment/edit";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("id") String ids, Model model) {
        for (String id : ids.split(",")) {
            environmentRepository.deleteById(Long.valueOf(id));
        }
        return "redirect:/admin/environment";
    }
}
