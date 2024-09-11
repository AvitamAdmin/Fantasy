package com.avitam.fantasy11.web.controllers.admin.tournament;

import com.avitam.fantasy11.api.dto.TournamentDto;
import com.avitam.fantasy11.api.service.TournamentService;
import com.avitam.fantasy11.model.Tournament;
import com.avitam.fantasy11.model.TournamentRepository;
import com.avitam.fantasy11.web.controllers.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/tournament")
public class TournamentController extends BaseController {

    @Autowired
    private TournamentService tournamentService;
    @Autowired
    private TournamentRepository tournamentRepository;


    @PostMapping
    @ResponseBody
    public TournamentDto getAllTournament(TournamentDto tournamentDto){

        Pageable pageable=getPageable(tournamentDto.getPage(),tournamentDto.getSizePerPage(),tournamentDto.getSortDirection(),tournamentDto.getSortField());
        Tournament tournament=tournamentDto.getTournament();
        Page<Tournament> page=isSearchActive(tournament)!=null ? tournamentRepository.findAll(Example.of(tournament),pageable) : tournamentRepository.findAll(pageable);
        tournamentDto.setTournamentList(page.getContent());
        tournamentDto.setTotalPages(page.getTotalPages());
        tournamentDto.setTotalRecords(page.getTotalElements());
        return tournamentDto;
    }

    @GetMapping("/edit")
    @ResponseBody
    public TournamentDto editTournament (@RequestBody TournamentDto request){
           TournamentDto tournamentDto=new TournamentDto();
           Tournament tournament=tournamentRepository.findByRecordId(request.getRecordId());

        return tournamentDto;
    }

    @PostMapping("/edit")
    @ResponseBody
    public  TournamentDto handleEdit(@RequestBody TournamentDto request) {

        return tournamentService.handleEdit(request);
    }

    @GetMapping("/add")
    @ResponseBody
    public TournamentDto addTournament() {
        TournamentDto tournamentDto = new TournamentDto();
        tournamentDto.setTournamentList(tournamentRepository.findOrderStatusByIdentifier(true));
        return tournamentDto;
    }

    @GetMapping("/delete")
    @ResponseBody
    public TournamentDto delete (@RequestBody TournamentDto tournamentDto) {
        for (String id : tournamentDto.getRecordId().split(",")) {
            tournamentRepository.deleteByRecordId(id);
        }
        return tournamentDto;
    }
}
