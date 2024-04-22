package com.avitam.fantasy11.web.controllers.admin.persona;

import com.avitam.fantasy11.core.model.ParamInput;
import com.avitam.fantasy11.core.model.Persona;
import com.avitam.fantasy11.core.model.PersonaRepository;
import com.avitam.fantasy11.core.service.CoreService;
import com.avitam.fantasy11.form.PersonaForm;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin/persona")
public class PersonaController {

    @Autowired
    private PersonaRepository personaRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CoreService coreService;


    @GetMapping
    public String getAll(Model model) {
        List<Persona> personas = personaRepository.findAll();
        model.addAttribute("models", personas);
        return "persona/personas";
    }

    @GetMapping("/edit")
    public String edit(@RequestParam("id") Long id, Model model) {
        model.addAttribute("existingParamsCount", 0);
        PersonaForm personaForm = null;
        Optional<Persona> personaOptional = personaRepository.findById(id);
        if (personaOptional.isPresent()) {
            Persona persona = personaOptional.get();
            personaForm = modelMapper.map(persona, PersonaForm.class);
            model.addAttribute("editForm", personaForm);
            if (CollectionUtils.isNotEmpty(persona.getParamInput())) {
                model.addAttribute("existingParamsCount", persona.getParamInput().size());
            }
        }

        return "persona/edit";
    }

    @PostMapping("/edit")
    public String handleEdit(@ModelAttribute("editForm") PersonaForm personaForm, Model model, BindingResult result) {
        model.addAttribute("existingParamsCount", 0);
        if (result.hasErrors()) {
            model.addAttribute("message", result);

            model.addAttribute("editForm", personaForm);
            return "persona/edit";
        }
        personaForm.setLastModified(new Date());
        if (personaForm.getId() == null) {
            personaForm.setCreationTime(new Date());
            personaForm.setCreator(coreService.getCurrentUser().getUsername());
        }
        Persona persona = modelMapper.map(personaForm, Persona.class);
        if (CollectionUtils.isNotEmpty(persona.getParamInput())) {
            List<ParamInput> params = persona.getParamInput().stream().filter(paramInput -> StringUtils.isNotEmpty(paramInput.getParamKey())).collect(Collectors.toList());
            params.sort(Comparator.comparing(inputParam -> inputParam.getParamKey()));
            persona.setParamInput(params);
        }
        personaRepository.save(persona);

        model.addAttribute("editForm", personaForm);

        return "redirect:/admin/persona";
    }

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("existingParamsCount", 0);
        PersonaForm form = new PersonaForm();
        form.setCreationTime(new Date());
        form.setLastModified(new Date());
        form.setStatus(true);
        form.setCreator(coreService.getCurrentUser().getUsername());

        model.addAttribute("editForm", form);
        return "persona/edit";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("id") String ids, Model model) {
        for (String id : ids.split(",")) {
            personaRepository.deleteById(Long.valueOf(id));
        }
        return "redirect:/admin/persona";
    }
}


