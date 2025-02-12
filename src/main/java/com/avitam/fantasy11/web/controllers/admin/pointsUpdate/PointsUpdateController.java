package com.avitam.fantasy11.web.controllers.admin.pointsUpdate;

import com.avitam.fantasy11.api.dto.PointsUpdateDto;
import com.avitam.fantasy11.api.dto.PointsUpdateWsDto;
import com.avitam.fantasy11.api.dto.SearchDto;
import com.avitam.fantasy11.api.service.PointsUpdateService;
import com.avitam.fantasy11.model.PointsUpdate;
import com.avitam.fantasy11.repository.PointsUpdateRepository;
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
@RequestMapping("/admin/pointsUpdate")
public class PointsUpdateController extends BaseController {

    private static final String ADMIN_POINTSUPDATE = "/admin/pointsUpdate";
    @Autowired
    private PointsUpdateRepository pointsUpdateRepository;
    @Autowired
    private PointsUpdateService pointsUpdateService;
    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    @ResponseBody
    public PointsUpdateWsDto getAllPointsUpdate(@RequestBody PointsUpdateWsDto pointsUpdateWsDto) {

        Pageable pageable = getPageable(pointsUpdateWsDto.getPage(), pointsUpdateWsDto.getSizePerPage(), pointsUpdateWsDto.getSortDirection(), pointsUpdateWsDto.getSortField());
        PointsUpdateDto pointsUpdateDto = CollectionUtils.isNotEmpty(pointsUpdateWsDto.getPointsUpdateDtoList()) ? pointsUpdateWsDto.getPointsUpdateDtoList().get(0) : new PointsUpdateDto();
        PointsUpdate pointsUpdate = modelMapper.map(pointsUpdateDto, PointsUpdate.class);
        Page<PointsUpdate> page = isSearchActive(pointsUpdate) == null ? pointsUpdateRepository.findAll(Example.of(pointsUpdate), pageable) : pointsUpdateRepository.findAll(pageable);
        pointsUpdateWsDto.setPointsUpdateDtoList(modelMapper.map(page.getContent(), List.class));
        pointsUpdateWsDto.setTotalPages(page.getTotalPages());
        pointsUpdateWsDto.setTotalRecords(page.getTotalElements());
        pointsUpdateWsDto.setBaseUrl(ADMIN_POINTSUPDATE);
        return pointsUpdateWsDto;
    }

    @GetMapping("/get")
    @ResponseBody
    public PointsUpdateWsDto getActivePointsUpdate() {
        PointsUpdateWsDto pointsUpdateWsDto = new PointsUpdateWsDto();
        pointsUpdateWsDto.setPointsUpdateDtoList(modelMapper.map(pointsUpdateRepository.findByStatusOrderByIdentifier(true), List.class));
        pointsUpdateWsDto.setBaseUrl(ADMIN_POINTSUPDATE);
        return pointsUpdateWsDto;
    }

    @PostMapping("/getedit")
    @ResponseBody
    public PointsUpdateWsDto editPointsUpdate(@RequestBody PointsUpdateWsDto request) {
        PointsUpdateWsDto pointsUpdateWsDto = new PointsUpdateWsDto();
        PointsUpdate pointsUpdate = pointsUpdateRepository.findByRecordId(request.getPointsUpdateDtoList().get(0).getRecordId());
        if (pointsUpdate != null) {
            pointsUpdateWsDto.setPointsUpdateDtoList(List.of(modelMapper.map(pointsUpdate, PointsUpdateDto.class)));
        }
        pointsUpdateWsDto.setBaseUrl(ADMIN_POINTSUPDATE);
        return pointsUpdateWsDto;
    }

    @PostMapping("/edit")
    @ResponseBody
    public PointsUpdateWsDto handleEdit(@RequestBody PointsUpdateWsDto request) {
        return pointsUpdateService.handleEdit(request);
    }


    @PostMapping("/delete")
    @ResponseBody
    public PointsUpdateWsDto deletePointsUpdate(@RequestBody PointsUpdateWsDto pointsUpdateWsDto) {
        for (PointsUpdateDto data : pointsUpdateWsDto.getPointsUpdateDtoList()) {
            pointsUpdateRepository.deleteByRecordId(data.getRecordId());
        }
        pointsUpdateWsDto.setMessage("Data deleted Successfully");
        pointsUpdateWsDto.setBaseUrl(ADMIN_POINTSUPDATE);
        return pointsUpdateWsDto;
    }

    @GetMapping("/getAdvancedSearch")
    @ResponseBody

    public List<SearchDto> getSearchAttributes() {
        return getGroupedParentAndChildAttributes(new PointsUpdate());
    }
}
