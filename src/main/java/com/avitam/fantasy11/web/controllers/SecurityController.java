package com.avitam.fantasy11.web.controllers;

import com.avitam.fantasy11.core.Utility;
import com.avitam.fantasy11.core.event.OnRegistrationCompleteEvent;
import com.avitam.fantasy11.core.model.RoleRepository;
import com.avitam.fantasy11.core.model.User;
import com.avitam.fantasy11.core.model.UserRepository;
import com.avitam.fantasy11.core.service.SecurityService;
import com.avitam.fantasy11.core.service.UserService;
import com.avitam.fantasy11.form.UserForm;
import com.avitam.fantasy11.mail.service.EMail;
import com.avitam.fantasy11.mail.service.MailService;
import com.avitam.fantasy11.validation.UserValidator;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.UnsupportedEncodingException;
import java.util.Locale;
import java.util.UUID;

@Controller
@CrossOrigin
public class SecurityController {

    @Autowired
    ApplicationEventPublisher eventPublisher;
    @Autowired
    private UserService userService;
    @Autowired
    private UserValidator userValidator;
    @Autowired
    private SecurityService securityService;
    @Autowired
    private MailService mailService;
    @Autowired
    private MessageSource messages;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @GetMapping("/forgotpassword")
    public String showForgotPasswordForm(HttpServletRequest request, Model model) {
        return "security/forgotpassword";
    }

    @PostMapping("/forgotpassword")
    public String processForgotPassword(HttpServletRequest request, Model model) {
        String email = request.getParameter("email");
        String token = UUID.randomUUID().toString();

        try {
            boolean success = userService.updateResetPasswordToken(token, email);
            if (success) {
                String resetPasswordLink = Utility.getSiteURL(request) + "/resetpassword?token=" + token;
                sendEmail(email, resetPasswordLink);
                model.addAttribute("message", "We have sent a reset password link to your email. Please check.");
                model.addAttribute("color", "green");
            } else {
                model.addAttribute("message", "User Not Registered. Please enter valid email id");
                model.addAttribute("color", "red");
            }

        } catch (UnsupportedEncodingException | MessagingException e) {
            model.addAttribute("error", "Error while sending email");
            model.addAttribute("color", "red");
        }
        return "security/forgotpasswordsuccess";
    }

    public void sendEmail(String recipientEmail, String link)
            throws MessagingException, UnsupportedEncodingException {
        EMail eMail = new EMail();

        eMail.setFrom("healthcheck@cheil.com");
        eMail.setTo(recipientEmail);

        String subject = "Here's the link to reset your password";

        String content = "<p>Hello,</p>"
                + "<p>You have requested to reset your password.</p>"
                + "<p>Click the link below to change your password:</p>"
                + "<p><a href=\"" + link + "\">Change my password</a></p>"
                + "<br>"
                + "<p>Ignore this email if you do remember your password, "
                + "or you have not made the request.</p>";

        eMail.setSubject(subject);
        eMail.setContent(content);
        mailService.sendEmail(eMail);
    }

    @GetMapping("/resetpassword")
    public String showResetPasswordForm(@RequestParam(value = "token") String token, Model model) {
        User user = userService.getByResetPasswordToken(token);
        model.addAttribute("token", token);

        if (user == null) {
            model.addAttribute("message", "Invalid Token");
            return "security/forgotpassword";
        }
        return "security/passwordreset";
    }

    @PostMapping("/resetpassword")
    public String processResetPassword(HttpServletRequest request, Model model) {
        String token = request.getParameter("token");
        String password = request.getParameter("password");

        User user = userService.getByResetPasswordToken(token);
        model.addAttribute("title", "Reset your password");

        if (user == null) {
            model.addAttribute("message", "Invalid Token");
            return "security/resetsuccess";
        } else {
            userService.updatePassword(user, password);
            model.addAttribute("message", "You have successfully changed your password.");
        }
        return "security/resetsuccess";
    }

