package com.avitam.fantasy11.api.service.impl;

import com.avitam.fantasy11.api.dto.MatchScoreDto;
import com.avitam.fantasy11.api.dto.ScriptDto;
import com.avitam.fantasy11.api.service.ScriptService;
import com.avitam.fantasy11.core.service.CoreService;
import com.avitam.fantasy11.model.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
@Service
public class ScriptServiceImpl implements ScriptService {

    @Autowired
    private ScriptRepository scriptRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CoreService coreService;

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
    public ScriptDto handleEdit(ScriptDto request) {
        {
            ScriptDto scriptDto = new ScriptDto();
            Script script=null;
            if (request.getRecordId()!=null) {
                Script requestData=request.getScript();
                script=scriptRepository.findByRecordId(request.getRecordId());
                modelMapper.map(requestData,script);
            }else {
                script=request.getScript();
                script.setCreator(coreService.getCurrentUser().getUsername());
                script.setCreationTime(new Date());
                scriptRepository.save(script);
            }
            script.setLastModified(new Date());
            if (request.getRecordId()==null){
                script.setRecordId(String.valueOf(script.getId().getTimestamp()));
            }
            scriptRepository.save(script);
            scriptDto.setScript(script);
            scriptDto.setBaseUrl(ADMIN_SCRIPT);
            return scriptDto;
        }

    }
}
