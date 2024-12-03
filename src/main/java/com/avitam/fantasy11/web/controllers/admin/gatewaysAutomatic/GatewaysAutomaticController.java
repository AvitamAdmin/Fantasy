package com.avitam.fantasy11.web.controllers.admin.gatewaysAutomatic;

import com.avitam.fantasy11.api.dto.AddressDto;
import com.avitam.fantasy11.api.dto.GatewaysAutomaticDto;
import com.avitam.fantasy11.api.dto.GatewaysAutomaticWsDto;
import com.avitam.fantasy11.api.service.GatewaysAutomaticService;
import com.avitam.fantasy11.core.service.CoreService;
import com.avitam.fantasy11.model.Address;
import com.avitam.fantasy11.model.GatewaysAutomatic;
import com.avitam.fantasy11.repository.GatewaysAutomaticRepository;
import com.avitam.fantasy11.web.controllers.BaseController;
import java.util.List;
import org.apache.commons.collections4.CollectionUtils;
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
    public GatewaysAutomaticWsDto getAll(@RequestBody GatewaysAutomaticWsDto gatewaysAutomaticWsDto){
        Pageable pageable = getPageable(gatewaysAutomaticWsDto.getPage(), gatewaysAutomaticWsDto.getSizePerPage(), gatewaysAutomaticWsDto.getSortDirection(), gatewaysAutomaticWsDto.getSortField());
        GatewaysAutomaticDto gatewaysAutomaticDto = CollectionUtils.isNotEmpty(gatewaysAutomaticWsDto.getGatewaysAutomaticDtoList()) ? gatewaysAutomaticWsDto.getGatewaysAutomaticDtoList().get(0) : null;
        GatewaysAutomatic gatewaysAutomatic = gatewaysAutomaticDto != null ? modelMapper.map(gatewaysAutomaticDto, GatewaysAutomatic.class) : null;
        Page<GatewaysAutomatic> page = isSearchActive(gatewaysAutomatic)!=null ? gatewaysAutomaticRepository.findAll(Example.of(gatewaysAutomatic), pageable) : gatewaysAutomaticRepository.findAll(pageable);
        gatewaysAutomaticWsDto.setGatewaysAutomaticDtoList(modelMapper.map(page.getContent(), List.class));
        gatewaysAutomaticWsDto.setTotalPages(page.getTotalPages());
        gatewaysAutomaticWsDto.setTotalRecords(page.getTotalElements());
        gatewaysAutomaticWsDto.setBaseUrl(ADMIN_GATEWAYSAUTOMATIC);
        return gatewaysAutomaticWsDto;
    }

    @GetMapping("/get")
    @ResponseBody
    public GatewaysAutomaticWsDto getActiveGatewaysAutomatic(){
        GatewaysAutomaticWsDto gatewaysAutomaticWsDto = new GatewaysAutomaticWsDto();
        gatewaysAutomaticWsDto.setGatewaysAutomaticDtoList(modelMapper.map(gatewaysAutomaticRepository.findByStatusOrderByIdentifier(true),List.class));
        gatewaysAutomaticWsDto.setBaseUrl(ADMIN_GATEWAYSAUTOMATIC);
        return gatewaysAutomaticWsDto;
    }
    @PostMapping("/getedit")
    @ResponseBody
    public GatewaysAutomaticWsDto editGatewaysAutomatic (@RequestBody GatewaysAutomaticWsDto gatewaysAutomaticWsDto){
        gatewaysAutomaticWsDto.setGatewaysAutomaticDtoList(modelMapper.map(gatewaysAutomaticRepository.findByRecordId(gatewaysAutomaticWsDto.getRecordId()),List.class));
        gatewaysAutomaticWsDto.setBaseUrl(ADMIN_GATEWAYSAUTOMATIC);
        return gatewaysAutomaticWsDto;
    }

    @PostMapping(value = "/edit", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public GatewaysAutomaticWsDto handleEdit(@ModelAttribute GatewaysAutomaticWsDto gatewaysAutomaticWsDto)  {
        return gatewaysAutomaticService.handleEdit(gatewaysAutomaticWsDto);
    }

    @GetMapping("/add")
    @ResponseBody
    public GatewaysAutomaticWsDto addGatewaysAutomatic() {
        GatewaysAutomaticWsDto gatewaysAutomaticWsDto = new GatewaysAutomaticWsDto();
        gatewaysAutomaticWsDto.setGatewaysAutomaticDtoList(modelMapper.map(gatewaysAutomaticRepository.findByStatusOrderByIdentifier(true),List.class));
        gatewaysAutomaticWsDto.setBaseUrl(ADMIN_GATEWAYSAUTOMATIC);
        return gatewaysAutomaticWsDto;
    }
    @PostMapping("/delete")
    @ResponseBody
    public GatewaysAutomaticWsDto deleteGatewaysAutomatic(@RequestBody GatewaysAutomaticWsDto gatewaysAutomaticWsDto) {
        for (GatewaysAutomaticDto gatewaysAutomaticDto : gatewaysAutomaticWsDto.getGatewaysAutomaticDtoList()) {
            gatewaysAutomaticRepository.deleteByRecordId(gatewaysAutomaticDto.getRecordId());
        }
        gatewaysAutomaticWsDto.setMessage("Data deleted successfully");
        gatewaysAutomaticWsDto.setBaseUrl(ADMIN_GATEWAYSAUTOMATIC);
        return gatewaysAutomaticWsDto;
    }
}
