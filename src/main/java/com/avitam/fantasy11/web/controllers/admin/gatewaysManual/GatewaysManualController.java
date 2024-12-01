package com.avitam.fantasy11.web.controllers.admin.gatewaysManual;

import com.avitam.fantasy11.api.dto.GatewaysManualDto;
import com.avitam.fantasy11.api.service.GatewaysManualService;
import com.avitam.fantasy11.core.service.CoreService;
import com.avitam.fantasy11.model.GatewaysManual;
import com.avitam.fantasy11.repository.GatewaysManualRepository;
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
@RequestMapping("/admin/gatewaysManual")
public class GatewaysManualController extends BaseController {
    @Autowired
    private GatewaysManualRepository gatewaysManualRepository;
    @Autowired
    private GatewaysManualService gatewaysManualService;
    @Autowired
    private CoreService coreService;
    @Autowired
    private ModelMapper modelMapper;

    public static final String ADMIN_GATEWAYSMANUAL = "/admin/gatewaysManual";

    @PostMapping
    @ResponseBody
    public GatewaysManualDto getAll(@RequestBody GatewaysManualDto gatewaysManualDto){
        Pageable pageable = getPageable(gatewaysManualDto.getPage(), gatewaysManualDto.getSizePerPage(), gatewaysManualDto.getSortDirection(), gatewaysManualDto.getSortField());
        GatewaysManual gatewaysManual = gatewaysManualDto.getGatewaysManual();
        Page<GatewaysManual> page = isSearchActive(gatewaysManual)!=null ? gatewaysManualRepository.findAll(Example.of(gatewaysManual), pageable) : gatewaysManualRepository.findAll(pageable);
        gatewaysManualDto.setGatewaysManualList(page.getContent());
        gatewaysManualDto.setTotalPages(page.getTotalPages());
        gatewaysManualDto.setTotalRecords(page.getTotalElements());
        gatewaysManualDto.setBaseUrl(ADMIN_GATEWAYSMANUAL);
        return gatewaysManualDto;
    }

    @GetMapping("/get")
    @ResponseBody
    public GatewaysManualDto getActiveGatewaysManual(){
        GatewaysManualDto gatewaysManualDto = new GatewaysManualDto();
        gatewaysManualDto.setGatewaysManualList(gatewaysManualRepository.findByStatusOrderByIdentifier(true));
        gatewaysManualDto.setBaseUrl(ADMIN_GATEWAYSMANUAL);
        return gatewaysManualDto;
    }
    @PostMapping("/getedit")
    @ResponseBody
    public GatewaysManualDto editGatewaysManual (@RequestBody GatewaysManualDto request){

        GatewaysManualDto gatewaysManualDto = new GatewaysManualDto();
        gatewaysManualDto.setGatewaysManual(gatewaysManualRepository.findByRecordId(request.getRecordId()));
        gatewaysManualDto.setBaseUrl(ADMIN_GATEWAYSMANUAL);
        return gatewaysManualDto;
    }

    @PostMapping(value = "/edit", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public GatewaysManualDto handleEdit(@ModelAttribute GatewaysManualDto request)  {
        return gatewaysManualService.handleEdit(request);
    }

    @GetMapping("/add")
    @ResponseBody
    public GatewaysManualDto addGatewaysManual() {

        GatewaysManualDto gatewaysManualDto = new GatewaysManualDto();
        gatewaysManualDto.setGatewaysManualList(gatewaysManualRepository.findByStatusOrderByIdentifier(true));
        gatewaysManualDto.setBaseUrl(ADMIN_GATEWAYSMANUAL);
        return gatewaysManualDto;
    }
    @PostMapping("/delete")
    @ResponseBody
    public GatewaysManualDto deleteGatewaysManual(@RequestBody GatewaysManualDto gatewaysManualDto) {
        for (String id : gatewaysManualDto.getRecordId().split(",")) {
            gatewaysManualRepository.deleteByRecordId(id);
        }
        gatewaysManualDto.setMessage("Data deleted successfully");
        gatewaysManualDto.setBaseUrl(ADMIN_GATEWAYSMANUAL);
        return gatewaysManualDto;
    }
}
