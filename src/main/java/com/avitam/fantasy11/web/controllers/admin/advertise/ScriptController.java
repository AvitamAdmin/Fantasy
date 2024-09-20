package com.avitam.fantasy11.web.controllers.admin.advertise;

import com.avitam.fantasy11.api.dto.DepositsDto;
import com.avitam.fantasy11.api.dto.ScriptDto;
import com.avitam.fantasy11.api.service.ScriptService;
import com.avitam.fantasy11.core.service.CoreService;
import com.avitam.fantasy11.form.AddressForm;
import com.avitam.fantasy11.form.ScriptForm;
import com.avitam.fantasy11.model.*;
import com.avitam.fantasy11.validation.AddressFormValidator;
import com.avitam.fantasy11.web.controllers.BaseController;
import org.bson.types.ObjectId;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Optional;

@Controller
@RequestMapping("/admin/script")
public class ScriptController extends BaseController {

    @Autowired
    private ScriptRepository scriptRepository;
    @Autowired
    private CoreService coreService;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ScriptService scriptService;

    public static final String ADMIN_SCRIPT = "/admin/script";

    @PostMapping
    @ResponseBody
    public ScriptDto getAllScripts(@RequestBody ScriptDto scriptDto){
        Pageable pageable=getPageable(scriptDto.getPage(),scriptDto.getSizePerPage(),scriptDto.getSortDirection(),scriptDto.getSortField());
        Script script=scriptDto.getScript();
        Page<Script> page=isSearchActive(script) !=null ? scriptRepository.findAll(Example.of(script),pageable) : scriptRepository.findAll(pageable);
        scriptDto.setScripts(page.getContent());
        scriptDto.setBaseUrl(ADMIN_SCRIPT);
        scriptDto.setTotalPages(page.getTotalPages());
        scriptDto.setTotalRecords(page.getTotalElements());
        return scriptDto;
    }

    @GetMapping("/get")
    @ResponseBody
    public ScriptDto getActiveScript(){
        ScriptDto scriptDto=new ScriptDto();
        scriptDto.setScripts(scriptRepository.findByStatusOrderByIdentifier(true));
        scriptDto.setBaseUrl(ADMIN_SCRIPT);
        return scriptDto;
    }


    @PostMapping("/getedit")
    @ResponseBody
    public ScriptDto edit (@RequestBody ScriptDto request) {
        ScriptDto scriptDto = new ScriptDto();
        Script script = scriptRepository.findByRecordId(request.getRecordId());
        scriptDto.setScript(script);
        scriptDto.setBaseUrl(ADMIN_SCRIPT);
        return scriptDto;
    }


    @PostMapping("/edit")
    @ResponseBody
    public  ScriptDto handleEdit(@RequestBody ScriptDto request) {
        return scriptService.handleEdit(request);
    }



    @GetMapping("/add")
    @ResponseBody
    public ScriptDto addScript(@RequestBody ScriptDto request) {
        ScriptDto scriptDto = new ScriptDto();
        scriptDto.setScripts(scriptRepository.findByStatusOrderByIdentifier(true));
        scriptDto.setBaseUrl(ADMIN_SCRIPT);
        return scriptDto;
    }

    @PostMapping("/delete")
    @ResponseBody
    public ScriptDto deleteScript(@RequestBody ScriptDto scriptDto) {
        for (String id : scriptDto.getRecordId().split(",")) {
            scriptRepository.deleteByRecordId(id);
        }
        scriptDto.setMessage("Data deleted Successfully");
        scriptDto.setBaseUrl(ADMIN_SCRIPT);
        return scriptDto;
    }
}
