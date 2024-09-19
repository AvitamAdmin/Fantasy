package com.avitam.fantasy11.web.controllers.admin.pointsMaster;

import com.avitam.fantasy11.api.dto.PointsMasterDto;
import com.avitam.fantasy11.api.service.PointsMasterService;
import com.avitam.fantasy11.model.PointsMaster;
import com.avitam.fantasy11.model.PointsMasterRepository;
import com.avitam.fantasy11.web.controllers.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/pointsMaster")
public class PointsMasterController extends BaseController {

    @Autowired
    private PointsMasterRepository pointsMasterRepository;
    @Autowired
    private PointsMasterService pointsMasterService;
    private static final String ADMIN_POINTSMASTER="/admin/pointsMaster";

    @PostMapping
    @ResponseBody
    public PointsMasterDto getAllPointsMaster(@RequestBody PointsMasterDto pointsMasterDto){

        Pageable pageable=getPageable(pointsMasterDto.getPage(),pointsMasterDto.getSizePerPage(),pointsMasterDto.getSortDirection(),pointsMasterDto.getSortField());
        PointsMaster pointsUpdate=pointsMasterDto.getPointsMaster();
        Page<PointsMaster> page=isSearchActive(pointsUpdate) != null ? pointsMasterRepository.findAll(Example.of(pointsUpdate),pageable): pointsMasterRepository.findAll(pageable);
        pointsMasterDto.setPointsMasterList(page.getContent());
        pointsMasterDto.setTotalPages(page.getTotalPages());
        pointsMasterDto.setTotalRecords(page.getTotalElements());
        pointsMasterDto.setBaseUrl(ADMIN_POINTSMASTER);
        return pointsMasterDto;
    }

    @GetMapping("/get")
    @ResponseBody
    public PointsMasterDto getActivePointsMaster() {
        PointsMasterDto pointsMasterDto=new PointsMasterDto();
        pointsMasterDto.setPointsMasterList(pointsMasterRepository.findByStatusOrderByIdentifier(true));
        pointsMasterDto.setBaseUrl(ADMIN_POINTSMASTER);
        return pointsMasterDto;
    }

    @PostMapping("/getedit")
    @ResponseBody
    public PointsMasterDto editPointsMaster(@RequestBody PointsMasterDto request) {
        PointsMasterDto pointsMasterDto=new PointsMasterDto();
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
    public PointsMasterDto addPointsMaster() {
        PointsMasterDto pointsMasterDto=new PointsMasterDto();
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
        pointsMasterDto.setMessage("Data deleted Successfully");
        pointsMasterDto.setBaseUrl(ADMIN_POINTSMASTER);
        return pointsMasterDto;
    }



}
