package com.avitam.fantasy11.web.controllers.admin.pointsUpdate;

import com.avitam.fantasy11.api.dto.PointsUpdateDto;
import com.avitam.fantasy11.api.service.PointsUpdateService;
import com.avitam.fantasy11.model.*;
import com.avitam.fantasy11.repository.PointsUpdateRepository;
import com.avitam.fantasy11.web.controllers.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/pointsUpdate")
public class PointsUpdateController extends BaseController {

    @Autowired
    private PointsUpdateRepository pointsUpdateRepository;
    @Autowired
    private PointsUpdateService pointsUpdateService;
    private static final String ADMIN_POINTSUPDATE="/admin/pointsUpdate";

    @PostMapping
    @ResponseBody
    public PointsUpdateDto getAllPointsUpdate(@RequestBody PointsUpdateDto pointsUpdateDto){

        Pageable pageable=getPageable(pointsUpdateDto.getPage(),pointsUpdateDto.getSizePerPage(),pointsUpdateDto.getSortDirection(),pointsUpdateDto.getSortField());
        PointsUpdate pointsUpdate=pointsUpdateDto.getPointsUpdate();
        Page<PointsUpdate>page=isSearchActive(pointsUpdate) != null ? pointsUpdateRepository.findAll(Example.of(pointsUpdate),pageable): pointsUpdateRepository.findAll(pageable);
       pointsUpdateDto.setPointsUpdateList(page.getContent());
        pointsUpdateDto.setBaseUrl(ADMIN_POINTSUPDATE);
        pointsUpdateDto.setTotalPages(page.getTotalPages());
        pointsUpdateDto.setTotalRecords(page.getTotalElements());
        return pointsUpdateDto;
    }

    @GetMapping("/get")
    @ResponseBody
    public PointsUpdateDto getActivePointsUpdate() {
        PointsUpdateDto pointsUpdateDto=new PointsUpdateDto();
        pointsUpdateDto.setPointsUpdateList(pointsUpdateRepository.findByStatusOrderByIdentifier(true));
        pointsUpdateDto.setBaseUrl(ADMIN_POINTSUPDATE);
        return pointsUpdateDto;
    }

    @PostMapping("/getedit")
    @ResponseBody
    public PointsUpdateDto editPointsUpdate(@RequestBody PointsUpdateDto request) {
        PointsUpdateDto pointsUpdateDto=new PointsUpdateDto();
        PointsUpdate pointsUpdate = pointsUpdateRepository.findByRecordId(request.getRecordId());
        pointsUpdateDto.setPointsUpdate(pointsUpdate);
        pointsUpdateDto.setBaseUrl(ADMIN_POINTSUPDATE);
        return pointsUpdateDto;
    }

    @PostMapping("/edit")
    @ResponseBody
    public PointsUpdateDto handleEdit(@RequestBody PointsUpdateDto request) {
         return pointsUpdateService.handleEdit(request);
    }

    @GetMapping("/add")
    @ResponseBody
    public PointsUpdateDto addPointsUpdate() {
        PointsUpdateDto pointsUpdateDto=new PointsUpdateDto();
        pointsUpdateDto.setPointsUpdateList(pointsUpdateRepository.findByStatusOrderByIdentifier(true));
        pointsUpdateDto.setBaseUrl(ADMIN_POINTSUPDATE);
        return pointsUpdateDto;
    }

    @PostMapping("/delete")
    @ResponseBody
    public PointsUpdateDto delete(@RequestBody PointsUpdateDto pointsUpdateDto) {
        for (String id : pointsUpdateDto.getRecordId().split(",")) {
            pointsUpdateRepository.deleteByRecordId(id);
        }
        pointsUpdateDto.setMessage("Data deleted Successfully");
        pointsUpdateDto.setBaseUrl(ADMIN_POINTSUPDATE);
        return pointsUpdateDto;
    }
}
