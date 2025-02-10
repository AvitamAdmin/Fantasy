package com.avitam.fantasy11.web.controllers.admin.pointsMaster;

import com.avitam.fantasy11.api.dto.PointsMasterDto;
import com.avitam.fantasy11.api.dto.PointsMasterWsDto;
import com.avitam.fantasy11.api.dto.SearchDto;
import com.avitam.fantasy11.api.service.PointsMasterService;
import com.avitam.fantasy11.model.LeaderBoard;
import com.avitam.fantasy11.model.PointsMaster;
import com.avitam.fantasy11.repository.PointsMasterRepository;
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
@RequestMapping("/admin/pointsMaster")
public class PointsMasterController extends BaseController {

    @Autowired
    private PointsMasterRepository pointsMasterRepository;
    @Autowired
    private PointsMasterService pointsMasterService;
    @Autowired
    private ModelMapper modelMapper;

    private static final String ADMIN_POINTSMASTER = "/admin/pointsMaster";

    @PostMapping
    @ResponseBody
    public PointsMasterWsDto getAll(@RequestBody PointsMasterWsDto pointsMasterWsDto) {

        Pageable pageable = getPageable(pointsMasterWsDto.getPage(), pointsMasterWsDto.getSizePerPage(), pointsMasterWsDto.getSortDirection(), pointsMasterWsDto.getSortField());
        PointsMasterDto pointsMasterDto = CollectionUtils.isNotEmpty(pointsMasterWsDto.getPointsMasterDtos()) ? pointsMasterWsDto.getPointsMasterDtos().get(0) : new PointsMasterDto();
        PointsMaster pointsMaster = modelMapper.map(pointsMasterDto, PointsMaster.class);
        Page<PointsMaster> page = isSearchActive(pointsMaster) != null ? pointsMasterRepository.findAll(Example.of(pointsMaster), pageable) : pointsMasterRepository.findAll(pageable);
        pointsMasterWsDto.setPointsMasterDtos(modelMapper.map(page.getContent(), List.class));
        pointsMasterWsDto.setTotalPages(page.getTotalPages());
        pointsMasterWsDto.setTotalRecords(page.getTotalElements());
        pointsMasterWsDto.setBaseUrl(ADMIN_POINTSMASTER);
        return pointsMasterWsDto;
    }

    @GetMapping("/get")
    @ResponseBody
    public PointsMasterWsDto getActivePointsMaster() {
        PointsMasterWsDto pointsMasterWsDto = new PointsMasterWsDto();
        pointsMasterWsDto.setBaseUrl(ADMIN_POINTSMASTER);
        pointsMasterWsDto.setPointsMasterDtos(modelMapper.map(pointsMasterRepository.findByStatusOrderByIdentifier(true), List.class));
        return pointsMasterWsDto;
    }

    @PostMapping("/getedit")
    @ResponseBody
    public PointsMasterWsDto editPointsMaster(@RequestBody PointsMasterWsDto request) {
        PointsMasterWsDto pointsMasterWsDto = new PointsMasterWsDto();
        pointsMasterWsDto.setBaseUrl(ADMIN_POINTSMASTER);
        PointsMaster pointsMaster = pointsMasterRepository.findByRecordId(request.getPointsMasterDtos().get(0).getRecordId());
        if (pointsMaster != null) {
            pointsMasterWsDto.setPointsMasterDtos(List.of(modelMapper.map(pointsMaster, PointsMasterDto.class)));
        }
        return pointsMasterWsDto;
    }

    @PostMapping("/edit")
    @ResponseBody
    public PointsMasterWsDto handleEdit(@RequestBody PointsMasterWsDto request) {
        return pointsMasterService.handleEdit(request);
    }


    @PostMapping("/delete")
    @ResponseBody
    public PointsMasterWsDto delete(@RequestBody PointsMasterWsDto pointsMasterWsDto) {
        for (PointsMasterDto pointsMasterDto : pointsMasterWsDto.getPointsMasterDtos()) {
            pointsMasterRepository.deleteByRecordId(pointsMasterDto.getRecordId());
        }
        pointsMasterWsDto.setMessage("Data deleted Successfully");
        pointsMasterWsDto.setBaseUrl(ADMIN_POINTSMASTER);
        pointsMasterWsDto.setRedirectUrl("/admin/pointsMaster");
        return pointsMasterWsDto;
    }
    @GetMapping("/getAdvancedSearch")
    @ResponseBody

    public List<SearchDto> getSearchAttributes() {
        return getGroupedParentAndChildAttributes(new PointsMaster());
    }
}
