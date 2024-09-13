package com.avitam.fantasy11.web.controllers.admin.matches;

import com.avitam.fantasy11.api.dto.AddressDto;
import com.avitam.fantasy11.api.dto.MatchesDto;
import com.avitam.fantasy11.api.service.MatchesService;
import com.avitam.fantasy11.core.service.CoreService;
import com.avitam.fantasy11.form.MatchesForm;
import com.avitam.fantasy11.model.*;
import com.avitam.fantasy11.web.controllers.BaseController;
import org.apache.catalina.mbeans.BaseCatalinaMBean;
import org.bson.types.ObjectId;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
    public MatchesDto getAllModel(MatchesDto matchesDto) {
        Pageable pageable=getPageable(matchesDto.getPage(),matchesDto.getSizePerPage(),matchesDto.getSortDirection(),matchesDto.getSortField());
        Matches matches=matchesDto.getMatches();
        Page<Matches> page=isSearchActive(matches)!=null ? matchesRepository.findAll(Example.of(matches),pageable) : matchesRepository.findAll(pageable);
        matchesDto.setMatchesList(page.getContent());
        matchesDto.setTotalPages(page.getTotalPages());
        matchesDto.setTotalRecords(page.getTotalElements());
        matchesDto.setBaseUrl(ADMIN_MATCHES);
        return matchesDto;
    }

    @GetMapping("/get")
    @ResponseBody
    public MatchesDto getActiveMatchesList() {
        MatchesDto matchesDto = new MatchesDto();
        matchesDto.setMatchesList(matchesRepository.findByStatusOrderByIdentifier(true));
        matchesDto.setBaseUrl(ADMIN_MATCHES);
        return matchesDto;
    }

    @GetMapping("/edit")
    @ResponseBody
    public MatchesDto editMatches(@RequestBody MatchesDto request) {
        MatchesDto matchesDto = new MatchesDto();
        Matches matches = matchesRepository.findByRecordId(request.getRecordId());
        matchesDto.setMatches(matches);
        matchesDto.setBaseUrl(ADMIN_MATCHES);
        return matchesDto;
    }

    @PostMapping("/edit")
    @ResponseBody
    public MatchesDto handleEdit(@RequestBody MatchesDto request) {
        return matchesService.handleEdit(request);
    }

    @GetMapping("/add")
    @ResponseBody
    public MatchesDto addMatches() {
        MatchesDto matchesDto = new MatchesDto();
        matchesDto.setMatchesList(matchesRepository.findByStatusOrderByIdentifier(true));
        matchesDto.setBaseUrl(ADMIN_MATCHES);
        return matchesDto;
    }

    @PostMapping("/delete")
    @ResponseBody
    public MatchesDto deleteMatches(@RequestBody MatchesDto matchesDto) {
        for (String id : matchesDto.getRecordId().split(",")) {
            matchesRepository.deleteByRecordId(id);
        }
        matchesDto.setMessage("Data deleted successfully");
        matchesDto.setBaseUrl(ADMIN_MATCHES);
        return matchesDto;
    }
}