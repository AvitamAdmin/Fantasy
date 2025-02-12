package com.avitam.fantasy11.web.controllers.admin.settings;

import com.avitam.fantasy11.api.dto.SEODto;
import com.avitam.fantasy11.api.dto.SEOWsDto;
import com.avitam.fantasy11.api.dto.SearchDto;
import com.avitam.fantasy11.api.service.SEOService;
import com.avitam.fantasy11.model.SEO;
import com.avitam.fantasy11.repository.SEORepository;
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

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/admin/seo")
public class SEOController extends BaseController {

    public static final String ADMIN_SEO = "/admin/seo";
    @Autowired
    private SEORepository seoRepository;
    @Autowired
    private SEOService seoService;
    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    @ResponseBody
    public SEOWsDto getAll(@RequestBody SEOWsDto seoWsDto) {
        Pageable pageable = getPageable(seoWsDto.getPage(), seoWsDto.getSizePerPage(), seoWsDto.getSortDirection(), seoWsDto.getSortField());
        SEODto seoDto = CollectionUtils.isNotEmpty(seoWsDto.getSeoDtoList()) ? seoWsDto.getSeoDtoList().get(0) : new SEODto();
        SEO seo = modelMapper.map(seoDto, SEO.class);
        Page<SEO> page = isSearchActive(seo) != null ? seoRepository.findAll(Example.of(seo), pageable) : seoRepository.findAll(pageable);
        seoWsDto.setSeoDtoList(modelMapper.map(page.getContent(), List.class));
        seoWsDto.setTotalPages(page.getTotalPages());
        seoWsDto.setTotalRecords(page.getTotalElements());
        seoWsDto.setBaseUrl(ADMIN_SEO);
        return seoWsDto;
    }

    @GetMapping("/get")
    @ResponseBody
    public SEOWsDto getActiveSEOList() {
        SEOWsDto seoWsDto = new SEOWsDto();
        seoWsDto.setSeoDtoList(modelMapper.map(seoRepository.findByStatusOrderByIdentifier(true), List.class));
        seoWsDto.setBaseUrl(ADMIN_SEO);
        return seoWsDto;
    }

    @PostMapping("/getedit")
    @ResponseBody
    public SEOWsDto edit(@RequestBody SEOWsDto request) {
        SEOWsDto seoWsDto = new SEOWsDto();
        seoWsDto.setBaseUrl(ADMIN_SEO);
        SEO seo = seoRepository.findByRecordId(request.getSeoDtoList().get(0).getRecordId());
        seoWsDto.setSeoDtoList(List.of(modelMapper.map(seo, SEODto.class)));
        return seoWsDto;
    }

    @PostMapping(value = "/edit", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public SEOWsDto handleEdit(@ModelAttribute SEOWsDto request) throws IOException {

        return seoService.handleEdit(request);
    }

    @PostMapping("/delete")
    @ResponseBody
    public SEOWsDto deleteSEO(@RequestBody SEOWsDto seoWsDto) {
        for (SEODto seoDto : seoWsDto.getSeoDtoList()) {
            seoRepository.deleteByRecordId(seoDto.getRecordId());
        }
        seoWsDto.setMessage("Data deleted successfully!!");
        seoWsDto.setBaseUrl(ADMIN_SEO);
        return seoWsDto;
    }

    @GetMapping("/getAdvancedSearch")
    @ResponseBody

    public List<SearchDto> getSearchAttributes() {
        return getGroupedParentAndChildAttributes(new SEO());
    }

}
