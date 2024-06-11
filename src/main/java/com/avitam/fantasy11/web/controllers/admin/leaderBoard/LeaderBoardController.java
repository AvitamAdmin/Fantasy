package com.avitam.fantasy11.web.controllers.admin.leaderBoard;

import com.avitam.fantasy11.core.service.CoreService;
import com.avitam.fantasy11.form.ContestForm;
import com.avitam.fantasy11.form.LeaderBoardForm;
import com.avitam.fantasy11.model.*;
import org.bson.types.ObjectId;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin/leaderBoard")
public class LeaderBoardController {

    @Autowired
    private LeaderBoardRepository leaderBoardRepository;
    @Autowired
    private TournamentRepository tournamentRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CoreService coreService;

    @GetMapping
    public String getAll(Model model){
        List<LeaderBoard> leaderBoardList=leaderBoardRepository.findAll();
        model.addAttribute("models",leaderBoardList);
        return "leaderBoard/leaderBoards";
    }

    @GetMapping("/edit")
    public String editLeaderBoard(@RequestParam("id") String id, Model model) {

        Optional<LeaderBoard> leaderBoardOptional = leaderBoardRepository.findById(id);
        if (leaderBoardOptional.isPresent()) {
            LeaderBoard leaderBoard = leaderBoardOptional.get();

            modelMapper.getConfiguration().setAmbiguityIgnored(true);
            LeaderBoardForm leaderBoardForm = modelMapper.map(leaderBoard, LeaderBoardForm.class);
            leaderBoardForm.setId(String.valueOf(leaderBoard.getId()));

            model.addAttribute("editForm", leaderBoardForm);
            model.addAttribute("tournaments",tournamentRepository.findAll().stream().filter(tournament -> tournament.getId()!=null).collect(Collectors.toList()));
        }
        return "leaderBoard/edit";
    }

    @PostMapping("/edit")
    public String handleEdit(@ModelAttribute("editForm") LeaderBoardForm leaderBoardForm, Model model, BindingResult result) {
        if (result.hasErrors()) {
            model.addAttribute("message", result);
            return "leaderBoard/edit";
        }
            leaderBoardForm.setLastModified(new Date());

        if (leaderBoardForm.getId() == null) {
            leaderBoardForm.setCreationTime(new Date());
            leaderBoardForm.setCreator(coreService.getCurrentUser().getEmail());
        }

        LeaderBoard leaderBoard = modelMapper.map(leaderBoardForm, LeaderBoard.class);
        Optional<LeaderBoard> leaderBoardOptional=leaderBoardRepository.findById(leaderBoardForm.getId());
        if(leaderBoardOptional.isPresent()) {
            leaderBoard.setId(leaderBoardOptional.get().getId());
        }
        Optional<Tournament> tournamentOptional=tournamentRepository.findById(leaderBoardForm.getTournamentId());
         if(tournamentOptional.isPresent()){
            leaderBoard.setTournamentId(String.valueOf(tournamentOptional.get().getId()));
         }

        leaderBoardRepository.save(leaderBoard);
        model.addAttribute("editForm", leaderBoardForm);
        return "redirect:/admin/leaderBoard";
    }

    @GetMapping("/add")
    public String addLeaderBoard(Model model) {
        LeaderBoardForm form = new LeaderBoardForm();
        form.setCreationTime(new Date());
        form.setLastModified(new Date());
        form.setStatus(true);
        form.setCreator(coreService.getCurrentUser().getEmail());
        model.addAttribute("editForm", form);
        model.addAttribute("tournaments",tournamentRepository.findAll().stream().filter(tournament -> tournament.getId()!=null).collect(Collectors.toList()));

        return "leaderBoard/edit";
    }

    @GetMapping("/delete")
    public String deleteLeaderBoard(@RequestParam("id") String ids, Model model) {
        for (String id : ids.split(",")) {
              leaderBoardRepository.deleteById(new ObjectId(id));
        }
        return "redirect:/admin/leaderBoard";
    }
}
