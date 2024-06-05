package com.avitam.fantasy11.web.controllers.admin.pointsUpdate;

import com.avitam.fantasy11.core.service.CoreService;
import com.avitam.fantasy11.form.PointsMasterForm;
import com.avitam.fantasy11.form.PointsUpdateForm;
import com.avitam.fantasy11.model.*;
import org.bson.types.ObjectId;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin/pointsUpdate")
public class PointsUpdateController {

    @Autowired
    private PointsUpdateRepository pointsUpdateRepository;
    @Autowired
    private MatchesRepository matchesRepository;
    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CoreService coreService;

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("models", pointsUpdateRepository.findAll());
        return "pointsUpdate/pointsUpdates";
    }

    @GetMapping("/edit")
    public String editPointsUpdate(@RequestParam("id") String id, Model model) {

        Optional<PointsUpdate> pointsUpdateOptional = pointsUpdateRepository.findById(id);

        if (pointsUpdateOptional.isPresent()) {
            PointsUpdate pointsUpdate = pointsUpdateOptional.get();

            PointsUpdateForm pointsUpdateForm = modelMapper.map(pointsUpdate, PointsUpdateForm.class);
            model.addAttribute("editForm", pointsUpdateForm);
        }
        model.addAttribute("matches",matchesRepository.findAll());
        model.addAttribute("players",playerRepository.findAll());

        return "pointsUpdate/edit";
    }

    @PostMapping("/edit")
    public String handleEdit(@ModelAttribute("editForm")  PointsUpdateForm pointsUpdateForm, Model model, BindingResult result) {
        if (result.hasErrors()) {
            model.addAttribute("message", result);
            return "pointsUpdate/edit";
        }
        pointsUpdateForm.setLastModified(new Date());

        if (pointsUpdateForm.getId() == null) {
            pointsUpdateForm.setCreationTime(new Date());
            pointsUpdateForm.setCreator(coreService.getCurrentUser().getEmail());
        }

        PointsUpdate pointsUpdate = modelMapper.map(pointsUpdateForm, PointsUpdate.class);

        Optional<PointsUpdate> pointsUpdateOptional=pointsUpdateRepository.findById(pointsUpdateForm.getId());
        if(pointsUpdateOptional.isPresent()) {
            pointsUpdate.setId(pointsUpdateOptional.get().getId());
        }

        Optional<Matches> matchesOptional=matchesRepository.findById(pointsUpdateForm.getMatchId());
        if(matchesOptional.isPresent()){
            pointsUpdate.setMatchId(String.valueOf(matchesOptional.get().getId()));
        }
        Optional<Player> playerOptional=playerRepository.findById(pointsUpdateForm.getPlayerId());
        if(playerOptional.isPresent()){
            pointsUpdate.setPlayerId(String.valueOf(playerOptional.get().getId()));
        }

        pointsUpdateRepository.save(pointsUpdate);
        model.addAttribute("editForm", pointsUpdateForm);
        return "redirect:/admin/pointsUpdate";
    }

    @GetMapping("/add")
    public String addPointsUpdate(Model model) {
        PointsUpdateForm form = new PointsUpdateForm();
        form.setCreationTime(new Date());
        form.setLastModified(new Date());
        form.setStatus(true);
        form.setCreator(coreService.getCurrentUser().getEmail());
        model.addAttribute("editForm", form);
        model.addAttribute("matches",matchesRepository.findAll());
        model.addAttribute("players",playerRepository.findAll());

        return "pointsUpdate/edit";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("id") String ids, Model model) {
        for (String id : ids.split(",")) {
            pointsUpdateRepository.deleteById(new ObjectId(id));
        }
        return "redirect:/admin/pointsUpdate";
    }
}
