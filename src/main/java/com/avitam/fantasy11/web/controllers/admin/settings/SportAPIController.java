package com.avitam.fantasy11.web.controllers.admin.settings;

import com.avitam.fantasy11.api.dto.SportAPIDto;
import com.avitam.fantasy11.api.service.SportAPIService;
import com.avitam.fantasy11.model.*;
import com.avitam.fantasy11.web.controllers.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/sportsApi")
public class SportAPIController extends BaseController {

    @Autowired
    private SportsApiRepository sportsApiRepository;
    @Autowired
    private SportAPIService sportAPIService;

    public static final String ADMIN_SPORTAPI = "/admin/sportsApi";


    @PostMapping
    @ResponseBody
    public SportAPIDto getAll(@RequestBody SportAPIDto sportAPIDto) {
        Pageable pageable=getPageable(sportAPIDto.getPage(),sportAPIDto.getSizePerPage(),sportAPIDto.getSortDirection(),sportAPIDto.getSortField());
        SportsApi sportsApi =sportAPIDto.getSportAPI();
        Page<SportsApi> page=isSearchActive(sportsApi)!=null ? sportsApiRepository.findAll(Example.of(sportsApi),pageable) : sportsApiRepository.findAll(pageable);
        sportAPIDto.setSportsApiList(page.getContent());
        sportAPIDto.setTotalPages(page.getTotalPages());
        sportAPIDto.setTotalRecords(page.getTotalElements());
        sportAPIDto.setBaseUrl(ADMIN_SPORTAPI);
        return sportAPIDto;
    }

    @GetMapping("/get")
    @ResponseBody
    public SportAPIDto getActiveSportAPIList() {
       SportAPIDto sportAPIDto = new SportAPIDto();
        sportAPIDto.setSportsApiList(sportsApiRepository.findByStatusOrderByIdentifier(true));
        sportAPIDto.setBaseUrl(ADMIN_SPORTAPI);
        return sportAPIDto;
    }

    @PostMapping("/getedit")
    @ResponseBody
    public SportAPIDto edit(@RequestBody SportAPIDto request) {
        SportAPIDto sportAPIDto = new SportAPIDto();
        SportsApi sportsApi = sportsApiRepository.findByRecordId(request.getRecordId());
        sportAPIDto.setSportAPI(sportsApi);
        sportAPIDto.setBaseUrl(ADMIN_SPORTAPI);
        return sportAPIDto;
    }

    @PostMapping("/edit")
    @ResponseBody
    public SportAPIDto handleEdit(@RequestBody SportAPIDto request) {

        return sportAPIService.handleEdit(request);
    }

    @GetMapping("/add")
    @ResponseBody
    public SportAPIDto add() {
        SportAPIDto sportAPIDto = new SportAPIDto();
        sportAPIDto.setSportsApiList(sportsApiRepository.findByStatusOrderByIdentifier(true));
        sportAPIDto.setBaseUrl(ADMIN_SPORTAPI);
        return sportAPIDto;
    }

    @PostMapping("/delete")
    @ResponseBody
    public SportAPIDto delete(@RequestBody SportAPIDto sportAPIDto) {
        for (String id : sportAPIDto.getRecordId().split(",")) {

            sportsApiRepository.deleteByRecordId(id);
        }
        sportAPIDto.setMessage("Data deleted successfully!!");
        sportAPIDto.setBaseUrl(ADMIN_SPORTAPI);
        return sportAPIDto;
    }
}
