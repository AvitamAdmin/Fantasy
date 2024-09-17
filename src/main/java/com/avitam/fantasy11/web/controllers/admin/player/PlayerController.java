package com.avitam.fantasy11.web.controllers.admin.player;

import com.avitam.fantasy11.api.dto.PlayerDto;
import com.avitam.fantasy11.api.service.PlayerService;
import com.avitam.fantasy11.model.*;
import com.avitam.fantasy11.web.controllers.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/admin/player")
public class PlayerController extends BaseController {

    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private PlayerService playerService;
    private static final String ADMIN_PlAYER="/admin/player";

  @PostMapping
  @ResponseBody
  public PlayerDto getAllPlayers(@RequestBody PlayerDto playerDto){
      Pageable pageable=getPageable(playerDto.getPage(),playerDto.getSizePerPage(),playerDto.getSortDirection(),playerDto.getSortField());
      Player player=playerDto.getPlayer();
      Page<Player>page=isSearchActive(player)  != null ? playerRepository.findAll(Example.of(player),pageable): playerRepository.findAll(pageable);
     playerDto.setPlayerList(page.getContent());
      playerDto.setBaseUrl(ADMIN_PlAYER);
      playerDto.setTotalPages(page.getTotalPages());
      playerDto.setTotalRecords(page.getTotalElements());
      return  playerDto;
  }

    @GetMapping("/get")
    @ResponseBody
    public PlayerDto getActivePlayers(){
        PlayerDto playerDto=new PlayerDto();
        playerDto.setPlayerList(playerRepository.findByStatusOrderByIdentifier(true));
        playerDto.setBaseUrl(ADMIN_PlAYER);
        return playerDto;
    }

    @GetMapping("/edit")
    public PlayerDto editPlayer(@RequestBody PlayerDto request){
        PlayerDto playerDto=new PlayerDto();
        Player player= playerRepository.findByRecordId(request.getRecordId());
        playerDto.setPlayer(player);
        playerDto.setBaseUrl(ADMIN_PlAYER);
        return playerDto;

    }

    @PostMapping("/edit")
    @ResponseBody
    public PlayerDto handleEdit(@RequestBody PlayerDto request) {

        return playerService.handleEdit(request);
    }

    @GetMapping("/add")
    @ResponseBody
    public PlayerDto addPlayer() {
        PlayerDto playerDto=new PlayerDto();
        playerDto.setPlayerList(playerRepository.findByStatusOrderByIdentifier(true));
        playerDto.setBaseUrl(ADMIN_PlAYER);
        return playerDto;
    }

    @GetMapping("/delete")
    @ResponseBody
    public PlayerDto deletePlayer(@RequestBody PlayerDto playerDto) {
       for (String id : playerDto.getRecordId().split(",")) {
            playerRepository.deleteByRecordId(id);
        }
       playerDto.setMessage("Data deleted Successfully");
       playerDto.setBaseUrl(ADMIN_PlAYER);
        return playerDto;
    }
}
