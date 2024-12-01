package com.avitam.fantasy11.web.controllers.admin.userWinnings;

import com.avitam.fantasy11.api.dto.UserWinningsDto;
import com.avitam.fantasy11.api.service.UserWinningsService;
import com.avitam.fantasy11.core.service.CoreService;
import com.avitam.fantasy11.model.UserWinnings;
import com.avitam.fantasy11.repository.MatchesRepository;
import com.avitam.fantasy11.repository.UserRepository;
import com.avitam.fantasy11.repository.UserTeamsRepository;
import com.avitam.fantasy11.repository.UserWinningsRepository;
import com.avitam.fantasy11.web.controllers.BaseController;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


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
    public UserWinningsDto getAll(UserWinningsDto userWinningsDto){

        Pageable pageable = getPageable(userWinningsDto.getPage(), userWinningsDto.getSizePerPage(), userWinningsDto.getSortDirection(), userWinningsDto.getSortField());
        UserWinnings userWinnings = userWinningsDto.getUserWinnings();
        Page<UserWinnings> page = isSearchActive(userWinnings)!=null ? userWinningsRepository.findAll(Example.of(userWinnings),pageable) : userWinningsRepository.findAll(pageable);
        userWinningsDto.setUserWinningsList(page.getContent());
        userWinningsDto.setTotalPages(page.getTotalPages());
        userWinningsDto.setTotalRecords(page.getTotalElements());
        userWinningsDto.setBaseUrl(ADMIN_USERWINNINGS);
        return userWinningsDto;
    }

    @GetMapping("/get")
    @ResponseBody
    public UserWinningsDto getActiveUserWinnings(){
        UserWinningsDto userWinningsDto = new UserWinningsDto();
        userWinningsDto.setUserWinningsList(userWinningsRepository.findByStatusOrderByIdentifier(true));
        userWinningsDto.setBaseUrl(ADMIN_USERWINNINGS);
        return userWinningsDto;
    }

    @PostMapping("/getedit")
    @ResponseBody
    public UserWinningsDto editUserWinnings(@RequestBody UserWinningsDto request){

        UserWinningsDto userWinningsDto = new UserWinningsDto();
        UserWinnings userWinnings = userWinningsRepository.findByRecordId(request.getRecordId());
        userWinningsDto.setUserWinnings(userWinnings);
        userWinningsDto.setBaseUrl(ADMIN_USERWINNINGS);

        return userWinningsDto;
    }

    @PostMapping("/edit")
    @ResponseBody
    public UserWinningsDto handleEdit(@RequestBody UserWinningsDto request) {

        return userWinningsService.handleEdit(request);
    }

    @GetMapping("/add")
    @ResponseBody
    public UserWinningsDto addUserWinnings() {
        UserWinningsDto userWinningsDto = new UserWinningsDto();
        userWinningsDto.setUserWinningsList(userWinningsRepository.findByStatusOrderByIdentifier(true));
        userWinningsDto.setBaseUrl(ADMIN_USERWINNINGS);

        return userWinningsDto;
    }

    @PostMapping("/delete")
    @ResponseBody
    public UserWinningsDto deleteUserWinnings(@RequestBody UserWinningsDto userWinningsDto) {
        for (String id : userWinningsDto.getRecordId().split(",")) {
            userWinningsRepository.deleteByRecordId(id);
        }
        userWinningsDto.setMessage("Data deleted successfully");
        userWinningsDto.setBaseUrl(ADMIN_USERWINNINGS);
        return userWinningsDto;
    }
   }
