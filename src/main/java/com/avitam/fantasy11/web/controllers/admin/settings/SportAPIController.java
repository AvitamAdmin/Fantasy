package com.avitam.fantasy11.web.controllers.admin.settings;

import com.avitam.fantasy11.api.dto.SportAPIDto;
import com.avitam.fantasy11.api.dto.SportsAPIWsDto;
import com.avitam.fantasy11.api.service.SportAPIService;
import com.avitam.fantasy11.model.SportsApi;
import com.avitam.fantasy11.repository.SportsApiRepository;
import com.avitam.fantasy11.web.controllers.BaseController;
import org.apache.commons.collections4.CollectionUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/sportsApi")
public class SportAPIController extends BaseController {

    @Autowired
    private SportsApiRepository sportsApiRepository;
    @Autowired
    private SportAPIService sportAPIService;
    @Autowired
    ModelMapper modelMapper;

    public static final String ADMIN_SPORTAPI = "/admin/sportsApi";


    @PostMapping
    @ResponseBody
    public SportsAPIWsDto getAll(@RequestBody SportsAPIWsDto sportsAPIWsDto) {
        Pageable pageable = getPageable(sportsAPIWsDto.getPage(), sportsAPIWsDto.getSizePerPage(), sportsAPIWsDto.getSortDirection(), sportsAPIWsDto.getSortField());
        SportAPIDto sportAPIDto = CollectionUtils.isNotEmpty(sportsAPIWsDto.getSportAPIDtoList()) ? sportsAPIWsDto.getSportAPIDtoList().get(0) : new SportAPIDto();
        SportsApi sportsApi = modelMapper.map(sportAPIDto, SportsApi.class);
        Page<SportsApi> page = isSearchActive(sportsApi) != null ? sportsApiRepository.findAll(Example.of(sportsApi), pageable) : sportsApiRepository.findAll(pageable);
        sportsAPIWsDto.setSportAPIDtoList(modelMapper.map(page.getContent(), List.class));
        sportsAPIWsDto.setTotalPages(page.getTotalPages());
        sportsAPIWsDto.setTotalRecords(page.getTotalElements());
        sportsAPIWsDto.setBaseUrl(ADMIN_SPORTAPI);
        return sportsAPIWsDto;
    }

    @GetMapping("/get")
    @ResponseBody
    public SportsAPIWsDto getActiveSportAPIList() {
        SportsAPIWsDto sportsAPIWsDto = new SportsAPIWsDto();
        sportsAPIWsDto.setSportAPIDtoList(modelMapper.map(sportsApiRepository.findByStatusOrderByIdentifier(true), List.class));
        sportsAPIWsDto.setBaseUrl(ADMIN_SPORTAPI);
        return sportsAPIWsDto;
    }

    @PostMapping("/getedit")
    @ResponseBody
    public SportsAPIWsDto editAPI(@RequestBody SportsAPIWsDto request) {
        SportsAPIWsDto sportsAPIWsDto = new SportsAPIWsDto();
        SportsApi sportsApi = sportsApiRepository.findByRecordId(request.getSportAPIDtoList().get(0).getRecordId());
        sportsAPIWsDto.setSportAPIDtoList(modelMapper.map(sportsApi, List.class));
        sportsAPIWsDto.setBaseUrl(ADMIN_SPORTAPI);
        return sportsAPIWsDto;
    }

    @PostMapping("/edit")
    @ResponseBody
    public SportsAPIWsDto handleEdit(@RequestBody SportsAPIWsDto request) {

        return sportAPIService.handleEdit(request);
    }

    @PostMapping("/delete")
    @ResponseBody
    public SportsAPIWsDto deleteAPI(@RequestBody SportsAPIWsDto sportsAPIWsDto) {
        for (SportAPIDto data : sportsAPIWsDto.getSportAPIDtoList()) {

            sportsApiRepository.deleteByRecordId(data.getRecordId());
        }
        sportsAPIWsDto.setMessage("Data deleted successfully!!");
        sportsAPIWsDto.setBaseUrl(ADMIN_SPORTAPI);
        return sportsAPIWsDto;
    }
}
