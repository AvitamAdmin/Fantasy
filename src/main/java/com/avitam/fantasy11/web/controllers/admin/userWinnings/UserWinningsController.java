package com.avitam.fantasy11.web.controllers.admin.userWinnings;

import com.avitam.fantasy11.api.dto.MainContestDto;
import com.avitam.fantasy11.api.dto.UserWinningsDto;
import com.avitam.fantasy11.api.dto.UserWinningsWsDto;
import com.avitam.fantasy11.api.service.UserWinningsService;
import com.avitam.fantasy11.core.service.CoreService;
import com.avitam.fantasy11.model.MainContest;
import com.avitam.fantasy11.model.UserWinnings;
import com.avitam.fantasy11.repository.MatchesRepository;
import com.avitam.fantasy11.repository.UserRepository;
import com.avitam.fantasy11.repository.UserTeamsRepository;
import com.avitam.fantasy11.repository.UserWinningsRepository;
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
@RequestMapping("/admin/userWinnings")
public class UserWinningsController extends BaseController {

    @Autowired
    private UserWinningsRepository userWinningsRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserTeamsRepository userTeamsRepository;
    @Autowired
    private MatchesRepository matchesRepository;
    @Autowired
    private UserWinningsService userWinningsService;
    @Autowired
    private CoreService coreService;
    @Autowired
    private ModelMapper modelMapper;

    public static final String ADMIN_USERWINNINGS = "/admin/userWinnings";

    @PostMapping
    @ResponseBody
    public UserWinningsWsDto getAll(@RequestBody UserWinningsWsDto userWinningsWsDto){

        Pageable pageable = getPageable(userWinningsWsDto.getPage(), userWinningsWsDto.getSizePerPage(), userWinningsWsDto.getSortDirection(), userWinningsWsDto.getSortField());
        UserWinningsDto userWinningsDto= CollectionUtils.isNotEmpty(userWinningsWsDto.getUserWinningsDtoList())?userWinningsWsDto.getUserWinningsDtoList() .get(0) : new UserWinningsDto() ;
        UserWinnings  userWinnings = modelMapper.map(userWinningsWsDto, UserWinnings.class);
        Page<UserWinnings> page = isSearchActive(userWinnings)!=null ? userWinningsRepository.findAll(Example.of(userWinnings),pageable) : userWinningsRepository.findAll(pageable);
        userWinningsWsDto.setUserWinningsDtoList(modelMapper.map(page.getContent(),List.class));
        userWinningsWsDto.setTotalPages(page.getTotalPages());
        userWinningsWsDto.setTotalRecords(page.getTotalElements());
        userWinningsWsDto.setBaseUrl(ADMIN_USERWINNINGS);
        return userWinningsWsDto;
    }

    @GetMapping("/get")
    @ResponseBody
    public UserWinningsWsDto getActiveUserWinnings(){
        UserWinningsWsDto userWinningsWsDto = new UserWinningsWsDto();
        userWinningsWsDto.setUserWinningsDtoList(modelMapper.map(userWinningsRepository.findByStatusOrderByIdentifier(true), List.class));
        userWinningsWsDto.setBaseUrl(ADMIN_USERWINNINGS);
        return userWinningsWsDto;
    }

    @PostMapping("/getedit")
    @ResponseBody
    public UserWinningsWsDto editUserWinnings(@RequestBody UserWinningsWsDto request){
        UserWinningsWsDto userWinningsWsDto = new UserWinningsWsDto();
        UserWinnings userWinnings = userWinningsRepository.findByRecordId(request.getRecordId());
        userWinningsWsDto.setUserWinningsDtoList((List<UserWinningsDto>) userWinnings);
        userWinningsWsDto.setBaseUrl(ADMIN_USERWINNINGS);
        return userWinningsWsDto;
    }

    @PostMapping("/edit")
    @ResponseBody
    public UserWinningsWsDto handleEdit(@RequestBody UserWinningsWsDto request) {

        return userWinningsService.handleEdit(request);
    }

    @GetMapping("/add")
    @ResponseBody
    public UserWinningsWsDto addUserWinnings() {
        UserWinningsWsDto userWinningsWsDto = new UserWinningsWsDto();
        userWinningsWsDto.setUserWinningsDtoList(modelMapper.map(userWinningsRepository.findByStatusOrderByIdentifier(true),List.class));
        userWinningsWsDto.setBaseUrl(ADMIN_USERWINNINGS);
        return userWinningsWsDto;
    }

    @PostMapping("/delete")
    @ResponseBody
    public UserWinningsWsDto deleteUserWinnings(@RequestBody UserWinningsWsDto userWinningsWsDto) {
        for (String id : userWinningsWsDto.getRecordId().split(",")) {
            userWinningsRepository.deleteByRecordId(id);
        }
        userWinningsWsDto.setMessage("Data deleted successfully");
        userWinningsWsDto.setBaseUrl(ADMIN_USERWINNINGS);
        return userWinningsWsDto;
    }
   }
