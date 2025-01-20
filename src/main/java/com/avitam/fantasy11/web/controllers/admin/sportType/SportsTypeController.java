package com.avitam.fantasy11.web.controllers.admin.sportType;

import com.avitam.fantasy11.api.dto.*;
import com.avitam.fantasy11.api.service.SportTypeService;
import com.avitam.fantasy11.model.Contest;
import com.avitam.fantasy11.model.SportType;
import com.avitam.fantasy11.model.TeamLineup;
import com.avitam.fantasy11.repository.SportTypeRepository;
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

import java.util.List;

@Controller
@RequestMapping("/admin/sportType")
public class SportsTypeController extends BaseController {

    @Autowired
    private SportTypeRepository sportTypeRepository;
    @Autowired
    private SportTypeService sportTypeService;
    @Autowired
    private ModelMapper modelMapper;
    private static final String ADMIN_SPORTSTYPE="/admin/sportType";

    @PostMapping
    @ResponseBody
    public SportTypeWsDto getAllSportType(@RequestBody SportTypeWsDto sportTypeWsDto){
        Pageable pageable=getPageable(sportTypeWsDto.getPage(),sportTypeWsDto.getSizePerPage(),sportTypeWsDto.getSortDirection(), sportTypeWsDto.getSortField());
        SportTypeDto sportTypeDto = CollectionUtils.isNotEmpty(sportTypeWsDto.getSportTypeDtoList()) ? sportTypeWsDto.getSportTypeDtoList()  .get(0):new SportTypeDto();
        SportType sportType =modelMapper.map(sportTypeDto,SportType.class);
        Page<SportType> page=isSearchActive(sportType)!=null ? sportTypeRepository.findAll(Example.of(sportType),pageable):sportTypeRepository.findAll(pageable);
        sportTypeWsDto.setSportTypeDtoList(modelMapper.map(page.getContent(), List.class));
        sportTypeWsDto.setBaseUrl(ADMIN_SPORTSTYPE);
        sportTypeWsDto.setTotalPages(page.getTotalPages());
        sportTypeWsDto.setTotalRecords(page.getTotalElements());
        return sportTypeWsDto;
    }

    @GetMapping("/get")
    @ResponseBody
    public SportTypeWsDto getActiveSportType(){
        SportTypeWsDto sportTypeWsDto= new SportTypeWsDto();
        sportTypeWsDto.setSportTypeDtoList(modelMapper.map(sportTypeRepository.findByStatusOrderByIdentifier(true),List.class));
        sportTypeWsDto.setBaseUrl(ADMIN_SPORTSTYPE);
        return sportTypeWsDto;
    }
    @PostMapping("/getedit")
    @ResponseBody
    public SportTypeWsDto editSportType (@RequestBody SportTypeWsDto request){
        SportTypeWsDto sportTypeWsDto = new SportTypeWsDto();
        sportTypeWsDto.setBaseUrl(ADMIN_SPORTSTYPE);
        SportType sportType = sportTypeRepository.findByRecordId(request.getSportTypeDtoList().get(0).getRecordId());
        if( sportType != null){
            sportTypeWsDto.setSportTypeDtoList(List.of(modelMapper.map(sportType, SportTypeDto.class)));
        }
        return sportTypeWsDto;
    }

    @PostMapping(value = "/edit", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public SportTypeWsDto handleEdit(@ModelAttribute SportTypeWsDto request){
        return sportTypeService.handleEdit(request);
    }

    @PostMapping("/delete")
    @ResponseBody
    public SportTypeWsDto deleteSportType(@RequestBody SportTypeWsDto sportTypeWsDto ) {
        for(SportTypeDto sportTypeDto : sportTypeWsDto.getSportTypeDtoList()){
            sportTypeRepository.deleteByRecordId(sportTypeDto.getRecordId());
        }
        sportTypeWsDto.setMessage("Data deleted Successfully");
        sportTypeWsDto.setBaseUrl(ADMIN_SPORTSTYPE);
        return sportTypeWsDto;

    }


}