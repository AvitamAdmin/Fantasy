package com.avitam.fantasy11.api.service.impl;

import com.avitam.fantasy11.api.dto.WinningsDto;
import com.avitam.fantasy11.api.dto.WinningsWsDto;
import com.avitam.fantasy11.api.service.BaseService;
import com.avitam.fantasy11.api.service.WinningsService;
import com.avitam.fantasy11.model.Winnings;
import com.avitam.fantasy11.repository.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WinningsServiceImpl implements WinningsService {
    @Autowired
    private UserWinningsRepository userWinningsRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private BaseService baseService;
    @Autowired
    private ContestRepository contestRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private WinningsRepository winningsRepository;

    public static final String ADMIN_WINNINGS = "/admin/winnings";

    @Override
    public Winnings findByRecordId(String recordId) {
        return winningsRepository.findByRecordId(recordId);
    }

    @Override
    public void deleteByRecordId(String recordId) {
        winningsRepository.deleteByRecordId(recordId);
    }

    @Override
    public void updateByRecordId(String recordId) {
        Winnings winningsOptional = winningsRepository.findByRecordId(recordId);
        if (winningsOptional != null) {
            winningsRepository.save(winningsOptional);
        }
    }

    @Override
    public WinningsWsDto handleEdit(WinningsWsDto request) {


        Winnings winningsData = null;
        List<WinningsDto> winningsDtos = request.getWinningsDtoList();
        List<Winnings> winningsList = new ArrayList<>();

        for (WinningsDto winningsDto1 : winningsDtos) {
            request.setMessage("Data added successfully");
            if (winningsDto1.getRecordId() != null) {
                winningsData = winningsRepository.findByRecordId(winningsDto1.getRecordId());
                modelMapper.map(winningsDto1, winningsData);
                winningsRepository.save(winningsData);
                request.setMessage("Data updated Successfully");
            } else {
                if (baseService.validateIdentifier(EntityConstants.USER_WINNINGS, winningsDto1.getIdentifier()) != null) {
                    request.setSuccess(false);
                    request.setMessage("already present");
                    return request;
                }

                winningsData = modelMapper.map(winningsDto1, Winnings.class);
            }
            baseService.populateCommonData(winningsData);
            winningsData.setStatus(true);
            winningsRepository.save(winningsData);
            if (winningsData.getRecordId() == null) {
                winningsData.setRecordId(String.valueOf(winningsData.getId().getTimestamp()));
            }
            winningsRepository.save(winningsData);

            winningsList.add(winningsData);
            request.setBaseUrl(ADMIN_WINNINGS);

        }
        request.setWinningsDtoList(modelMapper.map(winningsList, List.class));
        return request;
    }
}