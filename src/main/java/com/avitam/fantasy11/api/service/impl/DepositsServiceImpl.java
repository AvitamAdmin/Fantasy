package com.avitam.fantasy11.api.service.impl;

import com.avitam.fantasy11.api.dto.DepositsDto;
import com.avitam.fantasy11.api.service.BaseService;
import com.avitam.fantasy11.api.service.DepositsService;
import com.avitam.fantasy11.core.service.CoreService;
import com.avitam.fantasy11.model.Deposits;
import com.avitam.fantasy11.repository.DepositsRepository;
import com.avitam.fantasy11.repository.EntityConstants;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class DepositsServiceImpl implements DepositsService {

    @Autowired
    private DepositsRepository depositsRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CoreService coreService;
    @Autowired
    private BaseService baseService;

    @Override
    public Deposits findByRecordId(String recordId) {
        return depositsRepository.findByRecordId(recordId);

    }
    @Override
    public void deleteByRecordId(String recordId) {

        depositsRepository.deleteByRecordId(recordId);
    }

    @Override
    public DepositsDto handleEdit(DepositsDto request) {
        DepositsDto depositsDto=new DepositsDto();
        Deposits deposits=null;
        if (request.getRecordId()!=null){
            Deposits requestData=request.getDeposits();
            deposits=depositsRepository.findByRecordId(request.getRecordId());
            modelMapper.map(requestData,deposits);
        }else {
            if(baseService.validateIdentifier(EntityConstants.DEPOSITS,request.getDeposits().getIdentifier())!=null)
            {
                request.setSuccess(false);
                request.setMessage("Identifier already present");
                return request;
            }
            deposits=request.getDeposits();
        }
        baseService.populateCommonData(deposits);
        depositsRepository.save(deposits);
        if (request.getRecordId()==null){
            deposits.setRecordId(String.valueOf(deposits.getId().getTimestamp()));
        }
        depositsRepository.save(deposits);
        depositsDto.setDeposits(deposits);

        return depositsDto;
    }

    @Override
    public void updateByRecordId(String recordId) {
        Deposits deposits=depositsRepository.findByRecordId(recordId);
        if(deposits!=null){
            depositsRepository.save(deposits);
        }
    }


}
