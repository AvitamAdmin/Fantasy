package com.avitam.fantasy11.web.controllers.admin.leaderBoard;

import com.avitam.fantasy11.api.dto.LeaderBoardDto;
import com.avitam.fantasy11.api.service.LeaderBoardService;
import com.avitam.fantasy11.model.*;
import com.avitam.fantasy11.web.controllers.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/leaderBoard")
public class LeaderBoardController extends BaseController {

    @Autowired
    private LeaderBoardRepository leaderBoardRepository;
    @Autowired
    private LeaderBoardService leaderBoardService;

    @PostMapping
    @ResponseBody
    public LeaderBoardDto getAll(LeaderBoardDto leaderBoardDto){
        Pageable pageable=getPageable(leaderBoardDto.getPage(),leaderBoardDto.getSizePerPage(),leaderBoardDto.getSortDirection(),leaderBoardDto.getSortField());
        LeaderBoard leaderBoard=leaderBoardDto.getLeaderBoard();
        Page<LeaderBoard>page=isSearchActive(leaderBoard)!=null ? leaderBoardRepository.findAll(Example.of(leaderBoard),pageable) : leaderBoardRepository.findAll(pageable);
        leaderBoardDto.setLeaderBoardList(page.getContent());
        leaderBoardDto.setTotalPages(page.getTotalPages());
        leaderBoardDto.setTotalRecords(page.getTotalElements());
        return leaderBoardDto;
    }

    @GetMapping("/edit")
    @ResponseBody
    public LeaderBoardDto editLeaderBoard(@RequestBody LeaderBoardDto request) {
        LeaderBoardDto leaderBoardDto=new LeaderBoardDto();
        LeaderBoard leaderBoard=leaderBoardRepository.findByRecordId(request.getRecordId());
        return leaderBoardDto;
    }

    @PostMapping("/edit")
    @ResponseBody
    public LeaderBoardDto handleEdit(@RequestBody LeaderBoardDto request) {
        return leaderBoardService.handleEdit(request);
    }

    @GetMapping("/add")
    @ResponseBody
    public LeaderBoardDto addLeaderBoard() {
        LeaderBoardDto leaderBoardDto = new LeaderBoardDto();
        leaderBoardDto.setLeaderBoardList(leaderBoardRepository.findOrderStatusByIdentifier(true));
        return leaderBoardDto;
    }

    @GetMapping("/delete")
    @ResponseBody
    public LeaderBoardDto deleteLeaderBoard(@RequestBody LeaderBoardDto leaderBoardDto) {
        for (String id : leaderBoardDto.getRecordId().split(",")) {
              leaderBoardRepository.deleteByRecordId(id);
        }
        return leaderBoardDto;
    }
}
