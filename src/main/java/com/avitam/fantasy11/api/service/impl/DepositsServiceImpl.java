package com.avitam.fantasy11.api.service.impl;

import com.avitam.fantasy11.api.dto.DepositsDto;
import com.avitam.fantasy11.api.dto.DepositsWsDto;
import com.avitam.fantasy11.api.service.BaseService;
import com.avitam.fantasy11.api.service.DepositsService;
import com.avitam.fantasy11.core.service.CoreService;
import com.avitam.fantasy11.model.Deposits;
import com.avitam.fantasy11.repository.DepositsRepository;
import com.avitam.fantasy11.repository.EntityConstants;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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

    private static final String ADMIN_DEPOSIT = "/admin/depositLog";

    @Override
    public Deposits findByRecordId(String recordId) {
        return depositsRepository.findByRecordId(recordId);

    }

    @Override
    public void deleteByRecordId(String recordId) {

        depositsRepository.deleteByRecordId(recordId);
    }

    @Override
    public DepositsWsDto handleEdit(DepositsWsDto depositsWsDto) {
        Deposits depositsData = null;
        List<DepositsDto> depositsDtos = depositsWsDto.getDepositsList();
        List<Deposits> depositsList = new ArrayList<>();
        DepositsDto depositsDto = new DepositsDto();
        for (DepositsDto deposits : depositsDtos) {
            if (depositsWsDto.getRecordId() != null) {
                depositsData = depositsRepository.findByRecordId(deposits.getRecordId());
                modelMapper.map(depositsData, deposits);
            } else {
                if (baseService.validateIdentifier(EntityConstants.DEPOSITS, deposits.getIdentifier()) != null) {
                    depositsWsDto.setSuccess(false);
                    depositsWsDto.setMessage("Identifier already present");
                    return depositsWsDto;
                }
                depositsData = modelMapper.map(depositsDto, Deposits.class);
            }
            baseService.populateCommonData(depositsData);
            depositsData.setCreator(coreService.getCurrentUser().getCreator());
            depositsRepository.save(depositsData);
            depositsData.setLastModified(new Date());
            if (depositsWsDto.getRecordId() == null) {
                depositsWsDto.setRecordId(String.valueOf(depositsData.getId().getTimestamp()));
            }
            depositsRepository.save(depositsData);
            depositsList.add(depositsData);
            depositsWsDto.setMessage("deposits was updated successfully!");
            depositsWsDto.setBaseUrl(ADMIN_DEPOSIT);
        }
        depositsWsDto.setDepositsList(modelMapper.map(depositsList, List.class));
        return depositsWsDto;
    }


    @Override
    public void updateByRecordId(String recordId) {
        Deposits deposits = depositsRepository.findByRecordId(recordId);
        if (deposits != null) {
            depositsRepository.save(deposits);
        }
    }
}
