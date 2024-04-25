package com.avitam.fantasy11.web.controllers;

import com.avitam.fantasy11.core.model.RoleRepository;
import com.avitam.fantasy11.core.model.UserTM;
import com.avitam.fantasy11.core.model.UserTMRepository;
import com.avitam.fantasy11.core.service.CoreService;
import com.avitam.fantasy11.form.UserForm;
import com.avitam.fantasy11.validation.UserFormValidator;
import com.avitam.fantasy11.validation.UserValidator;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    UserFormValidator userFormValidator;
    @Autowired
    private UserValidator userValidator;
    @Autowired
    private CoreService coreService;
    @Autowired
    private UserTMRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/user")
    public String getAllUsers(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        org.springframework.security.core.userdetails.User principalObject = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
        model.addAttribute("userList", userRepository.findAll());
        return "admin/usersContent";
    }

    @GetMapping("/user/edit")
    public String editUser(@RequestParam("id") Long id, Model model) {
        UserForm userForm = new UserForm();
        Optional<UserTM> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            UserTM user = userOptional.get();
            userForm = modelMapper.map(user, UserForm.class);
            userForm.setPasswordConfirm(null);
            userForm.setPassword(null);
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            org.springframework.security.core.userdetails.User principalObject = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
            UserTM currentUser = userRepository.findByUsername(principalObject.getUsername());
            model.addAttribute("isAdmin", currentUser.getRoles().stream().filter(role -> role.getName().equalsIgnoreCase("ROLE_ADMIN")).findAny().isPresent());
            model.addAttribute("roles", roleRepository.findAll());
            model.addAttribute("editForm", userForm);

        }
        return "admin/usersEditContent";
    }

    @PostMapping("/user/edit")
    public String handleEdit(@ModelAttribute("editForm") UserForm userForm, Model model, BindingResult result, RedirectAttributes redirectAttributes) {

        UserTM user = null;
        model.addAttribute("roles", roleRepository.findAll());


        if (userForm.getId() == null) {
            user = new UserTM();
            user = modelMapper.map(userForm, UserTM.class);
            userValidator.validate(user, result);
            if (result.hasErrors()) {
                model.addAttribute("editForm", userForm);
                return "admin/usersEditContent";
            }
        } else {
            //userFormValidator.validate(userForm, result);
            user = userRepository.findById(Long.valueOf(userForm.getId())).get();
            user.setLocale(userForm.getLocale());
            user.setEmail(userForm.getEmail());
            user.setRoles(userForm.getRoles());
        }
        if (result.hasErrors()) {
            model.addAttribute("editForm", userForm);
            return "admin/usersEditContent";
        }
        if (StringUtils.isNotEmpty(userForm.getPassword())) {
            user.setPassword(bCryptPasswordEncoder.encode(userForm.getPassword()));
            user.setPasswordConfirm(bCryptPasswordEncoder.encode(userForm.getPasswordConfirm()));
        }
        user.setStatus(true);
        userRepository.save(user);
        redirectAttributes.addAttribute("id", user.getId());
        return "redirect:/admin/user";
    }

    @GetMapping("/user/add")
    public String addUser(@ModelAttribute UserForm userForm, Model model, BindingResult resultl, RedirectAttributes redirectAttributes) {
        model.addAttribute("roles", roleRepository.findAll());


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        org.springframework.security.core.userdetails.User principalObject = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
        UserTM currentUser = userRepository.findByUsername(principalObject.getUsername());
        model.addAttribute("isAdmin", currentUser.getRoles().stream().filter(role -> role.getName().equalsIgnoreCase("ROLE_ADMIN")).findAny().isPresent());

        userForm.setCreationTime(new Date());
        userForm.setLastModified(new Date());
        userForm.setStatus(true);
        userForm.setCreator(coreService.getCurrentUser().getUsername());
        model.addAttribute("editForm", userForm);
        return "admin/usersEditContent";
    }

    @GetMapping("/user/delete")
    public String deleteUser(@RequestParam("id") String ids, RedirectAttributes redirectAttributes) {
        for (String id : ids.split(",")) {
            userRepository.deleteById(Long.valueOf(id));
        }
        redirectAttributes.addAttribute("userList", userRepository.findAll());
        return "redirect:/admin/user";
    }

    public String updateUsers(@ModelAttribute("usersForm") List<UserTM> users, BindingResult bindingResultUser) {
        userRepository.saveAll(users);
        return "redirect:/admin/user";
    }
}
