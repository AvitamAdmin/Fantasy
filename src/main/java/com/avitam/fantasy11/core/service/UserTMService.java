package com.avitam.fantasy11.core.service;


import com.avitam.fantasy11.core.model.UserTM;
import com.avitam.fantasy11.core.model.VerificationTokenTM;

public interface UserTMService {
    void save(UserTM user);

    UserTM findByUsername(String username);

    void createVerificationToken(UserTM user, String token);

    VerificationTokenTM getVerificationToken(String VerificationToken);

    void saveRegisteredUser(UserTM user);

    String validateVerificationToken(String token);

    UserTM getUser(String verificationToken);

    boolean isAdminRole();

    boolean updateResetPasswordToken(String token, String email);

    UserTM getByResetPasswordToken(String token);

    void updatePassword(UserTM user, String newPassword);
}
