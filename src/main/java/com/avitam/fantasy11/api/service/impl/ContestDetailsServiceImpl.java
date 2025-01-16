package com.avitam.fantasy11.api.service.impl;

import com.avitam.fantasy11.api.dto.ContestDetailsDto;
import com.avitam.fantasy11.api.dto.ContestDetailsWsDto;
import com.avitam.fantasy11.api.service.BaseService;
import com.avitam.fantasy11.api.service.ContestDetailsService;
import com.avitam.fantasy11.model.ContestDetails;
import com.avitam.fantasy11.repository.ContestDetailsRepository;
import com.avitam.fantasy11.repository.EntityConstants;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ContestDetailsServiceImpl implements ContestDetailsService {
    @Autowired
    private ContestDetailsRepository contestDetailsRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private BaseService baseService;

    private static final String ADMIN_CONTESTDETAILS = "/admin/contestDetails";

    @Override
    public ContestDetailsWsDto handelEdit(ContestDetailsWsDto request) {
        ContestDetails contestDetails = null;
        List<ContestDetails> contestDetailsList = new ArrayList<>();
        for (ContestDetailsDto contestDetailsDto : request.getContestDetailsDtoList()) {
            if (contestDetailsDto.getRecordId() != null) {
                contestDetails = contestDetailsRepository.findByRecordId(contestDetailsDto.getRecordId());
                modelMapper.map(contestDetailsDto, contestDetails);
                contestDetailsRepository.save(contestDetails);
                request.setMessage("Data Updated successfully");
            } else {
                if (baseService.validateIdentifier(EntityConstants.CONTESTDETAILS, contestDetailsDto.getIdentifier()) != null) {
                    request.setSuccess(false);
                    request.setMessage("Identifier already present");
                    return request;
                }
                contestDetails = modelMapper.map(contestDetailsDto, ContestDetails.class);
            }
            baseService.populateCommonData(contestDetails);
            contestDetails.setStatus(true);
            contestDetailsRepository.save(contestDetails);
            if (contestDetails.getRecordId() == null) {
                contestDetails.setRecordId(String.valueOf(contestDetails.getId().getTimestamp()));
                contestDetailsRepository.save(contestDetails);
                request.setMessage("Data Added Successfully");
            }

            contestDetailsList.add(contestDetails);

            request.setBaseUrl(ADMIN_CONTESTDETAILS);
        }
        request.setContestDetailsDtoList(modelMapper.map(contestDetailsList, List.class));
        return request;
    }





}
