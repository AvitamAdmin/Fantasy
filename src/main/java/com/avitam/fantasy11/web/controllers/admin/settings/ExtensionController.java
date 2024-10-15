package com.avitam.fantasy11.web.controllers.admin.settings;

import com.avitam.fantasy11.api.dto.ExtensionDto;
import com.avitam.fantasy11.api.service.ExtensionService;
import com.avitam.fantasy11.model.Extension;
import com.avitam.fantasy11.repository.ExtensionRepository;
import com.avitam.fantasy11.web.controllers.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/extension")
public class ExtensionController extends BaseController {

    @Autowired
    private ExtensionRepository extensionRepository;
    @Autowired
    private ExtensionService extensionService;

    private static final String ADMIN_EXTENSION = "/admin/extension";

    @PostMapping
    @ResponseBody
    public ExtensionDto getAllExtension(@RequestBody ExtensionDto extensionDto) {
        Pageable pageable = getPageable(extensionDto.getPage(), extensionDto.getSizePerPage(), extensionDto.getSortDirection(), extensionDto.getSortField());
        Extension extension = extensionDto.getExtension();
        Page<Extension> page = isSearchActive(extension) != null ? extensionRepository.findAll(Example.of(extension), pageable) : extensionRepository.findAll(pageable);
        extensionDto.setExtensionList(page.getContent());
        extensionDto.setTotalPages(page.getTotalPages());
        extensionDto.setTotalRecords(page.getTotalElements());
        extensionDto.setBaseUrl(ADMIN_EXTENSION);
        return extensionDto;

    }

    @GetMapping("/get")
    @ResponseBody
    public ExtensionDto getExtension() {
        ExtensionDto extensionDto = new ExtensionDto();
        extensionDto.setExtensionList(extensionRepository.findByStatusOrderByIdentifier(true));
        extensionDto.setBaseUrl(ADMIN_EXTENSION);
        return extensionDto;
    }

    @PostMapping("/getedit")
    @ResponseBody
    public ExtensionDto editExtension(@RequestBody ExtensionDto request) {
        ExtensionDto extensionDto = new ExtensionDto();
        Extension extension = extensionRepository.findByRecordId(request.getRecordId());
        extensionDto.setExtension(extension);
        extensionDto.setBaseUrl(ADMIN_EXTENSION);
        return extensionDto;
    }

    @PostMapping(value = "/edit", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public ExtensionDto handleEdit(@ModelAttribute ExtensionDto request) {
        return extensionService.handleEdit(request);
    }

    @GetMapping("/add")
    @ResponseBody
    public ExtensionDto addExtension() {
        ExtensionDto extensionDto = new ExtensionDto();
        extensionDto.setExtensionList(extensionRepository.findByStatusOrderByIdentifier(true));
        extensionDto.setBaseUrl(ADMIN_EXTENSION);
        return extensionDto;
    }


    @PostMapping("/delete")
    @ResponseBody
    public ExtensionDto deleteExtension(@RequestBody ExtensionDto extensionDto) {

        for (String id : extensionDto.getRecordId().split(",")) {
            extensionRepository.deleteByRecordId(id);
        }
        extensionDto.setMessage("Data deleted Successfully");
        extensionDto.setBaseUrl(ADMIN_EXTENSION);
        return extensionDto;
    }
}
