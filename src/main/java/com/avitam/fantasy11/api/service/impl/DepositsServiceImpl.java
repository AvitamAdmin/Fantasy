package com.avitam.fantasy11.api.service.impl;

import com.avitam.fantasy11.api.dto.DepositsDto;
import com.avitam.fantasy11.api.service.DepositsService;
import com.avitam.fantasy11.core.service.CoreService;
import com.avitam.fantasy11.model.Deposits;
import com.avitam.fantasy11.model.DepositsRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
@Service
public class DepositsServiceImpl implements DepositsService {

    @Autowired
    private DepositsRepository depositsRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CoreService coreService;

    private static final String ADMIN_APPROVEDDEPOSIT="/admin/approvedDeposits";
    private static final String ADMIN_DEPOSIT="/admin/depositLog";
    private static final String ADMIN_PENDINGDEPOSIT="/admin/pendingDeposits";
    @Override
    public Deposits findByRecordId(String recordId) {
        return depositsRepository.findByRecordId(recordId);

    }
    @Override
    public void deleteByRecordId(String recordId) {

        depositsRepository.deleteByRecordId(recordId);
    }

    @Override
    public DepositsDto handleEdit(DepositsDto request,int flag) {
        DepositsDto depositsDto=new DepositsDto();
        Deposits deposits=null;
        if (request.getRecordId()!=null){
            Deposits requestData=request.getDeposits();
            deposits=depositsRepository.findByRecordId(request.getRecordId());
            modelMapper.map(requestData,deposits);
        }else {
            deposits=request.getDeposits();
            deposits.setCreator(coreService.getCurrentUser().getUsername());
            deposits.setCreationTime(new Date());
            depositsRepository.save(deposits);
        }
        deposits.setLastModified(new Date());
        if (request.getRecordId()==null){
            deposits.setRecordId(String.valueOf(deposits.getId().getTimestamp()));
        }
        depositsRepository.save(deposits);
        depositsDto.setDeposits(deposits);
         if(flag==1) {
             depositsDto.setBaseUrl(ADMIN_APPROVEDDEPOSIT);
         }
         if(flag==2) {
             depositsDto.setBaseUrl(ADMIN_DEPOSIT);
         }
         if(flag==3){
             depositsDto.setBaseUrl(ADMIN_PENDINGDEPOSIT);
}
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
