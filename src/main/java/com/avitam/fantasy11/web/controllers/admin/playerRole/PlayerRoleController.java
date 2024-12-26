package com.avitam.fantasy11.web.controllers.admin.playerRole;

import com.avitam.fantasy11.api.dto.*;
import com.avitam.fantasy11.api.service.PlayerRoleService;
import com.avitam.fantasy11.model.PlayerRole;
import com.avitam.fantasy11.repository.PlayerRoleRepository;
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
@RequestMapping("/admin/playerRole")
public class PlayerRoleController extends BaseController {

    @Autowired
    private PlayerRoleRepository playerRoleRepository;
    @Autowired
    private PlayerRoleService playerRoleService;
    @Autowired
    private ModelMapper modelMapper;

    private static final String ADMIN_PLAYERROLE = "/admin/playerRole";

    @PostMapping
    @ResponseBody
    public PlayerRoleWsDto getAllPlayerRole(@RequestBody PlayerRoleWsDto playerRoleWsDto) {
        Pageable pageable = getPageable(playerRoleWsDto.getPage(), playerRoleWsDto.getSizePerPage(), playerRoleWsDto.getSortDirection(), playerRoleWsDto.getSortField());
        CommonDto playerRoleDto = CollectionUtils.isNotEmpty(playerRoleWsDto.getPlayerRoleDtoList()) ? playerRoleWsDto.getPlayerRoleDtoList().get(0) : new PlayerRoleDto();
        PlayerRole playerRole = modelMapper.map(playerRoleDto, PlayerRole.class);
        Page<PlayerRole> page = isSearchActive(playerRole) != null ? playerRoleRepository.findAll(Example.of(playerRole), pageable) : playerRoleRepository.findAll(pageable);
        playerRoleWsDto.setPlayerRoleDtoList(modelMapper.map(page.getContent(), List.class));
        playerRoleWsDto.setBaseUrl(ADMIN_PLAYERROLE);
        playerRoleWsDto.setTotalPages(page.getTotalPages());
        playerRoleWsDto.setTotalRecords(page.getTotalElements());
        return playerRoleWsDto;
    }

    @GetMapping("/get")
    @ResponseBody
    public PlayerRoleWsDto getActivePlayerRole() {
        PlayerRoleWsDto playerRoleWsDto = new PlayerRoleWsDto();
        playerRoleWsDto.setPlayerRoleDtoList(modelMapper.map(playerRoleRepository.findByStatusOrderByIdentifier(true), List.class));
        playerRoleWsDto.setBaseUrl(ADMIN_PLAYERROLE);
        return playerRoleWsDto;
    }

    @PostMapping("/getedit")
    @ResponseBody
    public PlayerRoleWsDto editPlayerRole(@RequestBody PlayerWsDto request) {
        PlayerRoleWsDto playerRoleWsDto = new PlayerRoleWsDto();
        playerRoleWsDto.setBaseUrl(ADMIN_PLAYERROLE);
        PlayerRole playerRole = playerRoleRepository.findByRecordId(request.getPlayerDtoList().get(0).getRecordId());
        if (playerRole != null) {
            playerRoleWsDto.setPlayerRoleDtoList(List.of(modelMapper.map(playerRole, PlayerRoleDto.class)));
        }
        return playerRoleWsDto;
    }

    @PostMapping("/edit")
    @ResponseBody
    public PlayerRoleWsDto handleEdit(@RequestBody PlayerRoleWsDto request) {
        return playerRoleService.handleEdit(request);
    }

    @GetMapping("/add")
    @ResponseBody
    public PlayerRoleWsDto addPlayerRole() {
        PlayerRoleWsDto playerRoleWsDto = new PlayerRoleWsDto();
        playerRoleWsDto.setPlayerRoleDtoList(modelMapper.map(playerRoleRepository.findByStatusOrderByIdentifier(true), List.class));
        playerRoleWsDto.setBaseUrl(ADMIN_PLAYERROLE);
        return playerRoleWsDto;
    }

    @PostMapping("/delete")
    @ResponseBody
    public PlayerRoleWsDto delete(@RequestBody PlayerRoleWsDto playerRoleWsDto) {
        for (PlayerRoleDto playerRoleDto : playerRoleWsDto.getPlayerRoleDtoList()) {
            playerRoleRepository.deleteByRecordId(playerRoleDto.getRecordId());
        }
        playerRoleWsDto.setMessage("Data deleted successfully");
        playerRoleWsDto.setBaseUrl(ADMIN_PLAYERROLE);
        return playerRoleWsDto;
    }
}
