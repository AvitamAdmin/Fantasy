package com.avitam.fantasy11.web.controllers.admin.role;

import com.avitam.fantasy11.api.dto.RoleDto;
import com.avitam.fantasy11.api.service.RoleService;
import com.avitam.fantasy11.model.Role;
import com.avitam.fantasy11.repository.NodeRepository;
import com.avitam.fantasy11.repository.RoleRepository;
import com.avitam.fantasy11.web.controllers.BaseController;
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


    public static final String ADMIN_ROLE = "/admin/role";
    @PostMapping
    @ResponseBody
    public RoleDto getAllRoles(@RequestBody RoleDto roleDto) {
        Pageable pageable=getPageable(roleDto.getPage(),roleDto.getSizePerPage(),roleDto.getSortDirection(),roleDto.getSortField());
        Role role=roleDto.getRole();
        Page<Role> page=isSearchActive(role) !=null ? roleRepository.findAll(Example.of(role),pageable) : roleRepository.findAll(pageable);
        roleDto.setRoles(page.getContent());
        roleDto.setBaseUrl(ADMIN_ROLE);
        roleDto.setTotalPages(page.getTotalPages());
        roleDto.setTotalRecords(page.getTotalElements());
        return roleDto;
    }

    @GetMapping("/get")
    @ResponseBody
    public RoleDto getActiveRole(){
        RoleDto roleDto=new RoleDto();
        roleDto.setRoles(roleRepository.findByStatusOrderByIdentifier(true));
        roleDto.setBaseUrl(ADMIN_ROLE);
        return roleDto;
    }

    @GetMapping("/migrate")
    public void migrate()
    {
        List<Role> roleList=roleRepository.findAll();
        for(Role role:roleList)
        {
            role.setRecordId(String.valueOf(role.getId().getTimestamp()));
            roleRepository.save(role);

        }
    }

    @PostMapping("/getedit")
    @ResponseBody
    public RoleDto edit (@RequestBody RoleDto request) {
        RoleDto roleDto = new RoleDto();
        roleDto.setBaseUrl(ADMIN_ROLE);
        Role role = roleRepository.findByRecordId(request.getRecordId());
        roleDto.setRole(role);
        return roleDto;
    }

    @PostMapping("/edit")
    @ResponseBody
    public  RoleDto handleEdit(@RequestBody RoleDto request) {

        return roleService.handleEdit(request);
    }


    @GetMapping("/add")
    @ResponseBody
    public RoleDto add() {
        RoleDto roleDto = new RoleDto();
        roleDto.setRoles(roleRepository.findByStatusOrderByIdentifier(true));
        roleDto.setBaseUrl(ADMIN_ROLE);
        return roleDto;
    }


    @PostMapping("/delete")
    @ResponseBody
    public RoleDto delete(@RequestBody RoleDto roleDto) {
        for (String id : roleDto.getRecordId().split(",")) {
            roleRepository.deleteByRecordId(id);
        }
        roleDto.setMessage("Data deleted Successfully");
        roleDto.setBaseUrl(ADMIN_ROLE);
        return roleDto;
    }
}

