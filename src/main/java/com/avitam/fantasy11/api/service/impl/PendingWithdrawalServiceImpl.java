package com.avitam.fantasy11.api.service.impl;

import com.avitam.fantasy11.api.dto.PendingWithdrawalDto;
import com.avitam.fantasy11.api.service.BaseService;
import com.avitam.fantasy11.api.service.PendingWithdrawalService;
import com.avitam.fantasy11.core.service.CoreService;
import com.avitam.fantasy11.model.PendingWithdrawal;
import com.avitam.fantasy11.repository.EntityConstants;
import com.avitam.fantasy11.repository.PendingWithdrawalRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class PendingWithdrawalServiceImpl implements PendingWithdrawalService {

    @Autowired
    private PendingWithdrawalRepository pendingWithdrawalRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CoreService coreService;
    @Autowired
    private BaseService baseService;

    public static final String ADMIN_PENDINGWITHDRAWAL = "/admin/pendingWithdrawal";

    @Override
    public PendingWithdrawal findByRecordId(String recordId) {
        return pendingWithdrawalRepository.findByRecordId(recordId);
    }

    @Override
    public void deleteByRecordId(String recordId) {
        pendingWithdrawalRepository.deleteByRecordId(recordId);
    }

    @Override
    public PendingWithdrawalDto handleEdit(PendingWithdrawalDto request) {
        PendingWithdrawalDto pendingWithdrawalDto = new PendingWithdrawalDto();
        PendingWithdrawal pendingWithdrawal = null;
        if(request.getRecordId()!=null){
            PendingWithdrawal requestData = request.getPendingWithdrawal();
            pendingWithdrawal = pendingWithdrawalRepository.findByRecordId(request.getRecordId());
            modelMapper.map(requestData, pendingWithdrawal);
        }
        else {
            if(baseService.validateIdentifier(EntityConstants.PENDING_WITHDRAWL,request.getPendingWithdrawal().getIdentifier())!=null)
            {
                request.setSuccess(false);
                request.setMessage("Identifier already present");
                return request;
            }
            pendingWithdrawal=request.getPendingWithdrawal();
        }
        baseService.populateCommonData(pendingWithdrawal);
        pendingWithdrawalRepository.save(pendingWithdrawal);
        if(request.getRecordId()==null){
            pendingWithdrawal.setRecordId(String.valueOf(pendingWithdrawal.getId().getTimestamp()));
        }
        pendingWithdrawalRepository.save(pendingWithdrawal);
        pendingWithdrawalDto.setPendingWithdrawal(pendingWithdrawal);
        pendingWithdrawalDto.setBaseUrl(ADMIN_PENDINGWITHDRAWAL);
        return pendingWithdrawalDto;
    }

    @Override
    public void updateByRecordId(String recordId) {
        PendingWithdrawal  pendingWithdrawalOptional=pendingWithdrawalRepository.findByRecordId(recordId);
        if(pendingWithdrawalOptional!=null)
        {
            pendingWithdrawalRepository.save(pendingWithdrawalOptional);
        }
    }


}
