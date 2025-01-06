package com.avitam.fantasy11.web.controllers.admin.advertise;

import com.avitam.fantasy11.api.dto.ScriptDto;
import com.avitam.fantasy11.api.dto.ScriptWsDto;
import com.avitam.fantasy11.api.service.ScriptService;
import com.avitam.fantasy11.model.Script;
import com.avitam.fantasy11.repository.ScriptRepository;
import com.avitam.fantasy11.web.controllers.BaseController;
import java.util.List;
import org.apache.commons.collections4.CollectionUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/script")
public class ScriptController extends BaseController {

    @Autowired
    private ScriptRepository scriptRepository;
    @Autowired
    private ScriptService scriptService;
    @Autowired
    private ModelMapper modelMapper;

    public static final String ADMIN_SCRIPT = "/admin/script";

    @PostMapping
    @ResponseBody
    public ScriptWsDto getAllScripts(@RequestBody ScriptWsDto scriptWsDto) {
        Pageable pageable = getPageable(scriptWsDto.getPage(), scriptWsDto.getSizePerPage(), scriptWsDto.getSortDirection(), scriptWsDto.getSortField());
        ScriptDto scriptDto = CollectionUtils.isNotEmpty(scriptWsDto.getScriptDtoList()) ? scriptWsDto.getScriptDtoList().get(0) : null;
        Script script = scriptDto != null ? modelMapper.map(scriptDto, Script.class) : null;
        Page<Script> page = isSearchActive(script) != null ? scriptRepository.findAll(Example.of(script), pageable) : scriptRepository.findAll(pageable);
        scriptWsDto.setScriptDtoList(modelMapper.map(page.getContent(), List.class));
        scriptWsDto.setBaseUrl(ADMIN_SCRIPT);
        scriptWsDto.setTotalPages(page.getTotalPages());
        scriptWsDto.setTotalRecords(page.getTotalElements());
        return scriptWsDto;
    }

    @GetMapping("/get")
    @ResponseBody
    public ScriptWsDto getActiveScript() {
        ScriptWsDto scriptWsDto = new ScriptWsDto();
        scriptWsDto.setScriptDtoList(modelMapper.map(scriptRepository.findByStatusOrderByIdentifier(true), List.class));
        scriptWsDto.setBaseUrl(ADMIN_SCRIPT);
        return scriptWsDto;
    }


    @PostMapping("/getedit")
    @ResponseBody
    public ScriptWsDto edit(@RequestBody ScriptWsDto scriptWsDto) {
        Script script = scriptRepository.findByRecordId(scriptWsDto.getScriptDtoList().get(0).getRecordId());
        scriptWsDto.setScriptDtoList(List.of(modelMapper.map(script, ScriptDto.class)));
        scriptWsDto.setBaseUrl(ADMIN_SCRIPT);
        return scriptWsDto;
    }


    @PostMapping("/edit")
    @ResponseBody
    public ScriptWsDto handleEdit(@RequestBody ScriptWsDto scriptWsDto) {
        return scriptService.handleEdit(scriptWsDto);
    }


    @PostMapping("/delete")
    @ResponseBody
    public ScriptWsDto deleteScript(@RequestBody ScriptWsDto scriptWsDto) {
        for (ScriptDto scriptDto : scriptWsDto.getScriptDtoList()) {
            scriptRepository.deleteByRecordId(scriptDto.getRecordId());
        }
        scriptWsDto.setMessage("Data deleted Successfully");
        scriptWsDto.setBaseUrl(ADMIN_SCRIPT);
        return scriptWsDto;
    }
}
