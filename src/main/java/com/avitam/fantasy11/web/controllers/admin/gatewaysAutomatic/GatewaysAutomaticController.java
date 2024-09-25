package com.avitam.fantasy11.web.controllers.admin.gatewaysAutomatic;

import com.avitam.fantasy11.api.dto.GatewaysAutomaticDto;
import com.avitam.fantasy11.api.dto.GatewaysManualDto;
import com.avitam.fantasy11.api.service.GatewaysAutomaticService;
import com.avitam.fantasy11.core.service.CoreService;
import com.avitam.fantasy11.form.GatewaysAutomaticForm;
import com.avitam.fantasy11.form.TeamForm;
import com.avitam.fantasy11.model.*;
import com.avitam.fantasy11.web.controllers.BaseController;
import org.bson.types.Binary;
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

import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/admin/gatewaysAutomatic")
public class GatewaysAutomaticController extends BaseController {
    @Autowired
    private GatewaysAutomaticRepository gatewaysAutomaticRepository;
    @Autowired
    private CoreService coreService;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private GatewaysAutomaticService gatewaysAutomaticService;
    public static final String ADMIN_GATEWAYSAUTOMATIC = "/admin/gatewaysAutomatic";

    @PostMapping
    @ResponseBody
    public GatewaysAutomaticDto getAll(@RequestBody GatewaysAutomaticDto gatewaysAutomaticDto){
        Pageable pageable = getPageable(gatewaysAutomaticDto.getPage(), gatewaysAutomaticDto.getSizePerPage(), gatewaysAutomaticDto.getSortDirection(), gatewaysAutomaticDto.getSortField());
        GatewaysAutomatic gatewaysAutomatic = gatewaysAutomaticDto.getGatewaysAutomatic();
        Page<GatewaysAutomatic> page = isSearchActive(gatewaysAutomatic)!=null ? gatewaysAutomaticRepository.findAll(Example.of(gatewaysAutomatic), pageable) : gatewaysAutomaticRepository.findAll(pageable);
        gatewaysAutomaticDto.setGatewaysAutomaticList(page.getContent());
        gatewaysAutomaticDto.setTotalPages(page.getTotalPages());
        gatewaysAutomaticDto.setTotalRecords(page.getTotalElements());
        gatewaysAutomaticDto.setBaseUrl(ADMIN_GATEWAYSAUTOMATIC);
        return gatewaysAutomaticDto;
    }

    @GetMapping("/get")
    @ResponseBody
    public GatewaysAutomaticDto getActiveGatewaysAutomatic(){
        GatewaysAutomaticDto gatewaysAutomaticDto = new GatewaysAutomaticDto();
        gatewaysAutomaticDto.setGatewaysAutomaticList(gatewaysAutomaticRepository.findByStatusOrderByIdentifier(true));
        gatewaysAutomaticDto.setBaseUrl(ADMIN_GATEWAYSAUTOMATIC);
        return gatewaysAutomaticDto;
    }
    @PostMapping("/getedit")
    @ResponseBody
    public GatewaysAutomaticDto editGatewaysAutomatic (@RequestBody GatewaysAutomaticDto request){

        GatewaysAutomaticDto gatewaysAutomaticDto = new GatewaysAutomaticDto();
        gatewaysAutomaticDto.setGatewaysAutomatic(gatewaysAutomaticRepository.findByRecordId(request.getRecordId()));
        gatewaysAutomaticDto.setBaseUrl(ADMIN_GATEWAYSAUTOMATIC);
        return gatewaysAutomaticDto;
    }

    @PostMapping("/edit")
    @ResponseBody
    public GatewaysAutomaticDto handleEdit(@RequestBody GatewaysAutomaticDto request)  {
        return gatewaysAutomaticService.handleEdit(request);
    }

    @GetMapping("/add")
    @ResponseBody
    public GatewaysAutomaticDto addGatewaysAutomatic() {

        GatewaysAutomaticDto gatewaysAutomaticDto = new GatewaysAutomaticDto();
        gatewaysAutomaticDto.setGatewaysAutomaticList(gatewaysAutomaticRepository.findByStatusOrderByIdentifier(true));
        gatewaysAutomaticDto.setBaseUrl(ADMIN_GATEWAYSAUTOMATIC);
        return gatewaysAutomaticDto;
    }
    @PostMapping("/delete")
    @ResponseBody
    public GatewaysAutomaticDto deleteGatewaysAutomatic(@RequestBody GatewaysAutomaticDto gatewaysAutomaticDto) {
        for (String id : gatewaysAutomaticDto.getRecordId().split(",")) {
            gatewaysAutomaticRepository.deleteByRecordId(id);
        }
        gatewaysAutomaticDto.setMessage("Data deleted successfully");
        gatewaysAutomaticDto.setBaseUrl(ADMIN_GATEWAYSAUTOMATIC);
        return gatewaysAutomaticDto;
    }
}
