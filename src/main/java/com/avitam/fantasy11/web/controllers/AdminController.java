package com.avitam.fantasy11.web.controllers;

import com.avitam.fantasy11.api.dto.UserDto;
import com.avitam.fantasy11.api.dto.UserWsDto;
import com.avitam.fantasy11.core.service.UserService;
import com.avitam.fantasy11.model.User;
import com.avitam.fantasy11.repository.UserRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/user")
public class AdminController extends BaseController{

    public static final String ADMIN_USER="/admin/user";
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("")
    @ResponseBody
    public UserWsDto getAllUsers(@RequestBody UserWsDto userWsDto) {
        Pageable pageable = getPageable(userWsDto.getPage(), userWsDto.getSizePerPage(), userWsDto.getSortDirection(), userWsDto.getSortField());
        UserDto userDto= CollectionUtils.isNotEmpty(userWsDto.getUserDtoList())? userWsDto.getUserDtoList().get(0) : new UserDto();
        User user = modelMapper.map(userDto, User.class);
        Page<User> page = isSearchActive(user) != null ? userRepository.findAll(Example.of(user), pageable) : userRepository.findAll(pageable);
        userWsDto.setUserDtoList(modelMapper.map(page.getContent(),List.class));
        userWsDto.setTotalPages(page.getTotalPages());
        userWsDto.setTotalRecords(page.getTotalElements());
        userWsDto.setBaseUrl(ADMIN_USER);
        return userWsDto;
    }

    @GetMapping("/get")
    @ResponseBody
    public UserWsDto getActiveUserList() {
        UserWsDto userWsDto = new UserWsDto();
        userWsDto.setUserDtoList(modelMapper.map(userRepository.findByStatusOrderByIdentifier(true),List.class));
        userWsDto.setBaseUrl(ADMIN_USER);
        return userWsDto;
    }

    @PostMapping("/getedit")
    @ResponseBody
    public UserWsDto editUser(@RequestBody UserWsDto request) {
        UserWsDto userWsDto = new UserWsDto();
        userWsDto.setUserDtoList(modelMapper.map(userRepository.findByRecordId(request.getRecordId()),List.class));
        userWsDto.setBaseUrl(ADMIN_USER);
        return userWsDto;
    }

    @PostMapping("/edit")
    @ResponseBody
    public UserWsDto save(@RequestBody UserWsDto request) {
        userService.save(request);
        return request;
    }

    @GetMapping("/add")
    @ResponseBody
    public UserWsDto addUser() {
        UserWsDto userWsDto=new UserWsDto();
        userWsDto.setUserDtoList(modelMapper.map(userRepository.findByStatusOrderByIdentifier(true),List.class));
        userWsDto.setBaseUrl(ADMIN_USER);
        return userWsDto;
    }

    @PostMapping("/delete")
    @ResponseBody
    public UserWsDto deleteUser(@RequestBody UserWsDto userWsDto) {

        for (UserDto data : userWsDto.getUserDtoList()) {
            userRepository.deleteByRecordId(data.getRecordId());
        }
        userWsDto.setBaseUrl(ADMIN_USER);
        userWsDto.setMessage("Data Deleted Successfully");
        return userWsDto;
    }

//    @GetMapping("/generate-otp")
//    public ResponseEntity<UserDto> generateOtp(@RequestParam String email) {
//        UserDto userDto = userService.generateOtpForUser(email);
//        return ResponseEntity.ok(userDto);
//    }

}
