package com.avitam.fantasy11.web.controllers.admin.mainContest;

import com.avitam.fantasy11.model.MainContest;
import com.avitam.fantasy11.repository.MainContestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;
import  com.avitam.fantasy11.api.dto.MainContestDto;
import  org.springframework.data.domain.Page;
import  com.avitam.fantasy11.api.service.MainContestService;

@Controller
@RequestMapping("/admin/mainContest")
public class MainContestController extends com.avitam.fantasy11.web.controllers.BaseController {

    @Autowired
    private MainContestRepository mainContestRepository;
    @Autowired
    MainContestService mainContestService;

    private static final String ADMIN_MAINCONTEST="/admin/mainContest";

@PostMapping
@ResponseBody
public MainContestDto getAllContest(@RequestBody MainContestDto mainContestDto) {
    Pageable pageable = getPageable(mainContestDto.getPage(),mainContestDto.getSizePerPage(),mainContestDto.getSortDirection(),mainContestDto.getSortField());
    MainContest mainContest=mainContestDto.getMainContest();
    Page<MainContest> page=isSearchActive(mainContest)!=null ? mainContestRepository.findAll(org.springframework.data.domain.Example.of(mainContest),pageable):mainContestRepository.findAll(pageable);
    mainContestDto.setMainContestList(page.getContent());
    mainContestDto.setBaseUrl( ADMIN_MAINCONTEST);
    mainContestDto.setTotalPages(page.getTotalPages());
    mainContestDto.setTotalRecords(page.getTotalElements());
    return mainContestDto;
}

    @GetMapping("/get")
    @ResponseBody
    public MainContestDto getMainContest(){
    MainContestDto mainContestDto=new MainContestDto();
        mainContestDto.setMainContestList(mainContestRepository.findByStatusOrderByIdentifier(true));
        mainContestDto.setBaseUrl(ADMIN_MAINCONTEST);
        return mainContestDto;
    }


    @PostMapping("/getedit")
    @ResponseBody
    public MainContestDto editMainContest(@RequestBody MainContestDto request){
       MainContestDto mainContestDto=new MainContestDto();
       MainContest mainContest=mainContestRepository.findByRecordId(request.getRecordId());
       mainContestDto.setMainContest(mainContest);
       mainContestDto.setBaseUrl(ADMIN_MAINCONTEST);
        return mainContestDto;
    }

    @PostMapping("/edit")
    @ResponseBody
    public MainContestDto handleEdit(@RequestBody MainContestDto request) {
        return mainContestService.handleEdit(request);
    }

    @GetMapping("/add")
    @ResponseBody
    public MainContestDto addMainContest() {
        MainContestDto mainContestDto = new MainContestDto();
        mainContestDto.setMainContestList(mainContestRepository.findByStatusOrderByIdentifier(true));
        mainContestDto.setBaseUrl(ADMIN_MAINCONTEST);
        return mainContestDto;
    }
    @PostMapping("/delete")
    @ResponseBody
    public MainContestDto deleteContest(@RequestBody MainContestDto mainContestDto) {

        for (String id : mainContestDto.getRecordId().split(",")) {
            mainContestRepository.deleteByRecordId(id);
        }
        mainContestDto.setMessage("Data deleted Successfully");
        mainContestDto.setBaseUrl(ADMIN_MAINCONTEST);
        return mainContestDto;
    }
        }
