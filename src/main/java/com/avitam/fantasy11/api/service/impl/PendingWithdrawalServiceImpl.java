package com.avitam.fantasy11.api.service.impl;

import com.avitam.fantasy11.api.dto.PendingWithdrawalDto;
import com.avitam.fantasy11.api.dto.PendingWithdrawalWsDto;
import com.avitam.fantasy11.api.service.BaseService;
import com.avitam.fantasy11.api.service.PendingWithdrawalService;
import com.avitam.fantasy11.core.service.CoreService;
import com.avitam.fantasy11.model.PendingWithdrawal;
import com.avitam.fantasy11.repository.EntityConstants;
import com.avitam.fantasy11.repository.PendingWithdrawalRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PendingWithdrawalServiceImpl implements PendingWithdrawalService {

    @Autowired
    private PendingWithdrawalRepository pendingWithdrawalRepository;
    @Autowired
    private ModelMapper modelMapper;
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
    public PendingWithdrawalWsDto handleEdit(PendingWithdrawalWsDto request) {

        PendingWithdrawal pendingWithdrawal = null;
        List<PendingWithdrawalDto> pendingWithdrawalDto = request.getPendingWithdrawalDtoList();
        List<PendingWithdrawal> pendingWithdrawals = new ArrayList<>();

        for(PendingWithdrawalDto pendingWithdrawalDto1 :pendingWithdrawalDto){
            if(pendingWithdrawalDto1.getRecordId()!=null){
                pendingWithdrawal = pendingWithdrawalRepository.findByRecordId(pendingWithdrawalDto1.getRecordId());
                modelMapper.map(pendingWithdrawalDto1, pendingWithdrawal);
                pendingWithdrawalRepository.save(pendingWithdrawal);
                request.setMessage("Data updated Successfully");
            }else {
                if(baseService.validateIdentifier(EntityConstants.PENDING_WITHDRAWL,pendingWithdrawalDto1.getIdentifier()) !=null){
                    request.setSuccess(false);
                    request.setMessage("Identifier already present");
                    return request;
                }
                pendingWithdrawal=modelMapper.map(pendingWithdrawalDto1,PendingWithdrawal.class);
            }
            baseService.populateCommonData(pendingWithdrawal);
            pendingWithdrawal.setStatus(true);
            pendingWithdrawalRepository.save(pendingWithdrawal);
            if (pendingWithdrawalDto1.getRecordId() == null) {
                pendingWithdrawal.setRecordId(String.valueOf(pendingWithdrawal.getId().getTimestamp()));
            }
            pendingWithdrawalRepository.save(pendingWithdrawal);
            pendingWithdrawals.add(pendingWithdrawal);
            request.setMessage("Data added successfully");
            request.setBaseUrl(ADMIN_PENDINGWITHDRAWAL);
        }
        request.setPendingWithdrawalDtoList(modelMapper.map(pendingWithdrawals, List.class));
        return request;


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
