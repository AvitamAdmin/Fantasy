package com.avitam.fantasy11.web.controllers.admin.settings;

import com.avitam.fantasy11.api.dto.LanguageDto;
import com.avitam.fantasy11.api.service.LanguageService;
import com.avitam.fantasy11.model.Language;
import com.avitam.fantasy11.repository.LanguageRepository;
import com.avitam.fantasy11.web.controllers.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/language")
public class LanguageController extends BaseController {

    @Autowired
    private LanguageRepository languageRepository;
    @Autowired
    private LanguageService languageService;

    public static final String ADMIN_LANGUAGE = "/admin/language";

    @PostMapping
    @ResponseBody
    public LanguageDto getAll(@RequestBody LanguageDto languageDto) {
        Pageable pageable=getPageable(languageDto.getPage(),languageDto.getSizePerPage(),languageDto.getSortDirection(),languageDto.getSortField());
        Language language=languageDto.getLanguage();
        Page<Language> page=isSearchActive(language)!=null ? languageRepository.findAll(Example.of(language),pageable) : languageRepository.findAll(pageable);
        languageDto.setLanguageList(page.getContent());
        languageDto.setTotalPages(page.getTotalPages());
        languageDto.setTotalRecords(page.getTotalElements());
        languageDto.setBaseUrl(ADMIN_LANGUAGE);
        return languageDto;
    }

    @GetMapping("/get")
    @ResponseBody
    public LanguageDto getActiveLanguageList() {
        LanguageDto languageDto = new LanguageDto();
        languageDto.setLanguageList(languageRepository.findByStatusOrderByIdentifier(true));
        languageDto.setBaseUrl(ADMIN_LANGUAGE);
        return languageDto;
    }

    @PostMapping("/getedit")
    @ResponseBody
    public LanguageDto edit(@RequestBody LanguageDto request) {
        LanguageDto languageDto = new LanguageDto();
        Language language = languageRepository.findByRecordId(request.getRecordId());
        languageDto.setLanguage(language);
        languageDto.setBaseUrl(ADMIN_LANGUAGE);
        return languageDto;
    }

    @PostMapping("/edit")
    @ResponseBody
    public LanguageDto handleEdit(@RequestBody LanguageDto request) {

        return languageService.handleEdit(request);
    }

    @GetMapping("/add")
    @ResponseBody
    public LanguageDto add() {
        LanguageDto languageDto = new LanguageDto();
        languageDto.setLanguageList(languageRepository.findByStatusOrderByIdentifier(true));
        languageDto.setBaseUrl(ADMIN_LANGUAGE);
        return languageDto;
    }

    @PostMapping("/delete")
    @ResponseBody
    public LanguageDto delete(@RequestBody LanguageDto languageDto) {
        for (String id : languageDto.getRecordId().split(",")) {

            languageRepository.deleteByRecordId(id);
        }
        languageDto.setMessage("Data deleted successfully!!");
        languageDto.setBaseUrl(ADMIN_LANGUAGE);
        return languageDto;
    }
}