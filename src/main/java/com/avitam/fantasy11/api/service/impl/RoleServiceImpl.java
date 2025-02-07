package com.avitam.fantasy11.api.service.impl;

import com.avitam.fantasy11.api.dto.RoleDto;
import com.avitam.fantasy11.api.dto.RoleWsDto;
import com.avitam.fantasy11.api.service.BaseService;
import com.avitam.fantasy11.api.service.RoleService;
import com.avitam.fantasy11.model.Node;
import com.avitam.fantasy11.model.Role;
import com.avitam.fantasy11.repository.EntityConstants;
import com.avitam.fantasy11.repository.NodeRepository;
import com.avitam.fantasy11.repository.RoleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private BaseService baseService;
    @Autowired
    private NodeRepository nodeRepository;

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
        Role roleOptional = roleRepository.findByRecordId(recordId);
        if (roleOptional != null) {
            roleRepository.save(roleOptional);
        }

    }
    @Override
    public RoleWsDto  handleEdit(RoleWsDto request) {
        List<RoleDto> roleDtos = request.getRoleDtoList();
        List<Role> roles = new ArrayList<>();
        Role role = null;
        for (RoleDto roleDto : roleDtos) {
            if (roleDto.getRecordId() != null) {
                Role requestData = modelMapper.map(roleDto, Role.class);
                role = roleRepository.findByRecordId(roleDto.getRecordId());
                modelMapper.map(requestData, role);
                role.setLastModified(new Date());
                roleRepository.save(role);
                request.setMessage("Data updated Successfully");
            } else {
                if (baseService.validateIdentifier(EntityConstants.ROLE, roleDto.getIdentifier()) != null) {
                    request.setSuccess(false);
                    request.setMessage("Identifier already present");
                    return request;
                }
                role = modelMapper.map(roleDto, Role.class);

                role.setStatus(true);
                baseService.populateCommonData(role);
                Set<Node> nodes = new HashSet<>();
                for (Node node : roleDto.getPermissions()) {
                    Node node1 = nodeRepository.findByRecordId(node.getRecordId());
                    nodes.add(node1);
                }
                role.setPermissions(nodes);
                roleRepository.save(role);
                if (role.getRecordId() == null) {
                    role.setRecordId(String.valueOf(role.getId().getTimestamp()));
                }
                roleRepository.save(role);
                request.setMessage("Data added Successfully");
            }
            request.setBaseUrl(ADMIN_ROLE);
            roles.add(role);
        }
        request.setRoleDtoList(modelMapper.map(roles, List.class));
        return request;

    }
}

