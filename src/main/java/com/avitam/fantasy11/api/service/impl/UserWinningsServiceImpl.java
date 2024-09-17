package com.avitam.fantasy11.api.service.impl;

import com.avitam.fantasy11.api.dto.UserWinningsDto;
import com.avitam.fantasy11.api.service.UserWinningsService;
import com.avitam.fantasy11.core.service.CoreService;
import com.avitam.fantasy11.model.UserWinnings;
import com.avitam.fantasy11.model.UserWinningsRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class UserWinningsServiceImpl implements UserWinningsService {
    @Autowired
    private UserWinningsRepository userWinningsRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CoreService coreService;
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
        //userWinningsOptional.ifPresent(userWinnings -> userWinningsRepository.save(userWinnings));
    }

    @Override
    public UserWinningsDto handleEdit(UserWinningsDto request) {

        UserWinningsDto userWinningsDto = new UserWinningsDto();
        UserWinnings userWinnings = null;
        if(request.getRecordId()!=null){
            UserWinnings requestData = request.getUserWinnings();
            userWinnings = userWinningsRepository.findByRecordId(request.getRecordId());
            modelMapper.map(requestData, userWinnings);
        }
        else{
            userWinnings = request.getUserWinnings();
            userWinnings.setCreator(coreService.getCurrentUser().getUsername());
            userWinnings.setCreationTime(new Date());
            userWinningsRepository.save(userWinnings);
        }
        userWinnings.setLastModified(new Date());
        if(request.getRecordId()==null){
            userWinnings.setRecordId(String.valueOf(userWinnings.getId().getTimestamp()));
        }
        userWinningsRepository.save(userWinnings);
        userWinningsDto.setUserWinnings(userWinnings);
        userWinningsDto.setBaseUrl(ADMIN_USERWINNINGS);
        return userWinningsDto;
    }
}
