package com.avitam.fantasy11.core.service;


import com.avitam.fantasy11.model.User;

public interface UserService {
    void save(UserTM user);

    UserTM findByUsername(String username);

    void createVerificationToken(UserTM user, String token);

    VerificationTokenTM getVerificationToken(String VerificationToken);

    void saveRegisteredUser(UserTM user);

    String validateVerificationToken(String token);

    User getUser(String verificationToken);

    boolean isAdminRole();

    boolean updateResetPasswordToken(String token, String email);

    User getByResetPasswordToken(String token);

    void updatePassword(UserTM user, String newPassword);
}
