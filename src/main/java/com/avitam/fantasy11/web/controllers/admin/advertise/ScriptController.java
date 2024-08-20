package com.avitam.fantasy11.web.controllers.admin.advertise;

import com.avitam.fantasy11.core.service.CoreService;
import com.avitam.fantasy11.form.AddressForm;
import com.avitam.fantasy11.form.ScriptForm;
import com.avitam.fantasy11.model.Address;
import com.avitam.fantasy11.model.Script;
import com.avitam.fantasy11.model.ScriptRepository;
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
@RequestMapping("/advertise/script")
public class ScriptController {

    @Autowired
    private ScriptRepository scriptRepository;
    @Autowired
    private CoreService coreService;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public String getAllScripts(Model model){
        model.addAttribute("models",scriptRepository.findAll());
        return "advertise/scripts";
    }
    @GetMapping("/edit")
    public String editAddress(@RequestParam("id") ObjectId id, Model model) {
        Optional<Script> scriptOptional = scriptRepository.findById(id);
        if (scriptOptional.isPresent()) {
            Script script = scriptOptional.get();
            ScriptForm scriptForm = modelMapper.map(script, ScriptForm.class);
            model.addAttribute("editForm", scriptForm);
        }
        return "advertise/scriptEdit";
    }

    @PostMapping("/edit")
    public String handleEdit(@ModelAttribute("editForm") ScriptForm scriptForm, Model model, BindingResult result) {
        if (result.hasErrors()) {
            model.addAttribute("message", result);
            model.addAttribute("editForm",scriptForm);
            return "advertise/scriptEdit";
        }
        scriptForm.setLastModified(new Date());
        if (scriptForm.getId() == null) {
            scriptForm.setCreationTime(new Date());
            scriptForm.setCreator(coreService.getCurrentUser().getEmail());
        }
        Script script = modelMapper.map(scriptForm, Script.class);

        Optional<Script> scriptOptional = scriptRepository.findById(scriptForm.getId());
        if(scriptOptional.isPresent()){
            script.setId(scriptOptional.get().getId());
        }
        scriptRepository.save(script);
        model.addAttribute("editForm", scriptForm);
        return "redirect:/advertise/script";
    }

    @GetMapping("/add")
    public String addScript(Model model) {
        ScriptForm form = new ScriptForm();
        form.setCreationTime(new Date());
        form.setLastModified(new Date());
        form.setStatus(true);
        form.setCreator(coreService.getCurrentUser().getEmail());
        model.addAttribute("editForm", form);
        return "advertise/scriptEdit";
    }

    @GetMapping("/delete")
    public String deleteScript(@RequestParam("id") String ids, Model model) {
        for (String id : ids.split(",")) {
            scriptRepository.deleteById(new ObjectId(id));
        }
        return "redirect:/advertise/script";
    }
}
