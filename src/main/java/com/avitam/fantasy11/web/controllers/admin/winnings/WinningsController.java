package com.avitam.fantasy11.web.controllers.admin.winnings;

import com.avitam.fantasy11.api.dto.UserTeamWsDto;
import com.avitam.fantasy11.api.dto.UserTeamsDto;
import com.avitam.fantasy11.api.dto.WinningsDto;
import com.avitam.fantasy11.api.dto.WinningsWsDto;
import com.avitam.fantasy11.api.service.WinningsService;
import com.avitam.fantasy11.repository.WinningsRepository;
import com.avitam.fantasy11.web.controllers.BaseController;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/winnings")
public class WinningsController extends BaseController {

    @Autowired
    private WinningsRepository winningsRepository;
    @Autowired
    private WinningsService winningsService;
    @Autowired
    private ModelMapper modelMapper;

    public static final String ADMIN_WINNINGS = "/admin/winnings";



    @GetMapping("/get")
    @ResponseBody
    public WinningsWsDto getActiveWinnings() {
        WinningsWsDto winningsWsDto = new WinningsWsDto();
        winningsWsDto.setWinningsDtoList(modelMapper.map(winningsRepository.findByStatusOrderByIdentifier(true), List.class));
        winningsWsDto.setBaseUrl(ADMIN_WINNINGS);
        return winningsWsDto;
    }


    @PostMapping("/edit")
    @ResponseBody
    public WinningsWsDto handleEdit(@RequestBody WinningsWsDto request)  {
        return winningsService.handleEdit(request);
    }

    @PostMapping("/delete")
    @ResponseBody
    public WinningsWsDto deleteWinnings(@RequestBody WinningsWsDto winningsWsDto) {
        for (WinningsDto data : winningsWsDto.getWinningsDtoList()) {

            winningsRepository.deleteByRecordId(data.getRecordId());
        }
        winningsWsDto.setMessage("Data deleted successfully");
        winningsWsDto.setBaseUrl(ADMIN_WINNINGS);
        return winningsWsDto;
    }

}
