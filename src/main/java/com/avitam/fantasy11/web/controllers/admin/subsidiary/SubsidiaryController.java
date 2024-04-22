package com.avitam.fantasy11.web.controllers.admin.subsidiary;

import com.avitam.fantasy11.core.model.Subsidiary;
import com.avitam.fantasy11.core.model.SubsidiaryRepository;
import com.avitam.fantasy11.core.service.CoreService;
import com.avitam.fantasy11.form.SubsidiaryForm;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Optional;

@Controller
@RequestMapping("/admin/subsidiary")
public class SubsidiaryController {

    @Autowired
    private SubsidiaryRepository subsidiaryRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CoreService coreService;

    @GetMapping
    public String getAllSubsidiaries(org.springframework.ui.Model model) {
        model.addAttribute("models", subsidiaryRepository.findAll());
        return "subsidiary/subsidiaries";
    }

    @GetMapping("/edit")
    public String editSubidiary(@RequestParam("id") Long id, Model model) {
        SubsidiaryForm subsidiaryForm = null;
        Optional<Subsidiary> subsidiaryOptional = subsidiaryRepository.findById(id);
        if (subsidiaryOptional.isPresent()) {
            Subsidiary subsidiary = subsidiaryOptional.get();
            subsidiaryForm = modelMapper.map(subsidiary, SubsidiaryForm.class);
            model.addAttribute("editForm", subsidiaryForm);
        }

        return "subsidiary/edit";
    }

    @PostMapping("/edit")
    public String handleEdit(@ModelAttribute("editForm") SubsidiaryForm subsidiaryForm, Model model, BindingResult result) {

        if (result.hasErrors()) {
            model.addAttribute("message", result);
            model.addAttribute("editForm", subsidiaryForm);
            return "subsidiary/edit";
        }
        subsidiaryForm.setLastModified(new Date());
        if (subsidiaryForm.getId() == null) {
            subsidiaryForm.setCreationTime(new Date());
            subsidiaryForm.setCreator(coreService.getCurrentUser().getUsername());
        }
        Subsidiary subsidiary = modelMapper.map(subsidiaryForm, Subsidiary.class);
        subsidiaryRepository.save(subsidiary);
        model.addAttribute("editForm", subsidiaryForm);

        return "redirect:/admin/subsidiary";
    }

    @GetMapping("/add")
    public String add(Model model) {
        SubsidiaryForm form = new SubsidiaryForm();
        form.setCreationTime(new Date());
        form.setLastModified(new Date());
        form.setStatus(true);
        form.setCreator(coreService.getCurrentUser().getUsername());

        model.addAttribute("editForm", form);
        return "subsidiary/edit";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("id") String ids, Model model) {
        for (String id : ids.split(",")) {
            subsidiaryRepository.deleteById(Long.valueOf(id));
        }
        return "redirect:/admin/subsidiary";
    }
}
