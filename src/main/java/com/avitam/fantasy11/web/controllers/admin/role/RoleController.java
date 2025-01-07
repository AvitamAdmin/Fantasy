package com.avitam.fantasy11.web.controllers.admin.role;

import com.avitam.fantasy11.api.dto.RoleDto;
import com.avitam.fantasy11.api.dto.RoleWsDto;
import com.avitam.fantasy11.api.service.RoleService;
import com.avitam.fantasy11.model.Role;
import com.avitam.fantasy11.repository.NodeRepository;
import com.avitam.fantasy11.repository.RoleRepository;
import com.avitam.fantasy11.web.controllers.BaseController;
import org.apache.commons.collections4.CollectionUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/role")
public class RoleController extends BaseController {
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private NodeRepository nodeRepository;
    @Autowired
    private RoleService roleService;
    @Autowired
    private ModelMapper modelMapper;


    public static final String ADMIN_ROLE = "/admin/role";
    @PostMapping
    @ResponseBody
    public RoleWsDto getAllRoles(@RequestBody RoleWsDto roleWsDto) {
        Pageable pageable=getPageable(roleWsDto.getPage(),roleWsDto.getSizePerPage(),roleWsDto.getSortDirection(),roleWsDto.getSortField());
        RoleDto roleDto = CollectionUtils.isNotEmpty(roleWsDto.getRoleDtoList()) ? roleWsDto.getRoleDtoList().get(0) : new RoleDto();
        Role role=modelMapper.map(roleDto,Role.class);
        Page<Role> page=isSearchActive(role) !=null ? roleRepository.findAll(Example.of(role),pageable) : roleRepository.findAll(pageable);
        roleWsDto.setRoleDtoList(modelMapper.map(page.getContent(), List.class));
        roleWsDto.setBaseUrl(ADMIN_ROLE);
        roleWsDto.setTotalPages(page.getTotalPages());
        roleWsDto.setTotalRecords(page.getTotalElements());
        return roleWsDto;
    }

    @GetMapping("/get")
    @ResponseBody
    public RoleWsDto getActiveRole(){
        RoleWsDto roleWsDto=new RoleWsDto();
        roleWsDto.setRoleDtoList(modelMapper.map(roleRepository.findByStatusOrderByIdentifier(true), List.class));
        roleWsDto.setBaseUrl(ADMIN_ROLE);
        return roleWsDto;
    }

    @PostMapping("/getedit")
    @ResponseBody
    public RoleWsDto editRole (@RequestBody RoleWsDto request) {
        RoleWsDto roleWsDto = new RoleWsDto();
        roleWsDto.setBaseUrl(ADMIN_ROLE);
        Role role= roleRepository.findByRecordId(request.getRoleDtoList().get(0).getRecordId());
        if(role != null){
            roleWsDto.setRoleDtoList(List.of(modelMapper.map(role, RoleDto.class)));
        }
        return roleWsDto;
    }

    @PostMapping("/edit")
    @ResponseBody
    public  RoleWsDto handleEdit(@RequestBody RoleWsDto request) {

        return roleService.handleEdit(request);
    }


    @PostMapping("/delete")
    @ResponseBody
    public RoleWsDto deleteRole(@RequestBody RoleWsDto roleWsDto) {
        for (RoleDto roleDto : roleWsDto.getRoleDtoList()){
            roleRepository.deleteByRecordId(roleDto.getRecordId());
        }
        roleWsDto.setMessage("Data deleted Successfully");
        roleWsDto.setBaseUrl(ADMIN_ROLE);
        return roleWsDto;
    }
}

