package com.avitam.fantasy11.web.controllers.admin.lineUpStatus;

import com.avitam.fantasy11.core.service.CoreService;
import com.avitam.fantasy11.form.LineUpStatusForm;
import com.avitam.fantasy11.form.MatchTypeForm;
import com.avitam.fantasy11.model.LineUpStatus;
import com.avitam.fantasy11.model.LineUpStatusRepository;
import com.avitam.fantasy11.model.MatchType;
import com.avitam.fantasy11.model.MatchTypeRepository;
import org.bson.types.ObjectId;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/lineupStatus")
public class LineUpStatusController {

    @Autowired
    private LineUpStatusRepository lineUpStatusRepository;
    @Autowired
    private CoreService coreService;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public String getAll(Model model){
        List<LineUpStatus> lineUps = lineUpStatusRepository.findAll();
        model.addAttribute("models", lineUps);
        return "lineupStatus/lineupStatuses";
    }

    @GetMapping("/edit")
    public String editLineupStatus (@RequestParam("id") ObjectId id, Model model){

        Optional<LineUpStatus> lineUpStatusOptional = lineUpStatusRepository.findById(id);
        if (lineUpStatusOptional.isPresent()) {
            LineUpStatus lineUpStatus= lineUpStatusOptional.get();
            LineUpStatusForm lineUpStatusForm= modelMapper.map(lineUpStatus, LineUpStatusForm.class);
            model.addAttribute("editForm", lineUpStatusForm);
        }
        return "lineupStatus/edit";
    }

    @PostMapping("/edit")
    public String handleEdit(@ModelAttribute("editForm")LineUpStatusForm lineUpStatusForm, Model model, BindingResult result) throws IOException {

        if (result.hasErrors()) {
            model.addAttribute("message", result);
            model.addAttribute("editForm", lineUpStatusForm);
            return "lineupStatus/edit";
        }

            lineUpStatusForm.setLastModified(new Date());
        if (lineUpStatusForm.getId() == null) {
            lineUpStatusForm.setCreationTime(new Date());
            lineUpStatusForm.setCreator(coreService.getCurrentUser().getEmail());
        }

        LineUpStatus lineUpStatus = modelMapper.map(lineUpStatusForm, LineUpStatus.class);
        if(lineUpStatusForm.getId()!=null) {
            Optional<LineUpStatus> lineUpStatusOptional = lineUpStatusRepository.findById(lineUpStatusForm.getId());
            if (lineUpStatusOptional.isPresent()) {
                lineUpStatus.setId(lineUpStatusOptional.get().getId());
            }
        }
        lineUpStatusRepository.save(lineUpStatus);
        model.addAttribute("editForm", lineUpStatusForm);

        return "redirect:/admin/lineupStatus";
    }

    @GetMapping("/add")
    public String addLineUpStatus(Model model) {
        LineUpStatusForm form = new LineUpStatusForm();
        form.setCreationTime(new Date());
        form.setLastModified(new Date());
        form.setStatus(true);
        form.setCreator(coreService.getCurrentUser().getEmail());
        model.addAttribute("editForm", form);
        return "lineupStatus/edit";
    }
    @GetMapping("/delete")
    public String deleteLineupStatus(@RequestParam("id") String ids, Model model) {
        for (String id : ids.split(",")) {
            lineUpStatusRepository.deleteById(new ObjectId(id));
        }
        return "redirect:/admin/lineupStatus";
    }
}