package com.avitam.fantasy11.core.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Locale;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    List<User> findByLocale(Locale locale);

    User findByResetPasswordToken(String token);
}