    @GetMapping("")
    public String viewHomePage() {
        return "index";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {

        model.addAttribute("userForm", new User());

        model.addAttribute("roles", roleRepository.findAll());
        return "security/signupForm";
    }

    @PostMapping("/register")
    public String processRegister(HttpServletRequest request, @ModelAttribute("userForm") User user, BindingResult bindingResultUser, Model model) {
        userValidator.validate(user, bindingResultUser);
        if (bindingResultUser.hasErrors()) {
            model.addAttribute("userForm", new User());

            model.addAttribute("roles", roleRepository.findAll());
            model.addAttribute("message", bindingResultUser);
            return "security/signupForm";
        }
        userService.save(user);
        String appUrl = Utility.getSiteURL(request);
        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(user, request.getLocale(), appUrl, "New user Registration", "New user " + user.getUsername() + " as registered, Kindly approve the same by clicking the link below", "hybris.sup@cheil.com", "1"));
        model.addAttribute("message", "You have signed up successfully! We will notify once account is approved");
        return "security/signupSuccessForm";
    }

    @GetMapping("/login")
    public String login(Model model, String error, String logout) {
        if (error != null)
            model.addAttribute("error", "Your username and password is invalid.");

        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");

        return "login";
    }

    @GetMapping("/registrationConfirm")
    public ModelAndView confirmRegistration(final HttpServletRequest request, final ModelMap model, @RequestParam("level") final String level, @RequestParam("token") final String token) throws UnsupportedEncodingException {
        Locale locale = request.getLocale();
        model.addAttribute("lang", locale.getLanguage());
        final String result = userService.validateVerificationToken(token);
        if (result.equals("valid")) {
            final User user = userService.getUser(token);
            if (level.equals("1")) {
                model.addAttribute("message", "User Approved");
                String appUrl = Utility.getSiteURL(request);
                eventPublisher.publishEvent(new OnRegistrationCompleteEvent(user, request.getLocale(), appUrl, "New user Registration", "New user " + user.getUsername() + " has been approved by admin, Kindly approve by clicking the link below", user.getReferredBy(), "2"));
                return new ModelAndView("security/signupSuccessForm");
            } else if (level.equals("2")) {
                String appUrl = Utility.getSiteURL(request);
                eventPublisher.publishEvent(new OnRegistrationCompleteEvent(user, request.getLocale(), appUrl, "Registration Successful", "Registration successful, Kindly click link below to verify your account", user.getUsername(), "3"));
                model.addAttribute("message", "User Approved");
                return new ModelAndView("security/signupSuccessForm");
            } else if (level.equals("3")) {
                model.addAttribute("message", "You have signed up successfully!");
                user.setStatus(true);
                userRepository.save(user);
                return new ModelAndView("security/signupSuccessForm");
            }
            model.addAttribute("messageKey", "message.accountVerified");
            return new ModelAndView("redirect:/login", model);
        }

        model.addAttribute("messageKey", "auth.message." + result);
        model.addAttribute("expired", "expired".equals(result));
        model.addAttribute("token", token);
        return new ModelAndView("redirect:/badUser", model);
    }

    @GetMapping("/profile")
    public String profile(Model model) {
        UserForm userForm = new UserForm();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        org.springframework.security.core.userdetails.User principalObject = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
        User user = userRepository.findByUsername(principalObject.getUsername());

        userForm.setStatus(user.getStatus());
        userForm.setUsername(user.getUsername());
        userForm.setRoles(user.getRoles());
        userForm.setPassword(user.getPassword());
        userForm.setPasswordConfirm(user.getPassword());
        userForm.setId(user.getId());
        model.addAttribute("roles", roleRepository.findAll());
        model.addAttribute("editForm", userForm);


        model.addAttribute("isAdmin", user.getRoles().stream().filter(role -> role.getName().equalsIgnoreCase("ROLE_ADMIN")).findAny().isPresent());


        return "admin/usersEditContent";
    }

}