package com.avitam.fantasy11.web.controllers.admin.leaderBoard;

import com.avitam.fantasy11.api.dto.LeaderBoardDto;
import com.avitam.fantasy11.api.dto.LeaderBoardWsDto;
import com.avitam.fantasy11.api.dto.SearchDto;
import com.avitam.fantasy11.api.service.LeaderBoardService;
import com.avitam.fantasy11.model.Contest;
import com.avitam.fantasy11.model.Language;
import com.avitam.fantasy11.model.LeaderBoard;
import com.avitam.fantasy11.repository.LeaderBoardRepository;
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
@RequestMapping("/admin/leaderBoard")
public class LeaderBoardController extends BaseController {

    @Autowired
    private LeaderBoardRepository leaderBoardRepository;
    @Autowired
    private LeaderBoardService leaderBoardService;
    @Autowired
    ModelMapper modelMapper;

    private static final String ADMIN_LEADERBOARD="/admin/leaderBoard";

    @PostMapping
    @ResponseBody
    public LeaderBoardWsDto getAllLeaderBoard(@RequestBody LeaderBoardWsDto leaderBoardWsDto){
        Pageable pageable=getPageable(leaderBoardWsDto.getPage(),leaderBoardWsDto.getSizePerPage(),leaderBoardWsDto.getSortDirection(),leaderBoardWsDto.getSortField());
        LeaderBoardDto leaderBoardDto= CollectionUtils.isNotEmpty(leaderBoardWsDto.getLeaderBoardDtoList())?leaderBoardWsDto.getLeaderBoardDtoList().get(0) : new LeaderBoardDto() ;
        LeaderBoard leaderBoard = modelMapper.map(leaderBoardDto, LeaderBoard.class);
        Page<LeaderBoard>page=isSearchActive(leaderBoard)!=null ? leaderBoardRepository.findAll(Example.of(leaderBoard),pageable) : leaderBoardRepository.findAll(pageable);
        leaderBoardWsDto.setLeaderBoardDtoList(modelMapper.map(page.getContent(), List.class));
        leaderBoardWsDto.setBaseUrl(ADMIN_LEADERBOARD);
        leaderBoardWsDto.setTotalPages(page.getTotalPages());
        leaderBoardWsDto.setTotalRecords(page.getTotalElements());
        return leaderBoardWsDto;
    }

    @GetMapping("/get")
    @ResponseBody
    public LeaderBoardWsDto getActiveData(){
        LeaderBoardWsDto leaderBoardWsDto=new LeaderBoardWsDto();
        List<LeaderBoard> leaderBoard=leaderBoardRepository.findByStatusOrderByIdentifier(true);
        leaderBoardWsDto.setLeaderBoardDtoList(modelMapper.map(leaderBoard, List.class));
        leaderBoardWsDto.setBaseUrl(ADMIN_LEADERBOARD);
        return leaderBoardWsDto;
    }
    @PostMapping("/getedit")
    @ResponseBody
    public LeaderBoardWsDto editLeaderBoard(@RequestBody LeaderBoardWsDto request) {
        LeaderBoardWsDto leaderBoardWsDto=new LeaderBoardWsDto();
        LeaderBoard leaderBoard=leaderBoardRepository.findByRecordId(request.getLeaderBoardDtoList().get(0).getRecordId());
        leaderBoardWsDto.setLeaderBoardDtoList(List.of(modelMapper.map(leaderBoard, LeaderBoardDto.class)));
        leaderBoardWsDto.setBaseUrl(ADMIN_LEADERBOARD);
        return leaderBoardWsDto;
    }

    @PostMapping("/edit")
    @ResponseBody
    public LeaderBoardWsDto handleEdit(@RequestBody LeaderBoardWsDto request) {

        return leaderBoardService.handleEdit(request);
    }


    @PostMapping("/delete")
    @ResponseBody
    public LeaderBoardWsDto deleteLeaderBoard(@RequestBody LeaderBoardWsDto leaderBoardWsDto) {
        for (LeaderBoardDto data : leaderBoardWsDto.getLeaderBoardDtoList()) {
              leaderBoardRepository.deleteByRecordId(data.getRecordId());
        }
        leaderBoardWsDto.setMessage("Data deleted Successfully");
        leaderBoardWsDto.setBaseUrl(ADMIN_LEADERBOARD);
        return leaderBoardWsDto;
    }

    @GetMapping("/getAdvancedSearch")
    @ResponseBody
    public List<SearchDto> getSearchAttributes() {
        return getGroupedParentAndChildAttributes(new LeaderBoard());
    }
    
}
