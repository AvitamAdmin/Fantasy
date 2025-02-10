package com.avitam.fantasy11.web.controllers.admin.userteams;

import com.avitam.fantasy11.api.dto.SearchDto;
import com.avitam.fantasy11.api.dto.UserTeamsDto;
import com.avitam.fantasy11.api.dto.UserTeamsWsDto;
import com.avitam.fantasy11.api.service.UserTeamsService;
import com.avitam.fantasy11.model.LeaderBoard;
import com.avitam.fantasy11.model.UserTeams;
import com.avitam.fantasy11.repository.UserTeamsRepository;
import com.avitam.fantasy11.web.controllers.BaseController;
import org.apache.commons.collections4.CollectionUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/userTeams")
public class UserTeamsController extends BaseController {

    @Autowired
    private UserTeamsRepository userTeamsRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserTeamsService userTeamsService;

    public static final String ADMIN_USERTEAMS = "/admin/userTeams";

    @PostMapping
    @ResponseBody
    public UserTeamsWsDto getAllUserTeams(@RequestBody UserTeamsWsDto userTeamsWsDto) {

        Pageable pageable = getPageable(userTeamsWsDto.getPage(), userTeamsWsDto.getSizePerPage(), userTeamsWsDto.getSortDirection(), userTeamsWsDto.getSortField());
        UserTeamsDto userTeamsDto = CollectionUtils.isNotEmpty(userTeamsWsDto.getUserTeamsDtoList()) ? userTeamsWsDto.getUserTeamsDtoList().get(0) : new UserTeamsDto();
        UserTeams userTeams = modelMapper.map(userTeamsDto, UserTeams.class);
        Page<UserTeams> page = isSearchActive(userTeams) != null ? userTeamsRepository.findAll(Example.of(userTeams), pageable) : userTeamsRepository.findAll(pageable);
        userTeamsWsDto.setUserTeamsDtoList(modelMapper.map(page.getContent(), List.class));
        userTeamsWsDto.setTotalPages(page.getTotalPages());
        userTeamsWsDto.setTotalRecords(page.getTotalElements());
        userTeamsWsDto.setBaseUrl(ADMIN_USERTEAMS);
        return userTeamsWsDto;
    }


    @GetMapping("/get")
    @ResponseBody
    public UserTeamsWsDto getActiveUserTeams() {
        UserTeamsWsDto userTeamsWsDto = new UserTeamsWsDto();
        userTeamsWsDto.setUserTeamsDtoList(modelMapper.map(userTeamsRepository.findByStatusOrderByIdentifier(true), List.class));
        userTeamsWsDto.setBaseUrl(ADMIN_USERTEAMS);
        return userTeamsWsDto;
    }


    @PostMapping("/getedit")
    @ResponseBody
    public UserTeamsWsDto editUserTeams(@RequestBody UserTeamsWsDto request) {
        List<UserTeamsDto> userTeamsDtos = request.getUserTeamsDtoList();
        UserTeams userTeams = userTeamsRepository.findByRecordId(request.getUserTeamsDtoList().get(0).getRecordId());
        request.setUserTeamsDtoList(List.of(modelMapper.map(userTeams, UserTeamsDto.class)));
        request.setBaseUrl(ADMIN_USERTEAMS);
        return request;
    }


    @PostMapping("/edit")
    @ResponseBody
    public UserTeamsWsDto handleEdit(@RequestBody UserTeamsWsDto request) {

        return userTeamsService.handleEdit(request);
    }


    @PostMapping("/delete")
    @ResponseBody
    public UserTeamsWsDto deleteUserTeams(@RequestBody UserTeamsWsDto userTeamsWsDto) {
        for (UserTeamsDto id : userTeamsWsDto.getUserTeamsDtoList()) {
            userTeamsRepository.deleteByRecordId(id.getRecordId());
        }
        userTeamsWsDto.setMessage("Data deleted successfully");
        userTeamsWsDto.setBaseUrl(ADMIN_USERTEAMS);
        return userTeamsWsDto;
    }
    @GetMapping("/getAdvancedSearch")
    @ResponseBody

    public List<SearchDto> getSearchAttributes() {
        return getGroupedParentAndChildAttributes(new UserTeams());
    }


}