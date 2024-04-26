package com.avitam.fantasy11.web.controllers.admin.role;

import com.avitam.fantasy11.form.RoleForm;
import com.avitam.fantasy11.model.NodeRepository;
import com.avitam.fantasy11.model.Role;
import com.avitam.fantasy11.model.RoleRepository;
import com.avitam.fantasy11.core.service.CoreService;
import com.avitam.fantasy11.validation.RoleFormValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/role")
public class RoleController {
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private NodeRepository nodeRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CoreService coreService;

    @GetMapping
    public String getRoles(Model model) {
        List<Role> roles = roleRepository.findAll();
        model.addAttribute("roles", roles);
        return "role/roles";
    }

    @GetMapping("/edit")
    public String editUser(@RequestParam("id") Long id, Model model) {
        if (id == null) {
            model.addAttribute("message", "Please select a row for edit operation!");
            return "role/edit";
        }
        Optional<Role> roleOptional = roleRepository.findById(id);
        if (roleOptional.isPresent()) {
            RoleForm roleForm = modelMapper.map(roleOptional.get(), RoleForm.class);
            model.addAttribute("nodes", nodeRepository.findAll());
            model.addAttribute("roleForm", roleForm);
        }
        return "role/edit";
    }

    @PostMapping("/edit")
    public String handleEdit(@ModelAttribute("roleForm") RoleForm roleForm, Model model, BindingResult result) {
        new RoleFormValidator().validate(roleForm, result);
        model.addAttribute("nodes", nodeRepository.findAll());
        if (result.hasErrors()) {
            model.addAttribute("message", result);
            return "role/edit";
        }
        Role role = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        org.springframework.security.core.userdetails.User principalObject = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
        if (roleForm.getId() != null) {
            Optional<Role> optionalRole = roleRepository.findById(roleForm.getId());
            if (optionalRole.isPresent()) {
                role = optionalRole.get();
                role.setName(roleForm.getName());
                role.setPermissions(roleForm.getPermissions());
            }
        } else {
            role = modelMapper.map(roleForm, Role.class);
            role.setCreationTime(new Date());
        }
        role.setLastModified(new Date());
        role.setCreator(principalObject.getUsername());
        roleRepository.save(role);
        model.addAttribute("message", "Role was updated successfully!");
        return "redirect:/admin/role";
    }

    @GetMapping("/add")
    public String addUser(@ModelAttribute RoleForm roleForm, Model model) {
        RoleForm form = new RoleForm();
        form.setCreationTime(new Date());
        form.setLastModified(new Date());
        form.setStatus(true);
        form.setCreator(coreService.getCurrentUser().getName());
        model.addAttribute("nodes", nodeRepository.findAll());
        model.addAttribute("roleForm", form);
        return "role/edit";
    }

    @GetMapping("/delete")
    public String deleteRole(@RequestParam("id") String ids, Model model) {
        for (String id : ids.split(",")) {
            roleRepository.deleteById(Long.valueOf(id));
        }
        return "redirect:/admin/role";
    }
}
