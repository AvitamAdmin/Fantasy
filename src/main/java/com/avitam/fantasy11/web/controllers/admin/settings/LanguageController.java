package com.avitam.fantasy11.web.controllers.admin.settings;

import com.avitam.fantasy11.core.service.CoreService;
import com.avitam.fantasy11.form.LanguageForm;
import com.avitam.fantasy11.form.NotificationForm;
import com.avitam.fantasy11.model.Language;
import com.avitam.fantasy11.model.LanguageRepository;
import com.avitam.fantasy11.model.Notification;
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
@RequestMapping("/admin/language")
public class LanguageController {

    @Autowired
    private LanguageRepository languageRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CoreService coreService;

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("models", languageRepository.findAll());
        return "language/languages";
    }

    @GetMapping("/migrate")
    public void migrate()
    {
        List<Language> languageList=languageRepository.findAll();
        for(Language language:languageList)
        {
            language.setRecordId(String.valueOf(language.getId().getTimestamp()));
            languageRepository.save(language);
        }
    }

    @GetMapping("/edit")
    public String edit(@RequestParam("id") String id, Model model) {

        Optional<Language> languageOptional = languageRepository.findByRecordId(id);
        if (languageOptional.isPresent()) {
            Language language =languageOptional.get();

            LanguageForm languageForm = modelMapper.map(language, LanguageForm.class);
            model.addAttribute("editForm", languageForm);
        }
        return "language/edit";
    }

    @PostMapping("/edit")
    public String handleEdit(@ModelAttribute("editForm") LanguageForm languageForm, Model model, BindingResult result) {
        if (result.hasErrors()) {
            model.addAttribute("message", result);
            return "language/edit";
        }
        languageForm.setLastModified(new Date());
        if (languageForm.getId() == null) {
            languageForm.setCreationTime(new Date());
            languageForm.setCreator(coreService.getCurrentUser().getEmail());
        }
        Language language = modelMapper.map(languageForm, Language.class);

        Optional<Language> languageOptional = languageRepository.findById(languageForm.getId());
        if(languageOptional.isPresent()){
            language.setId(languageOptional.get().getId());
        }

        languageRepository.save(language);
        if(language.getRecordId()==null)
        {
            language.setRecordId(String.valueOf(language.getId().getTimestamp()));
        }
        languageRepository.save(language);
        model.addAttribute("editForm", languageForm);
        return "redirect:/settings/language";
    }

    @GetMapping("/add")
    public String add(Model model) {
        LanguageForm form = new LanguageForm();
        form.setCreationTime(new Date());
        form.setLastModified(new Date());
        form.setStatus(true);
        form.setCreator(coreService.getCurrentUser().getEmail());
        model.addAttribute("editForm", form);
        return "language/edit";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("id") String ids, Model model) {
        for (String id : ids.split(",")) {

            languageRepository.deleteByRecordId(id);
        }
        return "redirect:/settings/language";
    }
}
