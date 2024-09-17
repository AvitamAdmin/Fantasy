package com.avitam.fantasy11.web.controllers.admin.playerRole;

import com.avitam.fantasy11.api.dto.PlayerDto;
import com.avitam.fantasy11.api.dto.PlayerRoleDto;
import com.avitam.fantasy11.api.service.PlayerRoleService;
import com.avitam.fantasy11.model.*;
import com.avitam.fantasy11.web.controllers.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping
    public PlayerRoleDto getAllModels() {
        PlayerRoleDto playerRoleDto=new PlayerRoleDto();
        playerRoleDto.setPlayerRole(playerRoleRepository.findByStatusOrderByIdentifier(true));
        playerRoleDto.setBaseUrl(ADMIN_PLAYERROLE);
        return playerRoleDto;
    }

    @GetMapping("/edit")
    public PlayerRoleDto editPlayerRole(@RequestBody PlayerDto request) {
        PlayerRoleDto playerRoleDto=new PlayerRoleDto();
        playerRoleDto.setPlayerRole(playerRoleRepository.findByRecordId(request.getRecordId()));
        playerRoleDto.setBaseUrl(ADMIN_PLAYERROLE);
        return playerRoleDto;
    }

    @PostMapping("/edit")
    public PlayerRoleDto handleEdit(@RequestBody PlayerRoleDto request) {
        return playerRoleService.handleEdit(request);
    }

    @GetMapping("/add")
    public PlayerRoleDto addPlayerRole() {
        PlayerRoleDto playerRoleDto=new PlayerRoleDto();
        playerRoleDto.setPlayerRole(playerRoleRepository.findByStatusOrderByIdentifier(true));
        playerRoleDto.setBaseUrl(ADMIN_PLAYERROLE);
        return playerRoleDto;
    }

    @GetMapping("/delete")
    public PlayerRoleDto delete(@RequestBody PlayerRoleDto playerRoleDto) {
        for (String id : playerRoleDto.getRecordId().split(",")) {
            playerRoleRepository.deleteByRecordId(id);
        }
        playerRoleDto.setMessage("Data deleted Successfully");
        playerRoleDto.setBaseUrl(ADMIN_PLAYERROLE);
        return playerRoleDto;
    }
}
