package com.avitam.fantasy11.web.controllers.admin.settings;

import com.avitam.fantasy11.core.service.CoreService;
import com.avitam.fantasy11.form.AddressForm;
import com.avitam.fantasy11.form.GeneralForm;
import com.avitam.fantasy11.model.Address;
import com.avitam.fantasy11.model.General;
import com.avitam.fantasy11.repository.GeneralRepository;
import com.avitam.fantasy11.validation.AddressFormValidator;
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
@RequestMapping("/admin/general")
public class GeneralController {
    @Autowired
    private GeneralRepository generalRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CoreService coreService;

    @GetMapping
    public String add(Model model) {
        GeneralForm form = new GeneralForm();
        form.setCreationTime(new Date());
        form.setLastModified(new Date());
        form.setStatus(true);
        form.setCreator(coreService.getCurrentUser().getEmail());
        model.addAttribute("editForm", form);
        return "general/edit";
    }
    @PostMapping("/edit")
    public String handleEdit(@ModelAttribute("editForm") GeneralForm generalForm, Model model, BindingResult result) {
        if (result.hasErrors()) {
            model.addAttribute("message", result);
            model.addAttribute("editForm",generalForm);
            return "general/edit";
        }
        generalForm.setLastModified(new Date());
        if (generalForm.getId() == null) {
            generalForm.setCreationTime(new Date());
            generalForm.setCreator(coreService.getCurrentUser().getEmail());
        }
        General general = modelMapper.map(generalForm, General.class);

        Optional<General> generalOptional = generalRepository.findById(generalForm.getId());
        if(generalOptional.isPresent()){
            general.setId(generalOptional.get().getId());
        }

        generalRepository.save(general);
        if(general.getRecordId()==null)
        {
            general.setRecordId(String.valueOf(general.getId().getTimestamp()));
        }
        generalRepository.save(general);
        model.addAttribute("editForm", generalForm);
        return "redirect:/admin/general";
    }

}
