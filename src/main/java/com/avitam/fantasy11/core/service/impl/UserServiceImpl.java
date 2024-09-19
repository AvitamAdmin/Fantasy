package com.avitam.fantasy11.core.service.impl;

import com.avitam.fantasy11.api.dto.UserDto;
import com.avitam.fantasy11.core.service.CoreService;
import com.avitam.fantasy11.core.service.UserService;
import com.avitam.fantasy11.model.*;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    public static final String TOKEN_INVALID = "invalidToken";
    public static final String TOKEN_EXPIRED = "expired";
    public static final String TOKEN_VALID = "valid";

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private VerificationTokenRepository tokenRepository;
    @Autowired
    private CoreService coreService;

    @Override
    public void save(UserDto userDto, User requestUser) {
        User user=null;
        if(requestUser.getStatus()!=null)
        {
            user = userRepository.findByRecordId(requestUser.getRecordId());
            modelMapper.map(requestUser, user);
        }
        else {
            user = requestUser;
          //  user.setCreator(requestUser.getUsername());
            user.setCreationTime(new Date());
            user.setStatus(true);
            user.setLastModified(new Date());

        }
        if (StringUtils.isNotEmpty(user.getPassword())) {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            user.setPasswordConfirm(bCryptPasswordEncoder.encode(user.getPasswordConfirm()));
        }
        Set<Role> roles = requestUser.getRoles();
        Set<Role> rolesList = new HashSet<>();
        for (Role role : roles) {
            rolesList.add(roleRepository.findByRecordId(role.getRecordId()));
        }
        user.setRoles(rolesList);

        userRepository.save(user);
        if (user.getRecordId() == null) {
            user.setRecordId(String.valueOf(user.getId().getTimestamp()));
            userRepository.save(user);
        }
        userDto.setUser(user);
    }

    @Override
    public User findByEmail(String email) {

        return userRepository.findByEmail(email);
    }

    @Override
    public void createVerificationToken(User user, String token) {
        VerificationToken myToken = new VerificationToken();
        tokenRepository.save(myToken);
    }

    @Override
    public VerificationToken getVerificationToken(String VerificationToken) {
        return tokenRepository.findByToken(VerificationToken);
    }

    @Override
    public void saveRegisteredUser(User user) {
        userRepository.save(user);
    }

    @Override
    public String validateVerificationToken(String token) {
        final VerificationToken verificationToken = tokenRepository.findByToken(token);
        if (verificationToken == null) {
            return TOKEN_INVALID;
        }

        final User user = verificationToken.getUser();
        final Calendar cal = Calendar.getInstance();
        if ((verificationToken.getExpiryDate()
                .getTime() - cal.getTime()
                .getTime()) <= 0) {
            tokenRepository.delete(verificationToken);
            return TOKEN_EXPIRED;
        }

        user.setStatus(true);
        tokenRepository.delete(verificationToken);
        userRepository.save(user);
        return TOKEN_VALID;
    }

    @Override
    public User getUser(final String verificationToken) {
        final VerificationToken token = tokenRepository.findByToken(verificationToken);
        if (token != null) {
            return token.getUser();
        }
        return null;
    }

    @Override
    public boolean isAdminRole() {
        Set<Role> roles = coreService.getCurrentUser().getRoles();
        if (CollectionUtils.isNotEmpty(roles)) {
            for (Role role : roles) {
                if ("ROLE_ADMIN".equals(role.getName())) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean updateResetPasswordToken(String token, String email) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            user.setResetPasswordToken(token);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    public User getByResetPasswordToken(String token) {

        return userRepository.findByResetPasswordToken(token);
    }


    public void updatePassword(User user, String newPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);

        user.setResetPasswordToken(null);
        userRepository.save(user);
    }

}
