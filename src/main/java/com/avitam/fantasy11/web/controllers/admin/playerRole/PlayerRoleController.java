package com.avitam.fantasy11.web.controllers.admin.playerRole;

import com.avitam.fantasy11.api.dto.PlayerDto;
import com.avitam.fantasy11.api.dto.PlayerRoleDto;
import com.avitam.fantasy11.api.service.PlayerRoleService;
import com.avitam.fantasy11.model.*;
import com.avitam.fantasy11.web.controllers.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/admin/playerRole")
public class PlayerRoleController extends BaseController {

    @Autowired
    private PlayerRoleRepository playerRoleRepository;
    @Autowired
    private PlayerRoleService playerRoleService;

    private static final String ADMIN_PLAYERROLE="/admin/playerRole";

    @PostMapping
    @ResponseBody
    public PlayerRoleDto getAllPlayerRole(@RequestBody PlayerRoleDto playerRoleDto){
        Pageable pageable=getPageable(playerRoleDto.getPage(),playerRoleDto.getSizePerPage(),playerRoleDto.getSortDirection(),playerRoleDto.getSortField());
        PlayerRole playerRole=playerRoleDto.getPlayerRole();
        Page<PlayerRole> page=isSearchActive(playerRole)!=null ? playerRoleRepository.findAll(Example.of(playerRole),pageable): playerRoleRepository.findAll(pageable);
        playerRoleDto.setPlayerRoleList(page.getContent());
        playerRoleDto.setBaseUrl(ADMIN_PLAYERROLE);
        playerRoleDto.setTotalPages(page.getTotalPages());
        playerRoleDto.setTotalRecords(page.getTotalElements());
        return playerRoleDto;
    }

    @GetMapping("/get")
    @ResponseBody
    public PlayerRoleDto getActivePlayerRole() {
        PlayerRoleDto playerRoleDto=new PlayerRoleDto();
        playerRoleDto.setPlayerRoleList(playerRoleRepository.findByStatusOrderByIdentifier(true));
        playerRoleDto.setBaseUrl(ADMIN_PLAYERROLE);
        return playerRoleDto;
    }

    @PostMapping("/getedit")
    @ResponseBody
    public PlayerRoleDto editPlayerRole(@RequestBody PlayerDto request) {
        PlayerRoleDto playerRoleDto=new PlayerRoleDto();
        PlayerRole playerRole=playerRoleRepository.findByRecordId(request.getRecordId());
        playerRoleDto.setPlayerRole(playerRole);
        playerRoleDto.setBaseUrl(ADMIN_PLAYERROLE);
        return playerRoleDto;
    }

    @PostMapping("/edit")
    @ResponseBody
    public PlayerRoleDto handleEdit(@RequestBody PlayerRoleDto request) {
        return playerRoleService.handleEdit(request);
    }

    @GetMapping("/add")
    @ResponseBody
    public PlayerRoleDto addPlayerRole() {
        PlayerRoleDto playerRoleDto=new PlayerRoleDto();
        playerRoleDto.setPlayerRoleList(playerRoleRepository.findByStatusOrderByIdentifier(true));
        playerRoleDto.setBaseUrl(ADMIN_PLAYERROLE);
        return playerRoleDto;
    }

    @PostMapping("/delete")
    @ResponseBody
    public PlayerRoleDto delete(@RequestBody PlayerRoleDto playerRoleDto) {
        for (String id : playerRoleDto.getRecordId().split(",")) {
            playerRoleRepository.deleteByRecordId(id);
        }
        playerRoleDto.setMessage("Data deleted successfully");
        playerRoleDto.setBaseUrl(ADMIN_PLAYERROLE);
        return playerRoleDto;
    }
}
