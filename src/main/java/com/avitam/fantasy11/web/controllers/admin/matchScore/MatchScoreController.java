package com.avitam.fantasy11.web.controllers.admin.matchScore;

import com.avitam.fantasy11.api.dto.MatchScoreDto;

import com.avitam.fantasy11.api.service.MatchScoreService;
import com.avitam.fantasy11.core.service.CoreService;
import com.avitam.fantasy11.form.MatchScoreForm;
import com.avitam.fantasy11.form.TournamentForm;
import com.avitam.fantasy11.model.*;
import com.avitam.fantasy11.web.controllers.BaseController;
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

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin/matchScore")
public class MatchScoreController extends BaseController {
    @Autowired
    private MatchesRepository matchesRepository;
    @Autowired
    private MatchScoreRepository matchScoreRepository;
    @Autowired
    private CoreService coreService;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private MatchScoreService matchScoreService;

    public static final String ADMIN_MATCHSCORE = "/admin/matchScore";
    @PostMapping
    @ResponseBody
    public MatchScoreDto getAllScripts(@RequestBody MatchScoreDto matchScoreDto){
        Pageable pageable=getPageable(matchScoreDto.getPage(),matchScoreDto.getSizePerPage(),matchScoreDto.getSortDirection(),matchScoreDto.getSortField());
        MatchScore matchScore=matchScoreDto.getMatchScore();
        Page<MatchScore> page=isSearchActive(matchScore) !=null ? matchScoreRepository.findAll(Example.of(matchScore),pageable) : matchScoreRepository.findAll(pageable);
        matchScoreDto.setMatchScoreList(page.getContent());
        matchScoreDto.setBaseUrl(ADMIN_MATCHSCORE);
        matchScoreDto.setTotalPages(page.getTotalPages());
        matchScoreDto.setTotalRecords(page.getTotalElements());
        return matchScoreDto;
    }

    @GetMapping("/get")
    @ResponseBody
    public MatchScoreDto matchScoreDto(){
        MatchScoreDto matchScoreDto=new MatchScoreDto();
        matchScoreDto.setMatchScoreList(matchScoreRepository.findByStatusOrderByIdentifier(true));
        matchScoreDto.setBaseUrl(ADMIN_MATCHSCORE);
        return matchScoreDto;
    }

    @GetMapping("/edit")
    @ResponseBody
    public MatchScoreDto edit (@RequestBody MatchScoreDto request) {
        MatchScoreDto matchScoreDto = new MatchScoreDto();
        matchScoreDto.setBaseUrl(ADMIN_MATCHSCORE);
        MatchScore matchScore = matchScoreRepository.findByRecordId(request.getRecordId());
        matchScoreDto.setMatchScore(matchScore);
        return matchScoreDto;
    }


    @PostMapping("/edit")
    @ResponseBody
    public  MatchScoreDto handleEdit(@RequestBody MatchScoreDto request) {
        return matchScoreService.handleEdit(request);
    }



    @GetMapping("/add")
    @ResponseBody
    public MatchScoreDto addScript(@RequestBody MatchScoreDto request) {
        MatchScoreDto matchScoreDto = new MatchScoreDto();
        matchScoreDto.setBaseUrl(ADMIN_MATCHSCORE);
        matchScoreDto.setMatchScoreList(matchScoreRepository.findByStatusOrderByIdentifier(true));
        return matchScoreDto;
    }

    @GetMapping("/delete")
    @ResponseBody
    public MatchScoreDto deleteScript(@RequestBody MatchScoreDto matchScoreDto) {
        for (String id : matchScoreDto.getRecordId().split(",")) {
            matchScoreRepository.deleteById(new ObjectId(id));
        }
        matchScoreDto.setMessage("Data deleted Successfully");
        matchScoreDto.setBaseUrl(ADMIN_MATCHSCORE);
        return matchScoreDto;
    }
}

