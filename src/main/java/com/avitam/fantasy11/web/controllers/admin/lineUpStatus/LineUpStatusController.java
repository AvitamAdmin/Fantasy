package com.avitam.fantasy11.web.controllers.admin.lineUpStatus;

import com.avitam.fantasy11.api.dto.LineUpStatusDto;
import com.avitam.fantasy11.api.dto.LineUpStatusWsDto;
import com.avitam.fantasy11.api.service.LineUpStatusService;
import com.avitam.fantasy11.model.LineUpStatus;
import com.avitam.fantasy11.repository.LineUpStatusRepository;
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
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin/lineupStatus")
public class LineUpStatusController extends BaseController {

    private static final String ADMIN_LINEUP_STATUS = "/admin/lineupStatus";
    @Autowired
    private LineUpStatusRepository lineUpStatusRepository;

    @Autowired
    LineUpStatusService lineUpStatusService;
    @Autowired
    ModelMapper modelMapper;

    @PostMapping
    @ResponseBody
    public LineUpStatusWsDto getAll(@RequestBody LineUpStatusWsDto lineUpStatusWsDto) {
        Pageable pageable = getPageable(lineUpStatusWsDto.getPage(), lineUpStatusWsDto.getSizePerPage(), lineUpStatusWsDto.getSortDirection(), lineUpStatusWsDto.getSortField());
        LineUpStatusDto lineUpStatusDto = CollectionUtils.isNotEmpty(lineUpStatusWsDto.getLineUpStatusDtoList()) ? lineUpStatusWsDto.getLineUpStatusDtoList().get(0) : new LineUpStatusDto();
        LineUpStatus lineUpStatus = modelMapper.map(lineUpStatusDto, LineUpStatus.class);
        Page<LineUpStatus> page = isSearchActive(lineUpStatus) == null ? lineUpStatusRepository.findAll(Example.of(lineUpStatus),pageable) : lineUpStatusRepository.findAll(pageable);
        lineUpStatusWsDto.setLineUpStatusDtoList(modelMapper.map(page.getContent(),List.class));
        lineUpStatusWsDto.setTotalPages(page.getTotalPages());
        lineUpStatusWsDto.setTotalRecords(page.getTotalElements());
        lineUpStatusWsDto.setBaseUrl(ADMIN_LINEUP_STATUS);
        return lineUpStatusWsDto;
    }

    @GetMapping("/get")
    @ResponseBody
    public LineUpStatusWsDto getLineUpStatus() {
        LineUpStatusWsDto lineUpStatusWsDto = new LineUpStatusWsDto();
        lineUpStatusWsDto.setLineUpStatusDtoList(modelMapper.map(lineUpStatusRepository.findByStatusOrderByIdentifier(true),List.class));
        lineUpStatusWsDto.setBaseUrl(ADMIN_LINEUP_STATUS);
        return lineUpStatusWsDto;
    }

    @PostMapping("/getedit")
    @ResponseBody
    public LineUpStatusWsDto editLineupStatus(@RequestBody LineUpStatusWsDto request) {
        LineUpStatus lineUpStatus = lineUpStatusRepository.findByRecordId(request.getLineUpStatusDtoList().get(0).getRecordId());
        request.setLineUpStatusDtoList(List.of(modelMapper.map(lineUpStatus, LineUpStatusDto.class)));
        request.setBaseUrl(ADMIN_LINEUP_STATUS);
        return request;
    }

    @PostMapping("/edit")
    @ResponseBody
    public LineUpStatusWsDto handleEdit(@RequestBody LineUpStatusWsDto request) {
        return lineUpStatusService.handleEdit(request);
    }

    @PostMapping("/delete")
    @ResponseBody
    public LineUpStatusWsDto deleteLineupStatus(@RequestBody LineUpStatusWsDto lineUpStatusWSDto) {
        for (LineUpStatusDto lineUpStatusDto:lineUpStatusWSDto.getLineUpStatusDtoList()) {
            lineUpStatusRepository.deleteByRecordId(lineUpStatusDto.getRecordId());
        }
        lineUpStatusWSDto.setMessage("Data deleted Successfully");
        lineUpStatusWSDto.setBaseUrl(ADMIN_LINEUP_STATUS);
        return lineUpStatusWSDto;
    }
}