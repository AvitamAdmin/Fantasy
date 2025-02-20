package com.avitam.fantasy11.repository;

import com.avitam.fantasy11.model.Role;
import com.avitam.fantasy11.repository.generic.GenericImportRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("RoleRepository")
public interface RoleRepository extends GenericImportRepository<Role> {

    Optional<Role> findById(String id);

    Role findByRecordId(String recordId);

    Role findByRoleId(String roleId);

    void deleteByRecordId(String recordId);

    List<Role> findByStatusOrderByIdentifier(boolean b);
}
