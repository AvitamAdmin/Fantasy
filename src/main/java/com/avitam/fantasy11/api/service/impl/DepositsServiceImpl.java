package com.avitam.fantasy11.api.service.impl;

import com.avitam.fantasy11.api.dto.DepositsDto;
import com.avitam.fantasy11.api.dto.DepositsWsDto;
import com.avitam.fantasy11.api.service.BaseService;
import com.avitam.fantasy11.api.service.DepositsService;
import com.avitam.fantasy11.model.Deposits;
import com.avitam.fantasy11.repository.DepositsRepository;
import com.avitam.fantasy11.repository.EntityConstants;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class DepositsServiceImpl implements DepositsService {

    private static final String ADMIN_DEPOSIT = "/admin/depositLog";
    @Autowired
    private DepositsRepository depositsRepository;
    @Autowired
    private ModelMapper modelMapper;
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
    public DepositsWsDto handleEdit(DepositsWsDto depositsWsDto) {
        Deposits depositsData = null;
        List<DepositsDto> depositsDtos = depositsWsDto.getDepositsDtoList();
        List<Deposits> depositsList = new ArrayList<>();

        for (DepositsDto depositsDto : depositsDtos) {
            if (depositsDto.getRecordId() != null) {
                depositsData = depositsRepository.findByRecordId(depositsDto.getRecordId());
                modelMapper.map(depositsDto, depositsData);
                depositsData.setLastModified(new Date());
                depositsRepository.save(depositsData);
                depositsWsDto.setMessage("Data updated Successfully");
            } else {
                if (baseService.validateIdentifier(EntityConstants.DEPOSITS, depositsDto.getIdentifier()) != null) {
                    depositsWsDto.setSuccess(false);
                    depositsWsDto.setMessage("Identifier already present");
                    return depositsWsDto;
                }
                depositsData = modelMapper.map(depositsDto, Deposits.class);
                depositsData.setStatus(true);
                depositsData.setCreationTime(new Date());
                depositsRepository.save(depositsData);
                if (depositsData.getRecordId() == null) {
                    depositsData.setRecordId(String.valueOf(depositsData.getId().getTimestamp()));
                }
                depositsRepository.save(depositsData);
                depositsWsDto.setMessage("Deposits added Successfully!");
            }
            depositsList.add(depositsData);
        }
        depositsWsDto.setBaseUrl(ADMIN_DEPOSIT);
        depositsWsDto.setDepositsDtoList(modelMapper.map(depositsList, List.class));
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
