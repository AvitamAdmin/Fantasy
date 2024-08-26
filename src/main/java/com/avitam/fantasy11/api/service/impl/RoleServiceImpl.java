package com.avitam.fantasy11.api.service.impl;

import com.avitam.fantasy11.api.service.RoleService;
import com.avitam.fantasy11.model.Role;
import com.avitam.fantasy11.model.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;
    @Override
    public Optional<Role> findByRecordId(String recordId) {
        return roleRepository.findByRecordId(recordId);
    }

    @Override
    public void deleteByRecordId(String recordId) {
        roleRepository.deleteByRecordId(recordId);
    }

    @Override
    public void updateByRecordId(String recordId) {
        Optional<Role> roleOptional=roleRepository.findByRecordId(recordId);
        roleOptional.ifPresent(role -> roleRepository.save(role));
    }
}
