package com.avitam.fantasy11.web.controllers.admin.matchScore;

import com.avitam.fantasy11.api.dto.MatchScoreDto;
import com.avitam.fantasy11.api.dto.MatchScoreWsDto;
import com.avitam.fantasy11.api.service.MatchScoreService;
import com.avitam.fantasy11.core.service.CoreService;
import com.avitam.fantasy11.model.MatchScore;
import com.avitam.fantasy11.repository.MatchScoreRepository;
import com.avitam.fantasy11.repository.MatchesRepository;
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
    public MatchScoreWsDto getAllMatchScore(@RequestBody MatchScoreWsDto matchScoreWsDto) {
        Pageable pageable = getPageable(matchScoreWsDto.getPage(), matchScoreWsDto.getSizePerPage(), matchScoreWsDto.getSortDirection(), matchScoreWsDto.getSortField());
        MatchScoreDto matchScoreDto = CollectionUtils.isNotEmpty(matchScoreWsDto.getMatchScoreDtoList()) ? matchScoreWsDto.getMatchScoreDtoList().get(0) : new MatchScoreDto();
        MatchScore matchScore = modelMapper.map(matchScoreDto, MatchScore.class);
        Page<MatchScore> page = isSearchActive(matchScore) == null ? matchScoreRepository.findAll(Example.of(matchScore), pageable) : matchScoreRepository.findAll(pageable);
        matchScoreWsDto.setMatchScoreDtoList(modelMapper.map(page.getContent(), List.class));
        matchScoreWsDto.setBaseUrl(ADMIN_MATCHSCORE);
        matchScoreWsDto.setTotalPages(page.getTotalPages());
        matchScoreWsDto.setTotalRecords(page.getTotalElements());
        return matchScoreWsDto;
    }

    @GetMapping("/get")
    @ResponseBody
    public MatchScoreWsDto matchScoreWsDto() {
        MatchScoreWsDto matchScoreWsDto = new MatchScoreWsDto();
        matchScoreWsDto.setMatchScoreDtoList(modelMapper.map(matchScoreRepository.findByStatusOrderByIdentifier(true), List.class));
        matchScoreWsDto.setBaseUrl(ADMIN_MATCHSCORE);
        return matchScoreWsDto;
    }

    @PostMapping("/getedit")
    @ResponseBody
    public MatchScoreWsDto edit(@RequestBody MatchScoreWsDto request) {
        MatchScoreWsDto matchScoreWsDto = new MatchScoreWsDto();
        matchScoreWsDto.setBaseUrl(ADMIN_MATCHSCORE);
        MatchScore matchScore = matchScoreRepository.findByRecordId(request.getMatchScoreDtoList().get(0).getRecordId());
        matchScoreWsDto.setMatchScoreDtoList(List.of(modelMapper.map(matchScore,MatchScoreDto.class)));
        return matchScoreWsDto;
    }


    @PostMapping("/edit")
    @ResponseBody
    public MatchScoreWsDto handleEdit(@RequestBody MatchScoreWsDto request) {
        return matchScoreService.handleEdit(request);
    }


    @PostMapping("/delete")
    @ResponseBody
    public MatchScoreWsDto deleteMatchScore(@RequestBody MatchScoreWsDto matchScoreWsDto) {
        for (MatchScoreDto matchScoreDto : matchScoreWsDto.getMatchScoreDtoList()) {
            matchScoreRepository.deleteByRecordId(matchScoreDto.getRecordId());
        }
        matchScoreWsDto.setMessage("Data deleted Successfully");
        matchScoreWsDto.setBaseUrl(ADMIN_MATCHSCORE);
        return matchScoreWsDto;
    }
}

