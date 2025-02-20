package com.avitam.fantasy11.core.service.impl;

import com.avitam.fantasy11.api.dto.RoleDto;
import com.avitam.fantasy11.api.dto.UserDto;
import com.avitam.fantasy11.api.dto.UserWsDto;
import com.avitam.fantasy11.core.service.CoreService;
import com.avitam.fantasy11.core.service.UserService;
import com.avitam.fantasy11.model.Role;
import com.avitam.fantasy11.model.User;
import com.avitam.fantasy11.model.VerificationToken;
import com.avitam.fantasy11.repository.RoleRepository;
import com.avitam.fantasy11.repository.UserRepository;
import com.avitam.fantasy11.repository.VerificationTokenRepository;
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
    public static final String ADMIN_USER = "/admin/user";

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private VerificationTokenRepository tokenRepository;
    @Autowired
    private CoreService coreService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void save(UserWsDto request) {
        List<UserDto> userDtos = request.getUserDtoList();
        List<User> userList = new ArrayList<>();
        User user = null;
        for (UserDto userDto : userDtos) {
            if (userDto.getRecordId() != null) {
                user = userRepository.findByRecordId(userDto.getRecordId());
                modelMapper.map(userDto, user);
                userRepository.save(user);
            } else {
                user = modelMapper.map(userDto, User.class);
                user.setStatus(true);
                user.setCreationTime(new Date());
                Set<Role> roles = new HashSet<>();
                for (RoleDto role : userDto.getRoles()) {
                    Role role1 = roleRepository.findByRoleId(role.getRoleId());
                    roles.add(role1);
                }
                user.setRoles(roles);

            }
            user.setLastModified(new Date());
              if (StringUtils.isNotEmpty(user.getPassword())) {
                user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

            }
            userRepository.save(user);
            if (user.getRecordId() == null) {
                user.setRecordId(String.valueOf(user.getId().getTimestamp()));
            }
            userRepository.save(user);

            userList.add(user);
        }
        request.setBaseUrl(ADMIN_USER);
        request.setUserDtoList(modelMapper.map(userList, List.class));
    }

    @Override
    public User findByUserName(String userName) {
        return userRepository.findByUsername(userName);
    }

    @Override
    public User findByEmail(String email) {

        return userRepository.findByEmail(email);
    }

    @Override
    public void createVerificationToken(User user, String token) {
        VerificationToken myToken = new VerificationToken();
        myToken.setToken(token);
        myToken.setUser(user);
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

    @Override
    public boolean updateOtp(String token, String email) {
        return false;
    }


    public void updatePassword(User user, String newPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);
        user.setResetPasswordToken(null);
        userRepository.save(user);
    }

}