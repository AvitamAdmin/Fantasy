package com.avitam.fantasy11.web.controllers.admin.matches;


import com.avitam.fantasy11.api.dto.MatchesDto;
import com.avitam.fantasy11.api.dto.MatchesWsDto;
import com.avitam.fantasy11.api.service.MatchesService;
import com.avitam.fantasy11.core.service.CoreService;
import com.avitam.fantasy11.model.Matches;
import com.avitam.fantasy11.repository.*;
import com.avitam.fantasy11.web.controllers.BaseController;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/admin/matches")
public class MatchesController extends BaseController {
    @Autowired
    private MatchesRepository matchesRepository;
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private TournamentRepository tournamentRepository;
    @Autowired
    private SportTypeRepository sportTypeRepository;
    @Autowired
    private MainContestRepository mainContestRepository;
    @Autowired
    private MatchTypeRepository matchTypeRepository;
    @Autowired
    private MatchesService matchesService;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CoreService coreService;

    public static final String ADMIN_MATCHES = "/admin/matches";

    @PostMapping
    @ResponseBody
    public MatchesWsDto getAllMatches(@RequestBody MatchesWsDto matchesWsDto) {
        Pageable pageable=getPageable(matchesWsDto.getPage(),matchesWsDto.getSizePerPage(),matchesWsDto.getSortDirection(),matchesWsDto.getSortField());
        MatchesDto matchesDto = CollectionUtils.isNotEmpty(matchesWsDto.getMatchesDtoList()) ? matchesWsDto.getMatchesDtoList().get(0):new MatchesDto();
        Matches matches =modelMapper.map(matchesWsDto,Matches.class);
        Page<Matches> page= isSearchActive(matches) !=null ? matchesRepository.findAll(Example.of(matches),pageable): matchesRepository.findAll(pageable);
        matchesWsDto.setMatchesDtoList(modelMapper.map(page.getContent(), List.class));
        matchesWsDto.setBaseUrl(ADMIN_MATCHES);
        matchesWsDto.setTotalRecords(page.getTotalElements());
        return matchesWsDto;
    }

    @GetMapping("/get")
    @ResponseBody
    public MatchesWsDto getActiveMatches(@RequestBody MatchesWsDto request) {
        MatchesWsDto matchesWsDto = new MatchesWsDto();
        List<Matches> matchesList = new ArrayList<>();
        for(MatchesDto matchesDto: request.getMatchesDtoList()){
            matchesList.add(matchesRepository.findByRecordId(matchesDto.getRecordId()));

        }
        matchesWsDto.setMatchesDtoList(modelMapper.map(matchesList, List.class));
        matchesWsDto.setBaseUrl(ADMIN_MATCHES);
        return matchesWsDto;
    }

    @PostMapping("/edit")
    @ResponseBody
    public MatchesWsDto handleEdit(@RequestBody MatchesWsDto request) {
        return matchesService.handleEdit(request);
    }

    @PostMapping("/delete")
    @ResponseBody
    public MatchesWsDto deleteMatches(@RequestBody MatchesWsDto matcheswsDto) {
        for (MatchesDto matchesDto : matcheswsDto.getMatchesDtoList()){
            matchesRepository.deleteByRecordId(matchesDto.getRecordId());
        }
        matcheswsDto.setMessage("Data deleted successfully");
        matcheswsDto.setBaseUrl(ADMIN_MATCHES);
        return matcheswsDto;
    }


    @GetMapping("/add")
    @ResponseBody
    public MatchesWsDto addMatches() {
        MatchesWsDto matchesWsDto = new MatchesWsDto();
        matchesWsDto.setMatchesDtoList( modelMapper.map(matchesRepository.findByStatusOrderByIdentifier(true),List.class));
        matchesWsDto.setBaseUrl(ADMIN_MATCHES);
        return matchesWsDto;
    }


}