package com.avitam.fantasy11.web.controllers.admin.player;

import com.avitam.fantasy11.api.dto.*;
import com.avitam.fantasy11.api.service.PlayerService;
import com.avitam.fantasy11.model.Player;
import com.avitam.fantasy11.repository.PlayerRepository;
import com.avitam.fantasy11.web.controllers.BaseController;
import org.apache.commons.collections4.CollectionUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/admin/player")
public class PlayerController extends BaseController {

    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private PlayerService playerService;
    @Autowired
    private ModelMapper modelMapper;
    private static final String ADMIN_PlAYER = "/admin/player";

    @PostMapping
    @ResponseBody
    public PlayerWsDto getAllPlayers(@RequestBody PlayerWsDto playerWsDto) {
        Pageable pageable = getPageable(playerWsDto.getPage(), playerWsDto.getSizePerPage(), playerWsDto.getSortDirection(), playerWsDto.getSortField());
        PlayerDto playerDto = CollectionUtils.isNotEmpty(playerWsDto.getPlayerDtoList()) ? playerWsDto.getPlayerDtoList().get(0) : new PlayerDto();
        Player player = modelMapper.map(playerDto, Player.class);
        Page<Player> page = isSearchActive(player) != null ? playerRepository.findAll(Example.of(player), pageable) : playerRepository.findAll(pageable);
        playerWsDto.setPlayerDtoList(modelMapper.map(page.getContent(), List.class));
        playerWsDto.setBaseUrl(ADMIN_PlAYER);
        playerWsDto.setTotalPages(page.getTotalPages());
        playerWsDto.setTotalRecords(page.getTotalElements());
        return playerWsDto;
    }

    @GetMapping("/get")
    @ResponseBody
    public PlayerWsDto getActivePlayers() {
        PlayerWsDto playerWsDto = new PlayerWsDto();
        Pageable pageable = getPageable(playerWsDto.getPage(), playerWsDto.getSizePerPage(), playerWsDto.getSortDirection(), playerWsDto.getSortField());
        PlayerDto playerDto = CollectionUtils.isNotEmpty(playerWsDto.getPlayerDtoList()) ? playerWsDto.getPlayerDtoList().get(0) : new PlayerDto();
        Player player = modelMapper.map(playerDto, Player.class);
        playerWsDto.setPlayerDtoList(modelMapper.map(playerRepository.findByStatusOrderByIdentifier(true), List.class));
        Page<Player> page = isSearchActive(player) == null ? playerRepository.findAll(Example.of(player), pageable) : (Page<Player>) playerRepository.findByStatusOrderByIdentifier(true);
        playerWsDto.setBaseUrl(ADMIN_PlAYER);
        playerWsDto.setTotalPages(page.getTotalPages());
        playerWsDto.setTotalRecords(page.getTotalElements());
        return playerWsDto;
    }

    @PostMapping("/getedit")
    @ResponseBody
    public PlayerWsDto getEditPlayers(@RequestBody PlayerWsDto request) {
        List<Player> playerList = new ArrayList<>();
        List<PlayerDto> playerDtos = request.getPlayerDtoList();
        for (PlayerDto playerDto : playerDtos) {
            Player player1 = playerRepository.findByRecordId(playerDto.getRecordId());
            playerList.add(player1);
        }
        request.setPlayerDtoList(modelMapper.map(playerList, List.class));
        return request;
    }


    @PostMapping(value = "/edit", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public PlayerWsDto handleEdit(@ModelAttribute PlayerWsDto request) {

        return playerService.handleEdit(request);
    }

    @PostMapping("/delete")
    @ResponseBody
    public PlayerWsDto deletePlayer(@RequestBody PlayerWsDto playerWsDto) {
        for (PlayerDto playerDto : playerWsDto.getPlayerDtoList()) {
            playerRepository.deleteByRecordId(playerDto.getRecordId());
        }
        playerWsDto.setMessage("Data deleted Successfully");
        playerWsDto.setBaseUrl(ADMIN_PlAYER);
        return playerWsDto;
    }

    @PostMapping("/getPlayersList")
    @ResponseBody
    public PlayerWsDto getPlayersByTeam(@RequestBody PlayerWsDto request) {
        List<Player> playerList = new ArrayList<>();
        List<PlayerDto> playerDtos = request.getPlayerDtoList();
        for (PlayerDto playerDto : playerDtos) {
            List<Player> player = playerRepository.findByTeamId(playerDto.getTeamId());
            playerList.addAll(player);
        }
        request.setPlayerDtoList(modelMapper.map(playerList, List.class));
        return request;
    }

    @PostMapping("/getPlayers")
    @ResponseBody
    public PlayerWsDto getPlayersAll(@RequestParam("team1Id") String team1Id, @RequestParam("team2Id") String team2Id) {
        PlayerWsDto playerWsDto = new PlayerWsDto();
        List<Player> playerList1 = playerRepository.findByTeamId(team1Id);
        List<Player> playerList2 = playerRepository.findByTeamId(team2Id);
        playerList1.addAll(playerList2);
        playerWsDto.setPlayerDtoList(modelMapper.map(playerList1, List.class));
        return playerWsDto;


    }

}