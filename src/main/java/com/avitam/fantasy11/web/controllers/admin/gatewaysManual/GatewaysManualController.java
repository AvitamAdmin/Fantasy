package com.avitam.fantasy11.web.controllers.admin.gatewaysManual;

import com.avitam.fantasy11.core.service.CoreService;
import com.avitam.fantasy11.form.GatewaysAutomaticForm;
import com.avitam.fantasy11.form.GatewaysManualForm;
import com.avitam.fantasy11.model.GatewaysAutomatic;
import com.avitam.fantasy11.model.GatewaysAutomaticRepository;
import com.avitam.fantasy11.model.GatewaysManual;
import com.avitam.fantasy11.model.GatewaysManualRepository;
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
@RequestMapping("/admin/gatewaysManual")
public class GatewaysManualController {
    @Autowired
    private GatewaysManualRepository gatewaysManualRepository;
    @Autowired
    private CoreService coreService;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public String getAll(Model model){
        List<GatewaysManual> gatewaysManuals = gatewaysManualRepository.findAll();
        List<GatewaysManual> datas=new ArrayList<>();
        for(GatewaysManual gatewaysManual:gatewaysManuals){
            if(gatewaysManual.getId()!=null) {
                byte[] image = gatewaysManual.getLogo().getData();
                gatewaysManual.setPic(Base64.getEncoder().encodeToString(image));
                datas.add(gatewaysManual);
            }
        }
        model.addAttribute("models", datas);
        return "gatewaysManual/gatewaysManuals";
    }
    @PostMapping("/getedit")
    public String editGatewaysManual (@RequestParam("id")ObjectId id, Model model){

        Optional<GatewaysManual> gatewaysManualOptional = gatewaysManualRepository.findById(id);
        if (gatewaysManualOptional.isPresent()) {
            GatewaysManual gatewaysManual = gatewaysManualOptional.get();
            modelMapper.getConfiguration().setAmbiguityIgnored(true);
            GatewaysManualForm gatewaysManualForm = modelMapper.map(gatewaysManual, GatewaysManualForm.class);
            gatewaysManualForm.setId(String.valueOf(gatewaysManual.getId()));
            model.addAttribute("editForm", gatewaysManualForm);
        }
        return "gatewaysManual/edit";
    }

    @PostMapping("/edit")
    public String handleEdit(@ModelAttribute("editForm")GatewaysManualForm gatewaysManualForm,String id, Model model, BindingResult result) throws IOException {

        if (result.hasErrors()) {
            model.addAttribute("message", result);
            model.addAttribute("editForm", gatewaysManualForm);
            return "gatewaysManual/edit";
        }

        byte[] fig= gatewaysManualForm.getLogo().getBytes();
        Binary binary=new Binary(fig);

        gatewaysManualForm.setLastModified(new Date());
        if (gatewaysManualForm.getId() == null) {
            gatewaysManualForm.setCreationTime(new Date());
            gatewaysManualForm.setCreator(coreService.getCurrentUser().getEmail());
        }

        GatewaysManual gatewaysManual = modelMapper.map(gatewaysManualForm, GatewaysManual.class);

        Optional<GatewaysManual> gatewaysManualOptional=gatewaysManualRepository.findById(gatewaysManualForm.getId());
        if(gatewaysManualOptional.isPresent()){
            gatewaysManual.setId(gatewaysManualOptional.get().getId());
        }
        gatewaysManual.setLogo(binary);
        gatewaysManualRepository.save(gatewaysManual);
        model.addAttribute("editForm", gatewaysManualForm);

        return "redirect:/admin/gatewaysManual";
    }

    @GetMapping("/add")
    public String addGatewaysManual(Model model) {
        GatewaysManualForm form = new GatewaysManualForm();
        form.setCreationTime(new Date());
        form.setLastModified(new Date());
        form.setStatus(true);
        form.setCreator(coreService.getCurrentUser().getEmail());
        model.addAttribute("editForm", form);
        return "gatewaysManual/edit";
    }
    @GetMapping("/delete")
    public String deleteGatewaysAutomatic(@RequestParam("id") String ids, Model model) {
        for (String id : ids.split(",")) {
            gatewaysManualRepository.deleteById(new ObjectId(id));
        }
        return "redirect:/admin/gatewaysManual";
    }
}
