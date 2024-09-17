package com.avitam.fantasy11.web.controllers.admin.pointsMaster;

import com.avitam.fantasy11.api.dto.PointsMasterDto;
import com.avitam.fantasy11.api.service.PointsMasterService;
import com.avitam.fantasy11.core.service.CoreService;
import com.avitam.fantasy11.form.PointsMasterForm;
import com.avitam.fantasy11.model.*;
import com.avitam.fantasy11.web.controllers.BaseController;
import org.bson.types.ObjectId;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin/pointsMaster")
public class PointsMasterController extends BaseController {

    @Autowired
    private MatchTypeRepository matchTypeRepository;
    @Autowired
    private PointsMasterRepository pointsMasterRepository;
    @Autowired
    private PointsMasterService pointsMasterService;
    @Autowired
    private CoreService coreService;
    @Autowired
    private ModelMapper modelMapper;
    public static final String ADMIN_POINTSMASTER = "/admin/pointsMaster";

    @PostMapping
    @ResponseBody
    public PointsMasterDto getAll(PointsMasterDto pointsMasterDto) {

        Pageable pageable = getPageable(pointsMasterDto.getPage(), pointsMasterDto.getSizePerPage(), pointsMasterDto.getSortDirection(), pointsMasterDto.getSortField());
        PointsMaster pointsMaster = pointsMasterDto.getPointsMaster();
        Page<PointsMaster> page = isSearchActive(pointsMaster)!=null ? pointsMasterRepository.findAll(Example.of(pointsMaster), pageable) : pointsMasterRepository.findAll(pageable);
        pointsMasterDto.setPointsMasterList(page.getContent());
        pointsMasterDto.setTotalPages(page.getTotalPages());
        pointsMasterDto.setTotalRecords(page.getTotalElements());
        pointsMasterDto.setBaseUrl(ADMIN_POINTSMASTER);
        return pointsMasterDto;
    }

    @GetMapping("/get")
    @ResponseBody
    public PointsMasterDto getActivePointsMaster(){
        PointsMasterDto pointsMasterDto = new PointsMasterDto();
        pointsMasterDto.setPointsMasterList(pointsMasterRepository.findByStatusOrderByIdentifier(true));
        pointsMasterDto.setBaseUrl(ADMIN_POINTSMASTER);
        return pointsMasterDto;
    }

    @GetMapping("/edit")
    @ResponseBody
    public PointsMasterDto editPointsMaster(@RequestBody PointsMasterDto request) {
        PointsMasterDto pointsMasterDto = new PointsMasterDto();
        PointsMaster pointsMaster = pointsMasterRepository.findByRecordId(request.getRecordId());
        pointsMasterDto.setPointsMaster(pointsMaster);
        pointsMasterDto.setBaseUrl(ADMIN_POINTSMASTER);

        return pointsMasterDto;
    }

    @PostMapping("/edit")
    @ResponseBody
    public PointsMasterDto handleEdit(@RequestBody PointsMasterDto request) {

        return pointsMasterService.handleEdit(request);
    }

    @GetMapping("/add")
    @ResponseBody
    public PointsMasterDto addPointsMaster(Model model) {

        PointsMasterDto pointsMasterDto = new PointsMasterDto();
        pointsMasterDto.setPointsMasterList(pointsMasterRepository.findByStatusOrderByIdentifier(true));
        pointsMasterDto.setBaseUrl(ADMIN_POINTSMASTER);

        return pointsMasterDto;
    }

    @GetMapping("/delete")
    @ResponseBody
    public PointsMasterDto delete(@RequestBody PointsMasterDto pointsMasterDto) {
        for (String id : pointsMasterDto.getRecordId().split(",")) {
            pointsMasterRepository.deleteByRecordId(id);
        }
        pointsMasterDto.setMessage("Data deleted successfully");
        pointsMasterDto.setBaseUrl(ADMIN_POINTSMASTER);
        return pointsMasterDto;
    }
}
