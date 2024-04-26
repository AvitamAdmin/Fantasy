package com.avitam.fantasy11.web.controllers;

import com.avitam.fantasy11.form.UserForm;
import com.avitam.fantasy11.model.RoleRepository;
import com.avitam.fantasy11.core.service.CoreService;
import com.avitam.fantasy11.model.User;
import com.avitam.fantasy11.model.UserRepository;
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
    private UserRepository userRepository;
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
    public String editUser(@RequestParam("id") Integer id, Model model) {
        UserForm userForm = new UserForm();
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            userForm = modelMapper.map(user, UserForm.class);
            userForm.setPasswordConfirm(null);
            userForm.setPassword(null);
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            org.springframework.security.core.userdetails.User principalObject = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
            User currentUser = userRepository.findByName(principalObject.getUsername());
            model.addAttribute("isAdmin", currentUser.getRoles().stream().filter(role -> role.getName().equalsIgnoreCase("ROLE_ADMIN")).findAny().isPresent());
            model.addAttribute("roles", roleRepository.findAll());
            model.addAttribute("editForm", userForm);

        }
        return "admin/usersEditContent";
    }

    @PostMapping("/user/edit")
    public String handleEdit(@ModelAttribute("editForm") UserForm userForm, Model model, BindingResult result, RedirectAttributes redirectAttributes) {

        User user = null;
        model.addAttribute("roles", roleRepository.findAll());


        if (userForm.getId() == null) {
            user = new User();
            user = modelMapper.map(userForm, User.class);
            userValidator.validate(user, result);
            if (result.hasErrors()) {
                model.addAttribute("editForm", userForm);
                return "admin/usersEditContent";
            }
        } else {
            //userFormValidator.validate(userForm, result);
          //  user = userRepository.findById(userForm.getId());
            user.setEmailId(userForm.getEmailId());
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
        User currentUser = userRepository.findByName(principalObject.getUsername());
        model.addAttribute("isAdmin", currentUser.getRoles().stream().filter(role -> role.getName().equalsIgnoreCase("ROLE_ADMIN")).findAny().isPresent());

        userForm.setCreationTime(new Date());
        userForm.setLastModified(new Date());
        userForm.setStatus(1);
        userForm.setCreator(coreService.getCurrentUser().getName());
        model.addAttribute("editForm", userForm);
        return "admin/usersEditContent";
    }

    @GetMapping("/user/delete")
    public String deleteUser(@RequestParam("id") String ids, RedirectAttributes redirectAttributes) {
        for (String id : ids.split(",")) {
           // userRepository.deleteById(.id);
        }
        redirectAttributes.addAttribute("userList", userRepository.findAll());
        return "redirect:/admin/user";
    }

    public String updateUsers(@ModelAttribute("usersForm") List<User> users, BindingResult bindingResultUser) {
        userRepository.saveAll(users);
        return "redirect:/admin/user";
    }
}
