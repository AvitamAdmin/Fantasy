package com.avitam.fantasy11.web.controllers.admin.matches;


import com.avitam.fantasy11.api.dto.MatchesDto;
import com.avitam.fantasy11.api.service.MatchesService;
import com.avitam.fantasy11.core.service.CoreService;
import com.avitam.fantasy11.model.*;
import com.avitam.fantasy11.web.controllers.BaseController;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/upcomingMatches")
public class UpcomingMatchesController extends BaseController {
    @Autowired
    private MatchesRepository matchesRepository;
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private TournamentRepository tournamentRepository;
    @Autowired
    private SportTypeRepository sportTypeRepository;
    @Autowired
    private ContestRepository contestRepository;
    @Autowired
    private MatchTypeRepository matchTypeRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CoreService coreService;
    @Autowired
    private MainContestRepository mainContestRepository;
    @Autowired
    private MatchesService matchesService;

    private static final String ADMIN_UPCOMINGMATCHES="/admin/upcomingMatches";


    @PostMapping
    @ResponseBody
    public MatchesDto getAllMatches(@RequestBody MatchesDto matchesDto){
        Pageable pageable=getPageable(matchesDto.getPage(),matchesDto.getSizePerPage(),matchesDto.getSortDirection(),matchesDto.getSortField());
        Matches matches=matchesDto.getMatches();
        Page<Matches> page=isSearchActive(matches)!=null ? matchesRepository.findAll(Example.of(matches),pageable) : matchesRepository.findAll(pageable);
        matchesDto.setMatchesList(page.getContent());
        matchesDto.setTotalPages(page.getTotalPages());
        matchesDto.setTotalRecords(page.getTotalElements());
        matchesDto.setBaseUrl(ADMIN_UPCOMINGMATCHES);
        return matchesDto;
    }

    @GetMapping("/get")
    @ResponseBody
    public MatchesDto getActiveMatches() {
        MatchesDto matchesDto=new MatchesDto();
        matchesDto.setMatchesList(matchesRepository.findByStatusAndEventOrderByIdentifier(true,"Upcoming"));
        matchesDto.setBaseUrl(ADMIN_UPCOMINGMATCHES);
        return matchesDto;
    }

    @PostMapping("/getedit")
    @ResponseBody
    public MatchesDto editMatches(@RequestBody MatchesDto request) {

        MatchesDto matchesDto=new MatchesDto();
        Matches matches= matchesRepository.findByRecordId(request.getRecordId());
        matchesDto.setMatches(matches);
        matchesDto.setBaseUrl(ADMIN_UPCOMINGMATCHES);
        return matchesDto;
    }

    @PostMapping("/edit")
    @ResponseBody
    public MatchesDto handleEdit(@RequestBody MatchesDto request){
        int flag=4;
        return matchesService.handleEdit(request,flag);
    }

    @GetMapping("/add")
    @ResponseBody
    public MatchesDto addMatches() {
        MatchesDto matchesDto=new MatchesDto();
        matchesDto.setMatchesList(matchesRepository.findByStatusAndEventOrderByIdentifier(true,"Upcoming"));
        matchesDto.setBaseUrl(ADMIN_UPCOMINGMATCHES);
        return matchesDto;
    }

    @PostMapping("/delete")
    @ResponseBody
    public MatchesDto deleteMatches(@RequestBody MatchesDto matchesDto) {
        for (String id : matchesDto.getRecordId().split(",")) {
            matchesRepository.deleteByRecordId(id);
        }
        matchesDto.setMessage("Data delete successfully");
        matchesDto.setBaseUrl(ADMIN_UPCOMINGMATCHES);
        return matchesDto;
    }
}