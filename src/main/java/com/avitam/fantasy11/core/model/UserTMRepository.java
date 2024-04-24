package com.avitam.fantasy11.core.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Locale;

@Repository
public interface UserTMRepository extends JpaRepository<UserTM, Long> {
    UserTM findByUsername(String username);

    List<UserTM> findByLocale(Locale locale);

    UserTM findByResetPasswordToken(String token);
}
