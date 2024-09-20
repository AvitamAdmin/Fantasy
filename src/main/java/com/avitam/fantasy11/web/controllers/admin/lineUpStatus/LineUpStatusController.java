package com.avitam.fantasy11.web.controllers.admin.lineUpStatus;

import com.avitam.fantasy11.api.dto.LineUpStatusDto;
import com.avitam.fantasy11.api.service.LineUpStatusService;
import com.avitam.fantasy11.model.LineUpStatus;
import com.avitam.fantasy11.model.LineUpStatusRepository;
import com.avitam.fantasy11.web.controllers.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/lineupStatus")
public class LineUpStatusController extends BaseController {

    private static final String ADMIN_LINEUP_STATUS = "/admin/lineupStatus";
    @Autowired
    private LineUpStatusRepository lineUpStatusRepository;

    @Autowired
    LineUpStatusService lineUpStatusService;

    @PostMapping
    @ResponseBody
    public LineUpStatusDto getAll(@RequestBody LineUpStatusDto lineUpStatusDto) {
        Pageable pageable = getPageable(lineUpStatusDto.getPage(), lineUpStatusDto.getSizePerPage(), lineUpStatusDto.getSortDirection(), lineUpStatusDto.getSortField());
        LineUpStatus lineUpStatus = lineUpStatusDto.getLineUpStatus();
        Page<LineUpStatus> page = isSearchActive(lineUpStatus) != null ? lineUpStatusRepository.findAll(org.springframework.data.domain.Example.of(lineUpStatus), pageable) : lineUpStatusRepository.findAll(pageable);
        lineUpStatusDto.setLineUpStatusList(page.getContent());
        lineUpStatusDto.setTotalPages(page.getTotalPages());
        lineUpStatusDto.setTotalRecords(page.getTotalElements());
        lineUpStatusDto.setBaseUrl(ADMIN_LINEUP_STATUS);
        return lineUpStatusDto;
    }

    @GetMapping("/get")
    @ResponseBody
    public LineUpStatusDto getLineUpStatus() {
        LineUpStatusDto lineUpStatusDto = new LineUpStatusDto();
        lineUpStatusDto.setLineUpStatusList(lineUpStatusRepository.findByStatusOrderByIdentifier(true));
        lineUpStatusDto.setBaseUrl(ADMIN_LINEUP_STATUS);
        return lineUpStatusDto;
    }


    @PostMapping("/getedit")
    @ResponseBody
    public LineUpStatusDto editLineupStatus(@RequestBody LineUpStatusDto request) {
        LineUpStatusDto lineUpStatusDto = new LineUpStatusDto();
        LineUpStatus lineUpStatus = lineUpStatusRepository.findByRecordId(request.getRecordId());
        lineUpStatusDto.setLineUpStatus(lineUpStatus);
        lineUpStatusDto.setBaseUrl(ADMIN_LINEUP_STATUS);
        return lineUpStatusDto;
    }

    @PostMapping("/edit")
    @ResponseBody
    public LineUpStatusDto handleEdit(@RequestBody LineUpStatusDto request) {
        return lineUpStatusService.handleEdit(request);
    }

    @GetMapping("/add")
    @ResponseBody
    public LineUpStatusDto addLineUpStatus() {
        LineUpStatusDto lineUpStatusDto = new LineUpStatusDto();
        lineUpStatusDto.setLineUpStatusList(lineUpStatusRepository.findByStatusOrderByIdentifier(true));
        lineUpStatusDto.setBaseUrl(ADMIN_LINEUP_STATUS);
        return lineUpStatusDto;
    }

    @PostMapping("/delete")
    @ResponseBody
    public LineUpStatusDto deleteLineupStatus(@RequestBody LineUpStatusDto lineUpStatusDto) {
        for (String id : lineUpStatusDto.getRecordId().split(",")) {
            lineUpStatusRepository.deleteByRecordId(id);
        }
        lineUpStatusDto.setMessage("Data deleted Successfully");
        lineUpStatusDto.setBaseUrl(ADMIN_LINEUP_STATUS);
        return lineUpStatusDto;

    }
}