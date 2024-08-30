package com.avitam.fantasy11.web.controllers.admin.playerRole;

import com.avitam.fantasy11.core.service.CoreService;
import com.avitam.fantasy11.form.NotificationForm;
import com.avitam.fantasy11.form.PlayerRoleForm;
import com.avitam.fantasy11.model.*;
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
@RequestMapping("/admin/playerRole")
public class PlayerRoleController {

    @Autowired
    private PlayerRoleRepository playerRoleRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CoreService coreService;

    @GetMapping
    public String getAllModels(Model model) {
        model.addAttribute("models", playerRoleRepository.findAll());
        return "playerRole/playerRoles";
    }

    @GetMapping("/edit")
    public String editPlayerRole(@RequestParam("id") String id, Model model) {

        Optional<PlayerRole> playerRoleFormOptional = playerRoleRepository.findByRecordId(id);
        if (playerRoleFormOptional.isPresent()) {
            PlayerRole playerRole = playerRoleFormOptional.get();
            PlayerRoleForm playerRoleForm = modelMapper.map(playerRole, PlayerRoleForm.class);
            playerRoleForm.setId(String.valueOf(playerRole.getId()));
            model.addAttribute("editForm", playerRoleForm);
        }
        return "playerRole/edit";
    }

    @PostMapping("/edit")
    public String handleEdit(@ModelAttribute("editForm") PlayerRoleForm playerRoleForm, Model model, BindingResult result) {
        //new PlayerRoleFormValidator().validate(playerRoleForm, result);
        if (result.hasErrors()) {
            model.addAttribute("message", result);
            model.addAttribute("editForm",playerRoleForm);
            return "playerRole/edit";
        }
        playerRoleForm.setLastModified(new Date());
        if (playerRoleForm.getId() == null) {
            playerRoleForm.setCreationTime(new Date());
            playerRoleForm.setCreator(coreService.getCurrentUser().getEmail());
        }
        PlayerRole playerRole = modelMapper.map(playerRoleForm, PlayerRole.class);

        Optional<PlayerRole> playerRoleOptional = playerRoleRepository.findById(playerRoleForm.getId());
        if(playerRoleOptional.isPresent()){
            playerRole.setId(playerRoleOptional.get().getId());
        }

        playerRoleRepository.save(playerRole);
        if(playerRole.getRecordId()==null)
        {
            playerRole.setRecordId(String.valueOf(playerRole.getId().getTimestamp()));
        }
        playerRoleRepository.save(playerRole);
        model.addAttribute("editForm", playerRoleForm);
        return "redirect:/admin/playerRole";
    }

    @GetMapping("/add")
    public String addPlayerRole(Model model) {
        PlayerRoleForm form = new PlayerRoleForm();
        form.setCreationTime(new Date());
        form.setLastModified(new Date());
        form.setStatus(true);
        form.setCreator(coreService.getCurrentUser().getEmail());
        model.addAttribute("editForm", form);
        return "playerRole/edit";
    }

    @GetMapping("/delete")
    public String deletePlayerRole(@RequestParam("id") String ids, Model model) {
        for (String id : ids.split(",")) {
            playerRoleRepository.deleteByRecordId(id);
        }
        return "redirect:/admin/playerRole";
    }
}
