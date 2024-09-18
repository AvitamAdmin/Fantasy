package com.avitam.fantasy11.api.service.impl;

import com.avitam.fantasy11.api.dto.RoleDto;
import com.avitam.fantasy11.api.dto.ScriptDto;
import com.avitam.fantasy11.api.service.RoleService;
import com.avitam.fantasy11.core.service.CoreService;
import com.avitam.fantasy11.model.Role;
import com.avitam.fantasy11.model.RoleRepository;
import com.avitam.fantasy11.model.Script;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CoreService coreService;

    public static final String ADMIN_ROLE = "/admin/role";
    @Override
    public Role findByRecordId(String recordId) {
        return roleRepository.findByRecordId(recordId);
    }

    @Override
    public void deleteByRecordId(String recordId) {
        roleRepository.deleteByRecordId(recordId);
    }

    @Override
    public void updateByRecordId(String recordId) {
        Role roleOptional=roleRepository.findByRecordId(recordId);
        if(roleOptional!=null)
        {
            roleRepository.save(roleOptional);
        }

    }

    @Override
    public RoleDto handleEdit(RoleDto request) {
        {
            RoleDto roleDto = new RoleDto();
            Role role=null;
            if (request.getRecordId()!=null) {
                Role requestData=request.getRole();
                role=roleRepository.findByRecordId(request.getRecordId());
                modelMapper.map(requestData,role);
            }else {
                role=request.getRole();
                role.setCreator(coreService.getCurrentUser().getUsername());
                role.setCreationTime(new Date());
                roleRepository.save(role);
            }
            role.setLastModified(new Date());
            if (request.getRecordId()==null){
                role.setRecordId(String.valueOf(role.getId().getTimestamp()));
            }
            roleRepository.save(role);
            roleDto.setRole(role);
            roleDto.setBaseUrl(ADMIN_ROLE);
            return roleDto;
        }

    }
    }

