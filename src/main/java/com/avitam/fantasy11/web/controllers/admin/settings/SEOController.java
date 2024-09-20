package com.avitam.fantasy11.web.controllers.admin.settings;

import com.avitam.fantasy11.api.dto.SEODto;
import com.avitam.fantasy11.api.service.SEOService;
import com.avitam.fantasy11.model.SEO;
import com.avitam.fantasy11.model.SEORepository;
import com.avitam.fantasy11.web.controllers.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
@RequestMapping("/admin/seo")
public class SEOController extends BaseController {

    @Autowired
     private SEORepository seoRepository;
    @Autowired
    private SEOService seoService;

    public static final String ADMIN_SEO = "/admin/seo";

    @PostMapping
    @ResponseBody
    public SEODto getAll(@RequestBody SEODto seoDto){
        Pageable pageable=getPageable(seoDto.getPage(),seoDto.getSizePerPage(),seoDto.getSortDirection(),seoDto.getSortField());
        SEO seo = seoDto.getSeo();
        Page<SEO> page=isSearchActive(seo)!=null ? seoRepository.findAll(Example.of(seo),pageable) : seoRepository.findAll(pageable);
        seoDto.setSeoList(page.getContent());
        seoDto.setTotalPages(page.getTotalPages());
        seoDto.setTotalRecords(page.getTotalElements());
        seoDto.setBaseUrl(ADMIN_SEO);
        return seoDto;
    }

    @GetMapping("/get")
    @ResponseBody
    public SEODto getActiveSEOList() {
        SEODto seoDto = new SEODto();
        seoDto.setSeoList(seoRepository.findByStatusOrderByIdentifier(true));
        seoDto.setBaseUrl(ADMIN_SEO);
        return seoDto;
    }

    @PostMapping("/getedit")
    @ResponseBody
        public SEODto edit(@RequestBody SEODto request){
        SEODto seoDto = new SEODto();
        SEO seo = seoRepository.findByRecordId(request.getRecordId());
        seoDto.setSeo(seo);
        seoDto.setBaseUrl(ADMIN_SEO);
        return seoDto;
    }

    @PostMapping("/edit")
    @ResponseBody
    public SEODto handleEdit(@RequestBody SEODto request) throws IOException {

        return seoService.handleEdit(request);
    }

    @GetMapping("/add")
    @ResponseBody
    public SEODto add(){
        SEODto seoDto = new SEODto();
        seoDto.setSeoList(seoRepository.findByStatusOrderByIdentifier(true));
        seoDto.setBaseUrl(ADMIN_SEO);
        return seoDto;
    }
    @PostMapping("/delete")
    @ResponseBody
    public SEODto delete(@RequestBody SEODto seoDto) {
        for (String id : seoDto.getRecordId().split(",")) {

            seoRepository.deleteByRecordId(id);
        }
        seoDto.setMessage("Data deleted successfully!!");
        seoDto.setBaseUrl(ADMIN_SEO);
        return seoDto;
    }

}
