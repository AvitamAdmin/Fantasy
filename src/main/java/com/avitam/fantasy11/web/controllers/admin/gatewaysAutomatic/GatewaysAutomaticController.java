package com.avitam.fantasy11.web.controllers.admin.gatewaysAutomatic;

import com.avitam.fantasy11.core.service.CoreService;
import com.avitam.fantasy11.form.GatewaysAutomaticForm;
import com.avitam.fantasy11.form.TeamForm;
import com.avitam.fantasy11.model.GatewaysAutomatic;
import com.avitam.fantasy11.model.GatewaysAutomaticRepository;
import com.avitam.fantasy11.model.Team;
import com.avitam.fantasy11.model.TeamRepository;
import org.bson.types.Binary;
import org.bson.types.ObjectId;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/admin/gatewaysAutomatic")
public class GatewaysAutomaticController {
    @Autowired
    private GatewaysAutomaticRepository gatewaysAutomaticRepository;
    @Autowired
    private CoreService coreService;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public String getAll(Model model){
        List<GatewaysAutomatic> gatewaysAutomatics = gatewaysAutomaticRepository.findAll();
        List<GatewaysAutomatic> datas=new ArrayList<>();
        for(GatewaysAutomatic gatewaysAutomatic:gatewaysAutomatics){
            if(gatewaysAutomatic.getId()!=null) {
                byte[] image = gatewaysAutomatic.getLogo().getData();
                gatewaysAutomatic.setPic(Base64.getEncoder().encodeToString(image));
                datas.add(gatewaysAutomatic);
            }
        }
        model.addAttribute("models", datas);
        return "gatewaysAutomatic/gatewaysAutomatics";
    }
    @PostMapping("/getedit")
    public String editGatewaysAutomatic (@RequestParam("id")ObjectId id, Model model){

        Optional<GatewaysAutomatic> gatewaysAutomaticOptional = gatewaysAutomaticRepository.findById(id);
        if (gatewaysAutomaticOptional.isPresent()) {
            GatewaysAutomatic gatewaysAutomatic = gatewaysAutomaticOptional.get();
            modelMapper.getConfiguration().setAmbiguityIgnored(true);
            GatewaysAutomaticForm gatewaysAutomaticForm = modelMapper.map(gatewaysAutomatic, GatewaysAutomaticForm.class);
            gatewaysAutomaticForm.setId(String.valueOf(gatewaysAutomatic.getId()));
            model.addAttribute("editForm", gatewaysAutomaticForm);
        }
        return "gatewaysAutomatic/edit";
    }

    @PostMapping("/edit")
    public String handleEdit(@ModelAttribute("editForm")GatewaysAutomaticForm gatewaysAutomaticForm,String id, Model model, BindingResult result) throws IOException {

        if (result.hasErrors()) {
            model.addAttribute("message", result);
            model.addAttribute("editForm", gatewaysAutomaticForm);
            return "gatewaysAutomatic/edit";
        }

        byte[] fig= gatewaysAutomaticForm.getLogo().getBytes();
        Binary binary=new Binary(fig);

        gatewaysAutomaticForm.setLastModified(new Date());
        if (gatewaysAutomaticForm.getId() == null) {
            gatewaysAutomaticForm.setCreationTime(new Date());
            gatewaysAutomaticForm.setCreator(coreService.getCurrentUser().getEmail());
        }

        GatewaysAutomatic gatewaysAutomatic = modelMapper.map(gatewaysAutomaticForm, GatewaysAutomatic.class);

        Optional<GatewaysAutomatic> gatewaysAutomaticOptional=gatewaysAutomaticRepository.findById(gatewaysAutomaticForm.getId());
        if(gatewaysAutomaticOptional.isPresent()){
            gatewaysAutomatic.setId(gatewaysAutomaticOptional.get().getId());
        }
        gatewaysAutomatic.setLogo(binary);
        gatewaysAutomaticRepository.save(gatewaysAutomatic);
        model.addAttribute("editForm", gatewaysAutomaticForm);

        return "redirect:/admin/gatewaysAutomatic";
    }

    @GetMapping("/add")
    public String addGatewaysAutomatic(Model model) {
        GatewaysAutomaticForm form = new GatewaysAutomaticForm();
        form.setCreationTime(new Date());
        form.setLastModified(new Date());
        form.setStatus(true);
        form.setCreator(coreService.getCurrentUser().getEmail());
        model.addAttribute("editForm", form);
        return "gatewaysAutomatic/edit";
    }
    @GetMapping("/delete")
    public String deleteGatewaysAutomatic(@RequestParam("id") String ids, Model model) {
        for (String id : ids.split(",")) {
            gatewaysAutomaticRepository.deleteById(new ObjectId(id));
        }
        return "redirect:/admin/gatewaysAutomatic";
    }
}
