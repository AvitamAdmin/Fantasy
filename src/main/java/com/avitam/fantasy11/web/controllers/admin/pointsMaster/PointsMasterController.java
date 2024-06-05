package com.avitam.fantasy11.web.controllers.admin.pointsMaster;

import com.avitam.fantasy11.core.service.CoreService;
import com.avitam.fantasy11.form.PointsMasterForm;
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
@RequestMapping("/admin/pointsMaster")
public class PointsMasterController {

    @Autowired
    private MatchTypeRepository matchTypeRepository;
    @Autowired
    private PointsMasterRepository pointsMasterRepository;
    @Autowired
    private CoreService coreService;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("models", pointsMasterRepository.findAll());
        return "pointsMaster/pointsMasters";
    }

    @GetMapping("/edit")
    public String editPointsMaster(@RequestParam("id") String id, Model model) {

        Optional<PointsMaster> pointsMasterOptional = pointsMasterRepository.findById(id);
        if (pointsMasterOptional.isPresent()) {
            PointsMaster pointsMaster = pointsMasterOptional.get();
            PointsMasterForm pointsMasterForm = modelMapper.map(pointsMaster, PointsMasterForm.class);

            model.addAttribute("editForm", pointsMasterForm);
        }
        model.addAttribute("match",matchTypeRepository.findAll());

        return "pointsMaster/edit";
    }

    @PostMapping("/edit")
    public String handleEdit(@ModelAttribute("editForm")  PointsMasterForm pointsMasterForm, Model model, BindingResult result) {
        if (result.hasErrors()) {
            model.addAttribute("message", result);
            return "pointsMaster/edit";
        }
        pointsMasterForm.setLastModified(new Date());

        if (pointsMasterForm.getId() == null) {
            pointsMasterForm.setCreationTime(new Date());
            pointsMasterForm.setCreator(coreService.getCurrentUser().getEmail());
        }

        PointsMaster pointsMaster = modelMapper.map(pointsMasterForm, PointsMaster.class);

        Optional<PointsMaster> pointsMasterOptional=pointsMasterRepository.findById(pointsMasterForm.getId());
        if(pointsMasterOptional.isPresent()) {
            pointsMaster.setId(pointsMasterOptional.get().getId());
        }

        Optional<MatchType> matchTypeOptional=matchTypeRepository.findById(String.valueOf(pointsMasterForm.getMatchTypeId()));
        if(matchTypeOptional.isPresent()){
            pointsMaster.setMatchTypeId(String.valueOf(matchTypeOptional.get().getId()));
        }

        pointsMasterRepository.save(pointsMaster);
        model.addAttribute("editForm", pointsMasterForm);
        return "redirect:/admin/pointsMaster";
    }

    @GetMapping("/add")
    public String addPointsMaster(Model model) {
        PointsMasterForm form = new PointsMasterForm();
        form.setCreationTime(new Date());
        form.setLastModified(new Date());
        form.setStatus(true);
        form.setCreator(coreService.getCurrentUser().getEmail());
        model.addAttribute("editForm", form);
        model.addAttribute("matchTypes",matchTypeRepository.findAll());

        return "pointsMaster/edit";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("id") String ids, Model model) {
        for (String id : ids.split(",")) {
            pointsMasterRepository.deleteById(new ObjectId(id));
        }
        return "redirect:/admin/pointsMaster";
    }
}
