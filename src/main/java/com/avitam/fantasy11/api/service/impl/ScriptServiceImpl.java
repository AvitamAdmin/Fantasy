package com.avitam.fantasy11.api.service.impl;

import com.avitam.fantasy11.api.dto.ScriptDto;
import com.avitam.fantasy11.api.dto.ScriptWsDto;
import com.avitam.fantasy11.api.service.BaseService;
import com.avitam.fantasy11.api.service.ScriptService;
import com.avitam.fantasy11.core.service.CoreService;
import com.avitam.fantasy11.model.Role;
import com.avitam.fantasy11.model.Script;
import com.avitam.fantasy11.repository.EntityConstants;
import com.avitam.fantasy11.repository.ScriptRepository;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScriptServiceImpl implements ScriptService {

    @Autowired
    private ScriptRepository scriptRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CoreService coreService;
    @Autowired
    private BaseService baseService;

    public static final String ADMIN_SCRIPT = "/admin/script";

    @Override
    public Role findByRecordId(String recordId) {
        return null;
    }

    @Override
    public void deleteByRecordId(String recordId) {

    }

    @Override
    public void updateByRecordId(String recordId) {

    }

    @Override
    public ScriptWsDto handleEdit(ScriptWsDto scriptWsDto) {
        {
            ScriptDto scriptDto = new ScriptDto();
            Script script = null;
            List<Script> scripts = new ArrayList<>();
            List<ScriptDto> scriptDtoList = scriptWsDto.getScriptDtoList();
            for (ScriptDto scriptDto1 : scriptDtoList) {
                if (scriptDto1.getRecordId() != null) {
                    script = scriptRepository.findByRecordId(scriptDto1.getRecordId());
                    modelMapper.map(scriptDto1, script);
                } else {
                    if (baseService.validateIdentifier(EntityConstants.SCRIPT, scriptDto1.getIdentifier()) != null) {
                        scriptWsDto.setSuccess(false);
                        scriptWsDto.setMessage("Identifier already present");
                        return scriptWsDto;
                    }
                    script = modelMapper.map(scriptDto, Script.class);
                }
                baseService.populateCommonData(script);
                script.setCreator(coreService.getCurrentUser().getCreator());
                script.setModifiedBy(String.valueOf(new Date()));
                if (script.getRecordId() == null) {
                    script.setRecordId(String.valueOf(script.getId().getTimestamp()));
                }
                scriptRepository.save(script);
                scripts.add(script);
                scriptWsDto.setMessage("Script updated successfully!");
                scriptWsDto.setBaseUrl(ADMIN_SCRIPT);
            }
            scriptWsDto.setScriptDtoList(modelMapper.map(script, List.class));
            return scriptWsDto;
        }

    }
}
