package com.avitam.fantasy11.web.controllers.admin.gatewaysManual;

import com.avitam.fantasy11.api.dto.AddressDto;
import com.avitam.fantasy11.api.dto.GatewaysAutomaticDto;
import com.avitam.fantasy11.api.dto.GatewaysAutomaticWsDto;
import com.avitam.fantasy11.api.dto.GatewaysManualDto;
import com.avitam.fantasy11.api.dto.GatewaysManualWsDto;
import com.avitam.fantasy11.api.service.GatewaysManualService;
import com.avitam.fantasy11.core.service.CoreService;
import com.avitam.fantasy11.model.Address;
import com.avitam.fantasy11.model.GatewaysAutomatic;
import com.avitam.fantasy11.model.GatewaysManual;
import com.avitam.fantasy11.repository.GatewaysManualRepository;
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
    public GatewaysManualWsDto getAll(@RequestBody GatewaysManualWsDto gatewaysManualWsDto) {
        Pageable pageable = getPageable(gatewaysManualWsDto.getPage(), gatewaysManualWsDto.getSizePerPage(), gatewaysManualWsDto.getSortDirection(), gatewaysManualWsDto.getSortField());
        GatewaysManualDto gatewaysManualDto = CollectionUtils.isNotEmpty(gatewaysManualWsDto.getGatewaysManualDtoList()) ? gatewaysManualWsDto.getGatewaysManualDtoList().get(0) : null;
        GatewaysManual gatewaysManual = gatewaysManualDto != null ? modelMapper.map(gatewaysManualDto, GatewaysManual.class) : null;
        Page<GatewaysManual> page = isSearchActive(gatewaysManual) != null ? gatewaysManualRepository.findAll(Example.of(gatewaysManual), pageable) : gatewaysManualRepository.findAll(pageable);
        gatewaysManualWsDto.setGatewaysManualDtoList(modelMapper.map(page.getContent(), List.class));
        gatewaysManualWsDto.setTotalPages(page.getTotalPages());
        gatewaysManualWsDto.setTotalRecords(page.getTotalElements());
        gatewaysManualWsDto.setBaseUrl(ADMIN_GATEWAYSMANUAL);
        return gatewaysManualWsDto;
    }

    @GetMapping("/get")
    @ResponseBody
    public GatewaysManualWsDto getActiveGatewaysManual() {
        GatewaysManualWsDto gatewaysManualWsDto = new GatewaysManualWsDto();
        gatewaysManualWsDto.setGatewaysManualDtoList(modelMapper.map(gatewaysManualRepository.findByStatusOrderByIdentifier(true), List.class));
        gatewaysManualWsDto.setBaseUrl(ADMIN_GATEWAYSMANUAL);
        return gatewaysManualWsDto;
    }

    @PostMapping("/getedit")
    @ResponseBody
    public GatewaysManualWsDto editGatewaysManual(@RequestBody GatewaysManualWsDto gatewaysManualWsDto) {
        gatewaysManualWsDto.setGatewaysManualDtoList(modelMapper.map(gatewaysManualRepository.findByRecordId(gatewaysManualWsDto.getGatewaysManualDtoList().get(0).getRecordId()), List.class));
        gatewaysManualWsDto.setBaseUrl(ADMIN_GATEWAYSMANUAL);
        return gatewaysManualWsDto;
    }

    @PostMapping(value = "/edit", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public GatewaysManualWsDto handleEdit(@ModelAttribute GatewaysManualWsDto gatewaysManualWsDto) {
        return gatewaysManualService.handleEdit(gatewaysManualWsDto);
    }

    @GetMapping("/add")
    @ResponseBody
    public GatewaysManualWsDto addGatewaysManual() {
        GatewaysManualWsDto gatewaysManualWsDto = new GatewaysManualWsDto();
        gatewaysManualWsDto.setGatewaysManualDtoList(modelMapper.map(gatewaysManualRepository.findByStatusOrderByIdentifier(true), List.class));
        gatewaysManualWsDto.setBaseUrl(ADMIN_GATEWAYSMANUAL);
        return gatewaysManualWsDto;
    }

    @PostMapping("/delete")
    @ResponseBody
    public GatewaysManualWsDto deleteGatewaysManual(@RequestBody GatewaysManualWsDto gatewaysManualWsDto) {
        for (GatewaysManualDto gatewaysManualDto : gatewaysManualWsDto.getGatewaysManualDtoList()) {
            gatewaysManualRepository.deleteByRecordId(gatewaysManualDto.getRecordId());
        }
        gatewaysManualWsDto.setMessage("Data deleted successfully");
        gatewaysManualWsDto.setBaseUrl(ADMIN_GATEWAYSMANUAL);
        return gatewaysManualWsDto;
    }
}
