package com.avitam.fantasy11.web.controllers.admin.matchType;

import com.avitam.fantasy11.api.dto.MatchTypeDto;
import com.avitam.fantasy11.api.service.MatchTypeService;
import com.avitam.fantasy11.model.MatchType;
import com.avitam.fantasy11.model.MatchTypeRepository;
import com.avitam.fantasy11.web.controllers.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/matchType")
public class MatchTypeController extends BaseController {

    @Autowired
    private MatchTypeRepository matchTypeRepository;
    @Autowired
    private MatchTypeService matchTypeService;

    private static final String ADMIN_MATCHTYPE="/admin/matchType";

    @PostMapping
    @ResponseBody
    public MatchTypeDto getAllMatchTypes(@RequestBody MatchTypeDto matchTypeDto){
        Pageable pageable=getPageable(matchTypeDto.getPage(),matchTypeDto.getSizePerPage(),matchTypeDto.getSortDirection(),matchTypeDto.getSortField());
        MatchType matchType=matchTypeDto.getMatchType();
        Page<MatchType> page=isSearchActive(matchType) !=null ? matchTypeRepository.findAll(Example.of(matchType),pageable) : matchTypeRepository.findAll(pageable);
        matchTypeDto.setMatchTypeList(page.getContent());
        matchTypeDto.setTotalPages(page.getTotalPages());
        matchTypeDto.setTotalRecords(page.getTotalElements());
        matchTypeDto.setBaseUrl(ADMIN_MATCHTYPE);
        return matchTypeDto;
    }

    @GetMapping("/get")
    @ResponseBody
    public MatchTypeDto getActiveMatchType (){
        MatchTypeDto matchTypeDto=new MatchTypeDto();
        matchTypeDto.setMatchTypeList(matchTypeRepository.findByStatusOrderByIdentifier(true));
        matchTypeDto.setBaseUrl(ADMIN_MATCHTYPE);
        return matchTypeDto;
    }

    @PostMapping("/getedit")
    @ResponseBody
    public MatchTypeDto edit (@RequestBody MatchTypeDto request){

        MatchTypeDto matchTypeDto=new MatchTypeDto();
        MatchType matchType=matchTypeRepository.findByRecordId(request.getRecordId());
        matchTypeDto.setMatchType(matchType);
        matchTypeDto.setBaseUrl(ADMIN_MATCHTYPE);
        return matchTypeDto;
    }

    @PostMapping("/edit")
    @ResponseBody
    public MatchTypeDto handleEdit(@RequestBody MatchTypeDto request) {

        return matchTypeService.handleEdit(request);
    }

    @GetMapping("/add")
    @ResponseBody
    public MatchTypeDto add() {
        MatchTypeDto matchTypeDto=new MatchTypeDto();
        matchTypeDto.setMatchTypeList(matchTypeRepository.findByStatusOrderByIdentifier(true));
        matchTypeDto.setBaseUrl(ADMIN_MATCHTYPE);
        return matchTypeDto;
    }
    @PostMapping("/delete")
    @ResponseBody
    public MatchTypeDto delete(@RequestBody MatchTypeDto matchTypeDto) {
        for (String id : matchTypeDto.getRecordId().split(",")) {
            matchTypeRepository.deleteByRecordId(id);
        }
        matchTypeDto.setMessage("Data delete successfully");
        matchTypeDto.setBaseUrl(ADMIN_MATCHTYPE);
        return matchTypeDto;
    }
}
