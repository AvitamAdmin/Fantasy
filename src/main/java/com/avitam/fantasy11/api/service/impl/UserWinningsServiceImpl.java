package com.avitam.fantasy11.api.service.impl;

import com.avitam.fantasy11.api.dto.MatchTypeDto;
import com.avitam.fantasy11.api.dto.MatchTypeWsDto;
import com.avitam.fantasy11.api.dto.UserWinningsDto;
import com.avitam.fantasy11.api.dto.UserWinningsWsDto;
import com.avitam.fantasy11.api.service.BaseService;
import com.avitam.fantasy11.api.service.UserWinningsService;
import com.avitam.fantasy11.core.service.CoreService;
import com.avitam.fantasy11.model.MatchType;
import com.avitam.fantasy11.model.UserWinnings;
import com.avitam.fantasy11.repository.EntityConstants;
import com.avitam.fantasy11.repository.UserWinningsRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UserWinningsServiceImpl implements UserWinningsService {
    @Autowired
    private UserWinningsRepository userWinningsRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CoreService coreService;
    @Autowired
    private BaseService baseService;
    public static final String ADMIN_USERWINNINGS = "/admin/userWinnings";
    @Override
    public UserWinnings findByRecordId(String recordId) {
        return userWinningsRepository.findByRecordId(recordId);
    }

    @Override
    public void deleteByRecordId(String recordId) {
        userWinningsRepository.deleteByRecordId(recordId);
    }

    @Override
    public void updateByRecordId(String recordId) {
        UserWinnings userWinningsOptional=userWinningsRepository.findByRecordId(recordId);
        if(userWinningsOptional!=null)
        {
            userWinningsRepository.save(userWinningsOptional);
        }
    }



    @Override
    public UserWinningsWsDto handleEdit(UserWinningsWsDto request) {
        UserWinningsWsDto userWinningsWsDto = new UserWinningsWsDto();
        UserWinnings userWinningsData = null;
        List<UserWinningsDto> userWinningsDtos = request.getUserWinningsDtoList();
        List<UserWinnings> userWinningsList = new ArrayList<>();
        UserWinningsDto userWinningsDto = new UserWinningsDto();
        for (UserWinningsDto userWinningsDto1 : userWinningsDtos) {
            if (userWinningsDto1.getRecordId() != null) {
                userWinningsData = userWinningsRepository.findByRecordId(userWinningsDto1.getRecordId());
                modelMapper.map(userWinningsDto1, userWinningsData);
                userWinningsRepository.save(userWinningsData);
            } else {
                if (baseService.validateIdentifier(EntityConstants.KYC, userWinningsDto1.getIdentifier()) != null) {
                    request.setSuccess(false);
                    request.setMessage("already present");
                    return request;
                }

                userWinningsData = modelMapper.map( userWinningsDto,  UserWinnings.class);
            }
            userWinningsRepository.save(userWinningsData);
            userWinningsData.setLastModified(new Date());
            if (userWinningsData.getRecordId() == null) {
                userWinningsData.setRecordId(String.valueOf(userWinningsData.getId().getTimestamp()));
            }
            userWinningsRepository.save(userWinningsData);
            userWinningsList.add(userWinningsData);
            userWinningsWsDto.setMessage("UserWinnings was updated successfully");
            userWinningsWsDto.setBaseUrl(ADMIN_USERWINNINGS);

        }
        userWinningsWsDto.setUserWinningsDtoList(modelMapper.map(userWinningsList, List.class));
        return userWinningsWsDto;
    }}