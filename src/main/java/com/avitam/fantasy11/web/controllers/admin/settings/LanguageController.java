package com.avitam.fantasy11.web.controllers.admin.settings;

import com.avitam.fantasy11.api.dto.LanguageDto;
import com.avitam.fantasy11.api.dto.LanguageWsDto;
import com.avitam.fantasy11.api.dto.SearchDto;
import com.avitam.fantasy11.api.service.LanguageService;
import com.avitam.fantasy11.model.Language;
import com.avitam.fantasy11.model.LeaderBoard;
import com.avitam.fantasy11.repository.LanguageRepository;
import com.avitam.fantasy11.web.controllers.BaseController;
import org.apache.commons.collections4.CollectionUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/language")
public class LanguageController extends BaseController {

    public static final String ADMIN_LANGUAGE = "/admin/language";
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    private LanguageRepository languageRepository;
    @Autowired
    private LanguageService languageService;

    @PostMapping
    @ResponseBody
    public LanguageWsDto getAll(@RequestBody LanguageWsDto languageWsDto) {
        Pageable pageable = getPageable(languageWsDto.getPage(), languageWsDto.getSizePerPage(), languageWsDto.getSortDirection(), languageWsDto.getSortField());
        LanguageDto languageDto = CollectionUtils.isNotEmpty(languageWsDto.getLanguageDtoList()) ? languageWsDto.getLanguageDtoList().get(0) : new LanguageDto();
        Language language = modelMapper.map(languageDto, Language.class);
        Page<Language> page = isSearchActive(language) != null ? languageRepository.findAll(Example.of(language), pageable) : languageRepository.findAll(pageable);
        languageWsDto.setLanguageDtoList(modelMapper.map(page.getContent(), List.class));
        languageWsDto.setTotalPages(page.getTotalPages());
        languageWsDto.setTotalRecords(page.getTotalElements());
        languageWsDto.setBaseUrl(ADMIN_LANGUAGE);
        return languageWsDto;
    }

    @GetMapping("/get")
    @ResponseBody
    public LanguageWsDto getActiveLanguageList() {
        LanguageWsDto languageWsDto = new LanguageWsDto();
        languageWsDto.setLanguageDtoList(modelMapper.map(languageRepository.findByStatusOrderByIdentifier(true), List.class));
        languageWsDto.setBaseUrl(ADMIN_LANGUAGE);
        return languageWsDto;
    }

    @PostMapping("/getedit")
    @ResponseBody
    public LanguageWsDto edit(@RequestBody LanguageWsDto request) {
        LanguageWsDto languageWsDto = new LanguageWsDto();
        Language language = languageRepository.findByRecordId(request.getLanguageDtoList().get(0).getRecordId());
        languageWsDto.setLanguageDtoList(List.of(modelMapper.map(language, LanguageDto.class)));
        languageWsDto.setBaseUrl(ADMIN_LANGUAGE);
        return languageWsDto;
    }

    @PostMapping("/edit")
    @ResponseBody
    public LanguageWsDto handleEdit(@RequestBody LanguageWsDto request) {

        return languageService.handleEdit(request);
    }


    @PostMapping("/delete")
    @ResponseBody
    public LanguageWsDto deleteLanguage(@RequestBody LanguageWsDto languageWsDto) {
        for (LanguageDto data : languageWsDto.getLanguageDtoList()) {

            languageRepository.deleteByRecordId(data.getRecordId());
        }
        languageWsDto.setMessage("Data deleted successfully!!");
        languageWsDto.setBaseUrl(ADMIN_LANGUAGE);
        return languageWsDto;
    }

    @GetMapping("/getAdvancedSearch")
    @ResponseBody

    public List<SearchDto> getSearchAttributes() {
        return getGroupedParentAndChildAttributes(new LeaderBoard());
    }

}