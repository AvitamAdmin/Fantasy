package com.avitam.fantasy11.web.controllers.admin.player;

import com.avitam.fantasy11.core.service.CoreService;
import com.avitam.fantasy11.form.PlayerForm;
import com.avitam.fantasy11.form.TeamForm;
import com.avitam.fantasy11.model.*;
import org.bson.types.Binary;
import org.bson.types.ObjectId;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin/player")
public class PlayerController {

    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private PlayerRoleRepository playerRoleRepository;
    @Autowired
    private CoreService coreService;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public String getAll(Model model){
        List<Player> players = playerRepository.findAll();
        List<Player> datas=new ArrayList<>();
        for(Player player:players){
            if(player.getId()!=null) {
                byte[] image = player.getPlayerImage().getData();
                player.setPic(Base64.getEncoder().encodeToString(image));
                datas.add(player);
            }
        }
        model.addAttribute("models", datas);
        return "player/players";
    }
    @GetMapping("/edit")
    public String editPlayer(@RequestParam("id")ObjectId id, Model model){

        Optional<Player> playerOptional = playerRepository.findById(id);
        if (playerOptional.isPresent()) {
            Player player = playerOptional.get();
            PlayerForm playerForm = modelMapper.map(player, PlayerForm.class);
            model.addAttribute("editForm", playerForm);
        }
        model.addAttribute("teams", teamRepository.findAll());
        model.addAttribute("playerRoles", playerRoleRepository.findAll());
        return "player/edit";
    }

    @PostMapping("/edit")
    public String handleEdit(@ModelAttribute("editForm")PlayerForm playerForm,String id, Model model, BindingResult result) throws IOException {

        if (result.hasErrors()) {
            model.addAttribute("message", result);
            model.addAttribute("editForm", playerForm);
            return "player/edit";
        }

        byte[] fig= playerForm.getPlayerImage().getBytes();
        Binary binary=new Binary(fig);

        playerForm.setLastModified(new Date());
        if (playerForm.getId() == null) {
            playerForm.setCreationTime(new Date());
            playerForm.setCreator(coreService.getCurrentUser().getEmail());
        }

        Player player = modelMapper.map(playerForm, Player.class);
        Optional<Player> playerOptional=playerRepository.findById(playerForm.getId());
        if(playerOptional.isPresent()){
            player.setId(playerOptional.get().getId());
        }

        player.setPlayerImage(binary);

        Optional<Team> teamOptional = teamRepository.findById(playerForm.getTeamId());
        if(teamOptional.isPresent()){
            player.setTeamId(String.valueOf(teamOptional.get().getId()));
        }

        Optional<PlayerRole> playerRoleOptional = playerRoleRepository.findById(playerForm.getPlayerRoleId());
        if(playerRoleOptional.isPresent()){
            player.setPlayerRoleId(String.valueOf(playerRoleOptional.get().getId()));
        }

        playerRepository.save(player);
        model.addAttribute("editForm", playerForm);

        return "redirect:/admin/player";
    }

    @GetMapping("/add")
    public String addPlayer(Model model) {
        PlayerForm form = new PlayerForm();
        form.setCreationTime(new Date());
        form.setLastModified(new Date());
        form.setStatus(true);
        form.setCreator(coreService.getCurrentUser().getEmail());
        model.addAttribute("editForm", form);
        model.addAttribute("teams", teamRepository.findAll());
        model.addAttribute("playerRoles", playerRoleRepository.findAll());
        return "player/edit";
    }
    @GetMapping("/delete")
    public String deletePlayer(@RequestParam("id") String ids, Model model) {
       for (String id : ids.split(",")) {
            playerRepository.deleteById(new ObjectId(id));
        }
        return "redirect:/admin/player";
    }
}
