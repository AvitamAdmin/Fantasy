package com.avitam.fantasy11.core.service.impl;

import com.avitam.fantasy11.core.model.*;
import com.avitam.fantasy11.core.service.UserTMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

@Service
public class UserTMServiceImpl implements UserTMService {

    public static final String TOKEN_INVALID = "invalidToken";
    public static final String TOKEN_EXPIRED = "expired";
    public static final String TOKEN_VALID = "valid";

    @Autowired
    private UserTMRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private VerificationTokenTMRepository tokenRepository;

    @Override
    public void save(UserTM user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        Role role = roleRepository.findByName("ROLE_USER");
        user.setRoles(new HashSet<>(Set.of(role)));
        userRepository.save(user);
    }

    @Override
    public UserTM findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public void createVerificationToken(UserTM user, String token) {
        VerificationTokenTM myToken = new VerificationTokenTM(token, user);
        tokenRepository.save(myToken);
    }

    @Override
    public VerificationTokenTM getVerificationToken(String VerificationToken) {
        return tokenRepository.findByToken(VerificationToken);
    }

    @Override
    public void saveRegisteredUser(UserTM user) {
        userRepository.save(user);
    }

    @Override
    public String validateVerificationToken(String token) {
        final VerificationTokenTM verificationToken = tokenRepository.findByToken(token);
        if (verificationToken == null) {
            return TOKEN_INVALID;
        }

        final UserTM user = verificationToken.getUser();
        final Calendar cal = Calendar.getInstance();
        if ((verificationToken.getExpiryDate()
                .getTime() - cal.getTime()
                .getTime()) <= 0) {
            tokenRepository.delete(verificationToken);
            return TOKEN_EXPIRED;
        }

        //user.setStatus(true);
        // tokenRepository.delete(verificationToken);
        userRepository.save(user);
        return TOKEN_VALID;
    }

    @Override
    public UserTM getUser(final String verificationToken) {
        final VerificationTokenTM token = tokenRepository.findByToken(verificationToken);
        if (token != null) {
            return token.getUser();
        }
        return null;
    }

    @Override
    public boolean isAdminRole() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        org.springframework.security.core.userdetails.User principalObject = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
        UserTM currentUser = userRepository.findByUsername(principalObject.getUsername());
        Set<Role> roles = currentUser.getRoles();
        for (Role role : roles) {
            if ("ROLE_ADMIN".equals(role.getName())) {
                return true;
            }
        }
        return false;
    }

    public boolean updateResetPasswordToken(String token, String email) {
        UserTM user = userRepository.findByUsername(email);
        if (user != null) {
            user.setResetPasswordToken(token);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    public UserTM getByResetPasswordToken(String token) {
        return userRepository.findByResetPasswordToken(token);
    }

    public void updatePassword(UserTM user, String newPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);

        user.setResetPasswordToken(null);
        userRepository.save(user);
    }

}
