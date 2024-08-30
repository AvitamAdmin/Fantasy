package com.avitam.fantasy11.web.controllers.admin.sportType;

import com.avitam.fantasy11.core.service.CoreService;
import com.avitam.fantasy11.form.SportTypeForm;
import com.avitam.fantasy11.model.SportType;
import com.avitam.fantasy11.model.SportTypeRepository;
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

@Controller
@RequestMapping("/admin/sportType")
public class SportsTypeController {

    @Autowired
    private SportTypeRepository sportTypeRepository;
    @Autowired
    private CoreService coreService;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public String getAllSportType(Model model){
        List<SportType> sportTypes = sportTypeRepository.findAll();
        List<SportType> datas=new ArrayList<>();
        for(SportType data:sportTypes){
            if(data.getId()!=null) {
                byte[] image = data.getLogo().getData();
                data.setPic(Base64.getEncoder().encodeToString(image));
                datas.add(data);
            }
        }
        model.addAttribute("models", datas);
        return "sportType/sportTypes";
    }

    @GetMapping("/edit")
    public String editSportType (@RequestParam("id") String id, Model model){

        Optional<SportType> sportTypeOptional = sportTypeRepository.findByRecordId(id);
        if (sportTypeOptional.isPresent()) {
            SportType sportType = sportTypeOptional.get();
            SportTypeForm sportTypeForm = modelMapper.map(sportType, SportTypeForm.class);
            sportTypeForm.setId(String.valueOf(sportType.getId()));
            model.addAttribute("editForm", sportTypeForm);
        }
        return "sportType/edit";
    }

    @PostMapping("/edit")
    public String handleEdit(@ModelAttribute("editForm")SportTypeForm sportTypeForm, Model model, BindingResult result) throws IOException {

        if (result.hasErrors()) {
            model.addAttribute("message", result);
            model.addAttribute("editForm", sportTypeForm);
            return "sportType/edit";
        }

        sportTypeForm.setLastModified(new Date());
        if (sportTypeForm.getId() == null) {
            sportTypeForm.setCreationTime(new Date());
            sportTypeForm.setCreator(coreService.getCurrentUser().getEmail());
        }
        byte[] fig= sportTypeForm.getLogo().getBytes();
        Binary binary=new Binary(fig);


        SportType sportType = modelMapper.map(sportTypeForm, SportType.class);

        Optional<SportType> sportTypeOptional=sportTypeRepository.findById(sportTypeForm.getId());
        if(sportTypeOptional.isPresent()) {
            sportType.setId(sportTypeOptional.get().getId());
        }
        sportType.setLogo(binary);
        sportTypeRepository.save(sportType);
        if(sportType.getRecordId()==null)
        {
            sportType.setRecordId(String.valueOf(sportType.getId().getTimestamp()));
        }
        sportTypeRepository.save(sportType);
        model.addAttribute("editForm", sportTypeForm);

        return "redirect:/matches/sportType";
    }

    @GetMapping("/add")
    public String addSportType(Model model) {
        SportTypeForm form = new SportTypeForm();
        form.setCreationTime(new Date());
        form.setLastModified(new Date());
        form.setStatus(true);
        form.setCreator(coreService.getCurrentUser().getEmail());
        model.addAttribute("editForm", form);
        return "sportType/edit";
    }
    @GetMapping("/delete")
    public String deleteTeam(@RequestParam("id") String ids, Model model) {
        for (String id : ids.split(",")) {
            sportTypeRepository.deleteByRecordId(id);
        }
        return "redirect:/matches/sportType";
    }
}
