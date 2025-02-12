package com.avitam.fantasy11.api.service.impl;

import com.avitam.fantasy11.api.dto.UserWinningsDto;
import com.avitam.fantasy11.api.dto.UserWinningsWsDto;
import com.avitam.fantasy11.api.service.BaseService;
import com.avitam.fantasy11.api.service.UserWinningsService;
import com.avitam.fantasy11.model.UserWinnings;
import com.avitam.fantasy11.repository.ContestRepository;
import com.avitam.fantasy11.repository.EntityConstants;
import com.avitam.fantasy11.repository.UserRepository;
import com.avitam.fantasy11.repository.UserWinningsRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserWinningsServiceImpl implements UserWinningsService {
    public static final String ADMIN_USERWINNINGS = "/admin/userWinnings";
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
        UserWinnings userWinningsOptional = userWinningsRepository.findByRecordId(recordId);
        if (userWinningsOptional != null) {
            userWinningsRepository.save(userWinningsOptional);
        }
    }


    @Override
    public UserWinningsWsDto handleEdit(UserWinningsWsDto request) {

        UserWinnings userWinningsData = null;
        List<UserWinningsDto> userWinningsDtos = request.getUserWinningsDtoList();
        List<UserWinnings> userWinningsList = new ArrayList<>();

        for (UserWinningsDto userWinningsDto1 : userWinningsDtos) {
            if (userWinningsDto1.getRecordId() != null) {
                userWinningsData = userWinningsRepository.findByRecordId(userWinningsDto1.getRecordId());
                modelMapper.map(userWinningsDto1, userWinningsData);
                userWinningsRepository.save(userWinningsData);
                request.setMessage("Data updated Successfully");
            } else {
                if (baseService.validateIdentifier(EntityConstants.USER_WINNINGS, userWinningsDto1.getIdentifier()) != null) {
                    request.setSuccess(false);
                    request.setMessage(" Identifier already present");
                    return request;
                }

                userWinningsData = modelMapper.map(userWinningsDto1, UserWinnings.class);
                baseService.populateCommonData(userWinningsData);
                userWinningsData.setStatus(true);
                userWinningsRepository.save(userWinningsData);
                if (userWinningsData.getRecordId() == null) {
                    userWinningsData.setRecordId(String.valueOf(userWinningsData.getId().getTimestamp()));
                }
                userWinningsRepository.save(userWinningsData);
                request.setMessage("Data added successfully");
            }
            userWinningsList.add(userWinningsData);
            request.setBaseUrl(ADMIN_USERWINNINGS);

        }
        request.setUserWinningsDtoList(modelMapper.map(userWinningsList, List.class));
        return request;
    }
}