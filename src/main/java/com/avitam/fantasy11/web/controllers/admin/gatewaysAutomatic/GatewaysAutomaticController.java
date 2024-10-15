package com.avitam.fantasy11.web.controllers.admin.gatewaysAutomatic;

import com.avitam.fantasy11.api.dto.GatewaysAutomaticDto;
import com.avitam.fantasy11.api.service.GatewaysAutomaticService;
import com.avitam.fantasy11.core.service.CoreService;
import com.avitam.fantasy11.model.GatewaysAutomatic;
import com.avitam.fantasy11.repository.GatewaysAutomaticRepository;
import com.avitam.fantasy11.web.controllers.BaseController;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping(value = "/edit", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public GatewaysAutomaticDto handleEdit(@ModelAttribute GatewaysAutomaticDto request)  {
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
