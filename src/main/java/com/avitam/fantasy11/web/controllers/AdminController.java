package com.avitam.fantasy11.web.controllers;

import com.avitam.fantasy11.api.dto.AddressDto;
import com.avitam.fantasy11.api.dto.UserDto;
import com.avitam.fantasy11.core.service.UserService;
import com.avitam.fantasy11.model.RoleRepository;
import com.avitam.fantasy11.core.service.CoreService;
import com.avitam.fantasy11.model.User;
import com.avitam.fantasy11.model.UserRepository;
import com.avitam.fantasy11.validation.UserFormValidator;
import com.avitam.fantasy11.validation.UserValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/user")
public class AdminController extends BaseController{

    public static final String ADMIN_USER="/admin/user";
    Logger logger= LoggerFactory.getLogger(AdminController.class);
    @Autowired
    UserFormValidator userFormValidator;
    @Autowired
    private UserValidator userValidator;
    @Autowired
    private CoreService coreService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("")
    @ResponseBody
    public UserDto getAllUsers(@RequestBody UserDto userDto) {
        Pageable pageable = getPageable(userDto.getPage(), userDto.getSizePerPage(), userDto.getSortDirection(), userDto.getSortField());
        User user = userDto.getUser();
        Page<User> page = isSearchActive(user) != null ? userRepository.findAll(Example.of(user), pageable) : userRepository.findAll(pageable);
        userDto.setUsersList(page.getContent());
        userDto.setTotalPages(page.getTotalPages());
        userDto.setTotalRecords(page.getTotalElements());
        userDto.setBaseUrl(ADMIN_USER);
        return userDto;
    }

    @GetMapping("/get")
    @ResponseBody
    public UserDto getActiveUserList() {
        UserDto userDto = new UserDto();
        userDto.setUsersList(userRepository.findByStatusOrderByIdentifier(true));
        userDto.setBaseUrl(ADMIN_USER);
        return userDto;
    }

    @PostMapping("/getedit")
    @ResponseBody
    public UserDto editUser(@RequestBody UserDto request) {
        UserDto userDto = new UserDto();
        User user = userRepository.findByRecordId(userDto.getRecordId());
        userDto.setUser(user);
        userDto.setBaseUrl(ADMIN_USER);
        return userDto;
    }

    @PostMapping("/edit")
    @ResponseBody
    public UserDto save(@RequestBody UserDto request) {

        userService.save(request);
        return request;
    }

    @GetMapping("/add")
    @ResponseBody
    public UserDto addUser() {
        UserDto userDto=new UserDto();
        userDto.setUsersList(userRepository.findByStatusOrderByIdentifier(true));
        userDto.setBaseUrl(ADMIN_USER);
        return userDto;
    }

    @PostMapping("/delete")
    @ResponseBody
    public UserDto deleteUser(@RequestBody UserDto userDto) {

        for (String id : userDto.getRecordId().split(",")) {
            userRepository.deleteByRecordId(id);
        }
        userDto.setBaseUrl(ADMIN_USER);
        userDto.setMessage("Data Deleted Successfully");
        return userDto;
    }


}
