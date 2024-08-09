package com.avitam.fantasy11.web.controllers.admin.settings;

import com.avitam.fantasy11.core.service.CoreService;
import com.avitam.fantasy11.form.ExtensionForm;
import com.avitam.fantasy11.form.PlayerForm;
import com.avitam.fantasy11.model.*;
import org.bson.types.Binary;
import org.bson.types.ObjectId;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/settings/extension")
public class ExtensionController {

    @Autowired
    private ExtensionRepository extensionRepository;
    @Autowired
    private CoreService coreService;
    @Autowired
    private ModelMapper modelMapper;
    @GetMapping
    public String getAll(Model model){
        List<Extension> datas=new ArrayList<>();
        List<Extension> extensions = extensionRepository.findAll();
        for(Extension extension:extensions){
            if(extension.getId()!=null) {
                byte[] image = extension.getImage().getData();
                extension.setPic(Base64.getEncoder().encodeToString(image));
                datas.add(extension);
            }
        }
        model.addAttribute("models", datas);
        return "extension/extensions";
    }
    @GetMapping("/edit")
    public String editPlayer(@RequestParam("id")String id, Model model){

        Optional<Extension> extensionOptional = extensionRepository.findById(id);
        if (extensionOptional.isPresent()) {
            Extension extension = extensionOptional.get();

            modelMapper.getConfiguration().setAmbiguityIgnored(true);
            ExtensionForm extensionForm = modelMapper.map(extension, ExtensionForm.class);
            extensionForm.setId(String.valueOf(extension.getId()));

            byte[] image = extension.getImage().getData();
            extension.setPic(Base64.getEncoder().encodeToString(image));
            extensionForm.setPic(extension.getPic());

            model.addAttribute("editForm", extensionForm);
        }
        return "extension/edit";
    }

    @PostMapping("/edit")
    public String handleEdit(@ModelAttribute("editForm")ExtensionForm extensionForm,String id, Model model, BindingResult result) throws IOException {

        if (result.hasErrors()) {
            model.addAttribute("message", result);
            model.addAttribute("editForm", extensionForm);
            return "player/edit";
        }

        byte[] fig= extensionForm.getImage().getBytes();
        Binary binary=new Binary(fig);

        extensionForm.setLastModified(new Date());
        if (extensionForm.getId() == null) {
            extensionForm.setCreationTime(new Date());
            extensionForm.setCreator(coreService.getCurrentUser().getEmail());
        }

        Extension extension = modelMapper.map(extensionForm, Extension.class);
        Optional<Extension> extensionOptional=extensionRepository.findById(extensionForm.getId());
        if(extensionOptional.isPresent()){
            extension.setId(extensionOptional.get().getId());
        }
        extension.setImage(binary);

        extensionRepository.save(extension);
        model.addAttribute("editForm", extensionForm);

        return "redirect:/settings/extension";
    }

    @GetMapping("/add")
    public String addPlayer(Model model) {
        ExtensionForm form = new ExtensionForm();
        form.setCreationTime(new Date());
        form.setLastModified(new Date());
        form.setStatus(true);
        form.setCreator(coreService.getCurrentUser().getEmail());
        model.addAttribute("editForm", form);
        return "extension/edit";
    }
    @GetMapping("/delete")
    public String deletePlayer(@RequestParam("id") String ids, Model model) {
        for (String id : ids.split(",")) {
            extensionRepository.deleteById(new ObjectId(id));
        }
        return "redirect:/settings/extension";
    }
}

