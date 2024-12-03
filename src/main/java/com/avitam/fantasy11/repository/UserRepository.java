package com.avitam.fantasy11.repository;

import com.avitam.fantasy11.model.User;
import com.avitam.fantasy11.repository.generic.GenericImportRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository("UserRepository")
public interface UserRepository extends GenericImportRepository<User> {
    User findByUsername(String email);

    User findByResetPasswordToken(String token);

    User findByEmail(String email);

    User findById(String id);

    User findByRecordId(String recordId);

    void deleteByRecordId(String recordId);

    User findByMobileNumber(String mobileNumber);

    List<User> findByStatusOrderByIdentifier(boolean b);


}
