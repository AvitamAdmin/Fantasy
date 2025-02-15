package com.avitam.fantasy11.web.controllers;

import com.avitam.fantasy11.api.dto.UserWsDto;
import com.avitam.fantasy11.core.Utility;
import com.avitam.fantasy11.core.service.UserService;
import com.avitam.fantasy11.mail.service.EMail;
import com.avitam.fantasy11.mail.service.MailService;
import com.avitam.fantasy11.model.User;
import com.avitam.fantasy11.repository.RoleRepository;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

@Controller
@CrossOrigin
public class SecurityController {

    @Autowired
    private UserService userService;

    @Autowired
    private MailService mailService;

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

        //eMail.setFrom("healthcheck@cheil.com");
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
    @ResponseBody
    public UserWsDto processRegister(@RequestBody UserWsDto request) {
        userService.save(request);
        request.setMessage("Registration Successful!!");
        return request;
    }

    @GetMapping("/login")
    public String login(Model model, String error, String logout, String quota) {
        if (error != null) {
            model.addAttribute("message", "Your username and password is invalid.");
        }

        if (quota != null) {
            model.addAttribute("message", "Allotted Quota Exceeded");
        }

        if (logout != null) {
            model.addAttribute("message", "You have been logged out successfully.");
        }

        return "login";
    }


}
