package com.avitam.fantasy11.web.controllers.admin.settings;

import com.avitam.fantasy11.api.dto.ExtensionDto;
import com.avitam.fantasy11.api.dto.ExtensionWsDto;
import com.avitam.fantasy11.api.service.ExtensionService;
import com.avitam.fantasy11.model.Extension;
import com.avitam.fantasy11.repository.ExtensionRepository;
import com.avitam.fantasy11.web.controllers.BaseController;
import org.apache.commons.collections4.CollectionUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/extension")
public class ExtensionController extends BaseController {

    @Autowired
    private ExtensionRepository extensionRepository;
    @Autowired
    private ExtensionService extensionService;
    @Autowired
    ModelMapper modelMapper;

    private static final String ADMIN_EXTENSION = "/admin/extension";

    @PostMapping
    @ResponseBody
    public ExtensionWsDto getAllExtension(@RequestBody ExtensionWsDto extensionWsDto) {
        Pageable pageable = getPageable(extensionWsDto.getPage(), extensionWsDto.getSizePerPage(), extensionWsDto.getSortDirection(), extensionWsDto.getSortField());
        ExtensionDto extensionDto = CollectionUtils.isNotEmpty(extensionWsDto.getExtensionDtoList()) ? extensionWsDto.getExtensionDtoList().get(0) : new ExtensionDto();
        Extension extension = modelMapper.map(extensionDto, Extension.class);
        Page<Extension> page = isSearchActive(extension) != null ? extensionRepository.findAll(Example.of(extension), pageable) : extensionRepository.findAll(pageable);
        extensionWsDto.setExtensionDtoList(modelMapper.map(page.getContent(), List.class));
        extensionWsDto.setTotalPages(page.getTotalPages());
        extensionWsDto.setTotalRecords(page.getTotalElements());
        extensionWsDto.setBaseUrl(ADMIN_EXTENSION);
        return extensionWsDto;

    }

    @GetMapping("/get")
    @ResponseBody
    public ExtensionWsDto getExtension() {
        ExtensionWsDto extensionWsDto = new ExtensionWsDto();
        extensionWsDto.setExtensionDtoList(modelMapper.map(extensionRepository.findByStatusOrderByIdentifier(true), List.class));
        extensionWsDto.setBaseUrl(ADMIN_EXTENSION);
        return extensionWsDto;
    }

    @PostMapping("/getedit")
    @ResponseBody
    public ExtensionWsDto editExtension(@RequestBody ExtensionWsDto request) {
        Extension extension=extensionRepository.findByRecordId(request.getExtensionDtoList().get(0).getRecordId());
        request.setExtensionDtoList(List.of(modelMapper.map(extension, ExtensionDto.class)));
        request.setBaseUrl(ADMIN_EXTENSION);
        return request;
    }

    @PostMapping(value = "/edit", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public ExtensionWsDto handleEdit(@ModelAttribute ExtensionWsDto request){
        return extensionService.handleEdit(request);
    }

    @PostMapping("/delete")
    @ResponseBody
    public ExtensionWsDto deleteExtension(@RequestBody ExtensionWsDto extensionWsDto) {

        for (ExtensionDto data : extensionWsDto.getExtensionDtoList()) {
            extensionRepository.deleteByRecordId(data.getRecordId());
        }
        extensionWsDto.setMessage("Data deleted Successfully");
        extensionWsDto.setBaseUrl(ADMIN_EXTENSION);
        return extensionWsDto;
    }
}
