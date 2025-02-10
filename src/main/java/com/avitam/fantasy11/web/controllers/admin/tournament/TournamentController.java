package com.avitam.fantasy11.web.controllers.admin.tournament;

import com.avitam.fantasy11.api.dto.SearchDto;
import com.avitam.fantasy11.api.dto.TeamDto;
import com.avitam.fantasy11.api.dto.TournamentDto;
import com.avitam.fantasy11.api.dto.TournamentWsDto;
import com.avitam.fantasy11.api.service.TournamentService;
import com.avitam.fantasy11.model.LeaderBoard;
import com.avitam.fantasy11.model.Team;
import com.avitam.fantasy11.model.Tournament;
import com.avitam.fantasy11.repository.TournamentRepository;
import com.avitam.fantasy11.web.controllers.BaseController;
import org.apache.commons.collections4.CollectionUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin/tournament")
public class TournamentController extends BaseController {

    @Autowired
    private TournamentService tournamentService;
    @Autowired
    private TournamentRepository tournamentRepository;
    @Autowired
    private ModelMapper modelMapper;
    private static final String ADMIN_TOURNAMENT="/admin/tournament";

    @PostMapping
    @ResponseBody
    public TournamentWsDto getAllTournament(@RequestBody TournamentWsDto tournamentWsDto){

        Pageable pageable=getPageable(tournamentWsDto.getPage(),tournamentWsDto.getSizePerPage(),tournamentWsDto.getSortDirection(),tournamentWsDto.getSortField());
        TournamentDto tournamentDto= CollectionUtils.isNotEmpty(tournamentWsDto.getTournamentDtoList())?tournamentWsDto.getTournamentDtoList() .get(0) : new TournamentDto() ;
        Tournament tournament = modelMapper.map(tournamentDto, Tournament.class);
        Page<Tournament> page=isSearchActive(tournament)!=null ? tournamentRepository.findAll(Example.of(tournament),pageable) : tournamentRepository.findAll(pageable);
        tournamentWsDto.setTournamentDtoList(modelMapper.map(page.getContent(), List.class));
        tournamentWsDto.setBaseUrl(ADMIN_TOURNAMENT);
        tournamentWsDto.setTotalPages(page.getTotalPages());
        tournamentWsDto.setTotalRecords(page.getTotalElements());
        return tournamentWsDto;
    }
    @GetMapping("/get")
    @ResponseBody
    public TournamentWsDto getActiveTournament(){
        TournamentWsDto tournamentWsDto = new TournamentWsDto();
        tournamentWsDto.setTournamentDtoList(modelMapper.map(tournamentRepository.findByStatusOrderByIdentifier(true),List.class));
        tournamentWsDto.setBaseUrl(ADMIN_TOURNAMENT);
        return tournamentWsDto;

    }
    @PostMapping("/getedit")
    @ResponseBody
    public TournamentWsDto editTournament (@RequestBody TournamentWsDto request){
        List<Tournament> tournaments = new ArrayList<>();
        for (TournamentDto tournamentDto : request.getTournamentDtoList()) {
            Tournament tournament = tournamentRepository.findByRecordId(tournamentDto.getRecordId());
            tournaments.add(tournament);
        }
        request.setTournamentDtoList(modelMapper.map(tournaments, List.class));
        return request;
    }

    @PostMapping("/edit")
    @ResponseBody
    public  TournamentWsDto handleEdit(@RequestBody TournamentWsDto request) {

        return tournamentService.handleEdit(request);
    }


    @PostMapping("/delete")
    @ResponseBody
    public TournamentWsDto deleteTournament (@RequestBody TournamentWsDto tournamentWsDto) {
        for (TournamentDto id : tournamentWsDto.getTournamentDtoList()) {
            tournamentRepository.deleteByRecordId(id.getRecordId());
        }
        tournamentWsDto.setMessage("Data deleted Successfully");
        tournamentWsDto.setBaseUrl(ADMIN_TOURNAMENT);
        return tournamentWsDto;
    }
    @GetMapping("/getAdvancedSearch")
    @ResponseBody

    public List<SearchDto> getSearchAttributes() {
        return getGroupedParentAndChildAttributes(new Tournament());
    }


}