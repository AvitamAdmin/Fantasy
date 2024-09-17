package com.avitam.fantasy11.web.controllers.admin.sportType;

import com.avitam.fantasy11.api.dto.LeaderBoardDto;
import com.avitam.fantasy11.api.dto.SportTypeDto;
import com.avitam.fantasy11.api.service.SportTypeService;
import com.avitam.fantasy11.model.SportType;
import com.avitam.fantasy11.model.SportTypeRepository;
import com.avitam.fantasy11.web.controllers.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/sportType")
public class SportsTypeController extends BaseController {

    @Autowired
    private SportTypeRepository sportTypeRepository;
    @Autowired
    private SportTypeService sportTypeService;
    private static final String ADMIN_SPORTSTYPE="/admin/sportType";

    @PostMapping
    @ResponseBody
    public SportTypeDto getAllSportType(SportTypeDto sportTypeDto){
        Pageable pageable=getPageable(sportTypeDto.getPage(),sportTypeDto.getSizePerPage(),sportTypeDto.getSortDirection(), sportTypeDto.getSortField());
        SportType sportType=sportTypeDto.getSportType();
        Page<SportType> page=isSearchActive(sportType)!=null ? sportTypeRepository.findAll(Example.of(sportType),pageable):sportTypeRepository.findAll(pageable);
        sportTypeDto.setSportTypeList(page.getContent());
        sportTypeDto.setBaseUrl(ADMIN_SPORTSTYPE);
        sportTypeDto.setTotalPages(page.getTotalPages());
        sportTypeDto.setTotalRecords(page.getTotalElements());
        return sportTypeDto;
    }

    @GetMapping("/get")
    @ResponseBody
    public SportTypeDto getSportType(){
        SportTypeDto sportTypeDto= new SportTypeDto();
        sportTypeDto.setSportTypeList(sportTypeRepository.findByStatusOrderByIdentifier(true));
        sportTypeDto.setBaseUrl(ADMIN_SPORTSTYPE);
        return sportTypeDto;
    }
    @GetMapping("/edit")
    @ResponseBody
    public SportTypeDto editSportType (@RequestBody SportTypeDto request){
        SportTypeDto sportTypeDto=new SportTypeDto();
        SportType sportType=sportTypeRepository.findByRecordId(request.getRecordId());
        sportTypeDto.setBaseUrl(ADMIN_SPORTSTYPE);
        return sportTypeDto;
    }

    @PostMapping("/edit")
    @ResponseBody
    public SportTypeDto handleEdit(@RequestBody SportTypeDto request){

        return sportTypeService.handleEdit(request);
    }

    @GetMapping("/add")
    @ResponseBody
    public SportTypeDto addSportType(Model model) {
        SportTypeDto sportTypeDto= new SportTypeDto();
        sportTypeDto.setSportTypeList(sportTypeRepository.findByStatusOrderByIdentifier(true));
        sportTypeDto.setBaseUrl(ADMIN_SPORTSTYPE);
        return sportTypeDto;
    }
    @GetMapping("/delete")
    @ResponseBody
    public SportTypeDto delete(@RequestBody SportTypeDto sportTypeDto ) {
        for (String id : sportTypeDto.getRecordId().split(",")) {
            sportTypeRepository.deleteByRecordId(id);
        }
        sportTypeDto.setMessage("Data deleted Successfully");
        sportTypeDto.setBaseUrl(ADMIN_SPORTSTYPE);
        return sportTypeDto;
    }
}
