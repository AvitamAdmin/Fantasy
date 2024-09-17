package com.avitam.fantasy11.core.service;


import com.avitam.fantasy11.api.dto.UserDto;
import com.avitam.fantasy11.model.User;
import com.avitam.fantasy11.model.VerificationToken;

public interface UserService {
    void save(UserDto userDto, User user);

    User findByEmail(String email);

    void createVerificationToken(User user, String token);

    VerificationToken getVerificationToken(String VerificationToken);

    void saveRegisteredUser(User user);

    String validateVerificationToken(String token);

    User getUser(String verificationToken);

    boolean isAdminRole();

    boolean updateResetPasswordToken(String token, String email);

    User getByResetPasswordToken(String token);

    void updatePassword(User user, String password);
}
