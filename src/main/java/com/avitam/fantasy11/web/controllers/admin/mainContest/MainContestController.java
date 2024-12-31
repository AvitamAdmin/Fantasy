package com.avitam.fantasy11.web.controllers.admin.mainContest;

import com.avitam.fantasy11.api.dto.LineUpStatusDto;
import com.avitam.fantasy11.api.dto.MainContestDto;
import com.avitam.fantasy11.api.dto.MainContestWsDto;
import com.avitam.fantasy11.api.service.MainContestService;
import com.avitam.fantasy11.model.LineUpStatus;
import com.avitam.fantasy11.model.MainContest;
import com.avitam.fantasy11.repository.MainContestRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/mainContest")
public class MainContestController extends com.avitam.fantasy11.web.controllers.BaseController {

    @Autowired
    private MainContestRepository mainContestRepository;
    @Autowired
    MainContestService mainContestService;
    @Autowired
    ModelMapper modelMapper;

    private static final String ADMIN_MAINCONTEST="/admin/mainContest";

@PostMapping
@ResponseBody
public MainContestWsDto getAllContest(@RequestBody MainContestWsDto mainContestWsDto) {
    Pageable pageable = getPageable(mainContestWsDto.getPage(),mainContestWsDto.getSizePerPage(),mainContestWsDto.getSortDirection(),mainContestWsDto.getSortField());
    MainContestDto mainContestDto= CollectionUtils.isNotEmpty(mainContestWsDto.getMainContestDtoList())?mainContestWsDto.getMainContestDtoList() .get(0) : new MainContestDto() ;
    MainContest mainContest = modelMapper.map(mainContestWsDto, MainContest.class);
    Page<MainContest> page=isSearchActive(mainContest)!=null ? mainContestRepository.findAll(org.springframework.data.domain.Example.of(mainContest),pageable):mainContestRepository.findAll(pageable);
    mainContestWsDto.setMainContestDtoList(modelMapper.map(page.getContent(),List.class));
    mainContestWsDto.setBaseUrl( ADMIN_MAINCONTEST);
    mainContestWsDto.setTotalPages(page.getTotalPages());
    mainContestWsDto.setTotalRecords(page.getTotalElements());
    return mainContestWsDto;
}

    @GetMapping("/get")
    @ResponseBody
    public MainContestWsDto getMainContest(){
    MainContestWsDto mainContestWsDto=new MainContestWsDto();
        mainContestWsDto.setMainContestDtoList(modelMapper.map(mainContestRepository.findByStatusOrderByIdentifier(true), List.class));
        mainContestWsDto.setBaseUrl(ADMIN_MAINCONTEST);
        return mainContestWsDto;
    }


    @PostMapping("/getedit")
    @ResponseBody
    public MainContestWsDto editMainContest(@RequestBody MainContestWsDto request){
       MainContestWsDto mainContestWsDto=new MainContestWsDto();
       MainContest mainContest=mainContestRepository.findByRecordId(request.getMainContestDtoList().get(0).getRecordId());
        mainContestWsDto.setMainContestDtoList((List<MainContestDto>) mainContest);
        mainContestWsDto.setBaseUrl(ADMIN_MAINCONTEST);
        return mainContestWsDto;
    }

    @PostMapping("/edit")
    @ResponseBody
    public MainContestWsDto handleEdit(@RequestBody MainContestWsDto request) {
        return mainContestService.handleEdit(request);
    }

    @GetMapping("/add")
    @ResponseBody
    public MainContestWsDto addMainContest() {
        MainContestWsDto mainContestWsDto = new MainContestWsDto();
        mainContestWsDto.setMainContestDtoList(modelMapper.map(mainContestRepository.findByStatusOrderByIdentifier(true),List.class));
        mainContestWsDto.setBaseUrl(ADMIN_MAINCONTEST);
        return mainContestWsDto;
    }
    @PostMapping("/delete")
    @ResponseBody
    public MainContestWsDto deleteContest(@RequestBody MainContestWsDto mainContestWsDto) {

        for (String id : mainContestWsDto.getMainContestDtoList().get(0).getRecordId().split(",")) {
            mainContestRepository.deleteByRecordId(id);
        }
        mainContestWsDto.setMessage("Data deleted Successfully");
        mainContestWsDto.setBaseUrl(ADMIN_MAINCONTEST);
        return mainContestWsDto;
    }
        }
