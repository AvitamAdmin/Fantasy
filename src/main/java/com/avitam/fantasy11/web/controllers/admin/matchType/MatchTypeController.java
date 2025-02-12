package com.avitam.fantasy11.web.controllers.admin.matchType;

import com.avitam.fantasy11.api.dto.MatchTypeDto;
import com.avitam.fantasy11.api.dto.MatchTypeWsDto;
import com.avitam.fantasy11.api.dto.SearchDto;
import com.avitam.fantasy11.api.service.MatchTypeService;
import com.avitam.fantasy11.model.MatchType;
import com.avitam.fantasy11.repository.MatchTypeRepository;
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
@RequestMapping("/admin/matchType")
public class MatchTypeController extends BaseController {

    private static final String ADMIN_MATCHTYPE = "/admin/matchType";
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    private MatchTypeRepository matchTypeRepository;
    @Autowired
    private MatchTypeService matchTypeService;

    @PostMapping
    @ResponseBody
    public MatchTypeWsDto getAllMatchTypes(@RequestBody MatchTypeWsDto matchTypeWsDto) {
        Pageable pageable = getPageable(matchTypeWsDto.getPage(), matchTypeWsDto.getSizePerPage(), matchTypeWsDto.getSortDirection(), matchTypeWsDto.getSortField());
        MatchTypeDto matchTypeDto = CollectionUtils.isNotEmpty(matchTypeWsDto.getMatchTypeDtoList()) ? matchTypeWsDto.getMatchTypeDtoList().get(0) : new MatchTypeDto();
        MatchType matchType = modelMapper.map(matchTypeDto, MatchType.class);
        Page<MatchType> page = isSearchActive(matchTypeWsDto) != null ? matchTypeRepository.findAll(Example.of(matchType), pageable) : matchTypeRepository.findAll(pageable);
        matchTypeWsDto.setMatchTypeDtoList(modelMapper.map(page.getContent(), List.class));
        matchTypeWsDto.setTotalPages(page.getTotalPages());
        matchTypeWsDto.setTotalRecords(page.getTotalElements());
        matchTypeWsDto.setBaseUrl(ADMIN_MATCHTYPE);
        return matchTypeWsDto;
    }

    @GetMapping("/get")
    @ResponseBody
    public MatchTypeWsDto getActiveMatchType() {
        MatchTypeWsDto matchTypeWsDto = new MatchTypeWsDto();
        matchTypeWsDto.setBaseUrl(ADMIN_MATCHTYPE);
        matchTypeWsDto.setMatchTypeDtoList(modelMapper.map(matchTypeRepository.findByStatusOrderByIdentifier(true), List.class));
        return matchTypeWsDto;
    }

    @PostMapping("/getedit")
    @ResponseBody
    public MatchTypeWsDto edit(@RequestBody MatchTypeWsDto request) {
        MatchTypeWsDto matchTypeWsDto = new MatchTypeWsDto();
        MatchType matchType = matchTypeRepository.findByRecordId(request.getMatchTypeDtoList().get(0).getRecordId());
        matchTypeWsDto.setMatchTypeDtoList(List.of(modelMapper.map(matchType, MatchTypeDto.class)));
        matchTypeWsDto.setBaseUrl(ADMIN_MATCHTYPE);
        return matchTypeWsDto;
    }

    @PostMapping("/edit")
    @ResponseBody
    public MatchTypeWsDto handleEdit(@RequestBody MatchTypeWsDto request) {

        return matchTypeService.handleEdit(request);
    }


    @PostMapping("/delete")
    @ResponseBody
    public MatchTypeWsDto deleteMatchType(@RequestBody MatchTypeWsDto matchTypeWsDto) {
        for (MatchTypeDto matchTypeDto : matchTypeWsDto.getMatchTypeDtoList()) {
            matchTypeRepository.deleteByRecordId(matchTypeDto.getRecordId());
        }
        matchTypeWsDto.setMessage("Data delete successfully");
        matchTypeWsDto.setBaseUrl(ADMIN_MATCHTYPE);
        return matchTypeWsDto;
    }

    @GetMapping("/getAdvancedSearch")
    @ResponseBody

    public List<SearchDto> getSearchAttributes() {
        return getGroupedParentAndChildAttributes(new MatchType());
    }
}
