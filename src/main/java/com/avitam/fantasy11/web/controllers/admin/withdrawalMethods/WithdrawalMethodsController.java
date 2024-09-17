package com.avitam.fantasy11.web.controllers.admin.withdrawalMethods;

import com.avitam.fantasy11.core.service.CoreService;
import com.avitam.fantasy11.form.GatewaysAutomaticForm;
import com.avitam.fantasy11.form.WithdrawalMethodsForm;
import com.avitam.fantasy11.model.GatewaysAutomatic;
import com.avitam.fantasy11.model.GatewaysAutomaticRepository;
import com.avitam.fantasy11.model.WithdrawalMethods;
import com.avitam.fantasy11.model.WithdrawalMethodsRepository;
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
@RequestMapping("/admin/withdrawalMethods")
public class WithdrawalMethodsController {
    @Autowired
    private WithdrawalMethodsRepository withdrawalMethodsRepository;
    @Autowired
    private CoreService coreService;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public String getAll(Model model){
        List<WithdrawalMethods> withdrawalMethods = withdrawalMethodsRepository.findAll();
        List<WithdrawalMethods> datas=new ArrayList<>();
        for(WithdrawalMethods withdrawalMethod:withdrawalMethods){
            if(withdrawalMethod.getId()!=null) {
                byte[] image = withdrawalMethod.getLogo().getData();
                withdrawalMethod.setPic(Base64.getEncoder().encodeToString(image));
                datas.add(withdrawalMethod);
            }
        }
        model.addAttribute("models", datas);
        return "withdrawalMethods/withdrawalMethodss";
    }
    @GetMapping("/edit")
    public String editGatewaysAutomatic (@RequestParam("id")ObjectId id, Model model){

        Optional<WithdrawalMethods> withdrawalMethodsOptional = withdrawalMethodsRepository.findById(id);
        if (withdrawalMethodsOptional.isPresent()) {
            WithdrawalMethods withdrawalMethods = withdrawalMethodsOptional.get();
            modelMapper.getConfiguration().setAmbiguityIgnored(true);
            WithdrawalMethodsForm withdrawalMethodsForm = modelMapper.map(withdrawalMethods, WithdrawalMethodsForm.class);
            withdrawalMethodsForm.setId(String.valueOf(withdrawalMethods.getId()));
            model.addAttribute("editForm", withdrawalMethodsForm);
        }
        return "withdrawalMethods/edit";
    }

    @PostMapping("/edit")
    public String handleEdit(@ModelAttribute("editForm")WithdrawalMethodsForm withdrawalMethodsForm,String id, Model model, BindingResult result) throws IOException {

        if (result.hasErrors()) {
            model.addAttribute("message", result);
            model.addAttribute("editForm", withdrawalMethodsForm);
            return "withdrawalMethods/edit";
        }

        byte[] fig= withdrawalMethodsForm.getLogo().getBytes();
        Binary binary=new Binary(fig);

        withdrawalMethodsForm.setLastModified(new Date());
        if (withdrawalMethodsForm.getId() == null) {
            withdrawalMethodsForm.setCreationTime(new Date());
            withdrawalMethodsForm.setCreator(coreService.getCurrentUser().getEmail());
        }

        WithdrawalMethods withdrawalMethods = modelMapper.map(withdrawalMethodsForm, WithdrawalMethods.class);

        Optional<WithdrawalMethods> withdrawalMethodsOptional=withdrawalMethodsRepository.findById(withdrawalMethodsForm.getId());
        if(withdrawalMethodsOptional.isPresent()){
            withdrawalMethods.setId(withdrawalMethodsOptional.get().getId());
        }
        withdrawalMethods.setLogo(binary);
        withdrawalMethodsRepository.save(withdrawalMethods);
        model.addAttribute("editForm", withdrawalMethodsForm);

        return "redirect:/admin/withdrawalMethods";
    }

    @GetMapping("/add")
    public String addGatewaysAutomatic(Model model) {
        WithdrawalMethodsForm form = new WithdrawalMethodsForm();
        form.setCreationTime(new Date());
        form.setLastModified(new Date());
        form.setStatus(true);
        form.setCreator(coreService.getCurrentUser().getEmail());
        model.addAttribute("editForm", form);
        return "withdrawalMethods/edit";
    }
    @GetMapping("/delete")
    public String deleteGatewaysAutomatic(@RequestParam("id") String ids, Model model) {
        for (String id : ids.split(",")) {
            withdrawalMethodsRepository.deleteById(new ObjectId(id));
        }
        return "redirect:/admin/withdrawalMethods";
    }
}
