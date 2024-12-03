package com.avitam.fantasy11.api.service.impl;

import com.avitam.fantasy11.api.dto.ContestDto;
import com.avitam.fantasy11.api.dto.ContestWsDto;
import com.avitam.fantasy11.api.service.BaseService;
import com.avitam.fantasy11.api.service.ContestService;
import com.avitam.fantasy11.core.service.CoreService;
import com.avitam.fantasy11.model.Contest;
import com.avitam.fantasy11.repository.ContestRepository;
import com.avitam.fantasy11.repository.EntityConstants;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class ContestServiceImpl implements ContestService {

    @Autowired
    private ContestRepository contestRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CoreService coreService;
    @Autowired
    private BaseService baseService;

    private static final String ADMIN_CONTEST = "/admin/contest";

    @Override
    public Contest findByRecordId(String recordId) {

        return contestRepository.findByRecordId(recordId);
    }

    @Override
    public ContestWsDto handleEdit(ContestWsDto request) {
        ContestWsDto contestWsDto = new ContestWsDto();
        Contest contestData = null;
        List<ContestDto> contestDtos = request.getContestDtos();
        List<Contest> contestList = new ArrayList<>();
        ContestDto contestDto = new ContestDto();
        for (ContestDto contestDto1 : contestDtos) {
            if (contestDto1.getRecordId() != null) {
                contestData = contestRepository.findByRecordId(contestDto1.getRecordId());
                modelMapper.map(contestDto1, contestData);
                contestRepository.save(contestData);
            } else {
                if (baseService.validateIdentifier(EntityConstants.CONTEST, contestDto1.getIdentifier()) != null) {
                    request.setSuccess(false);
                    request.setMessage("Identifier already present");
                    return request;
                }

                contestData = modelMapper.map(contestDto1, Contest.class);
            }
            //baseService.populateCommonData(contestData);
            //contestData.setCreator(coreService.getCurrentUser().getCreator());
            contestRepository.save(contestData);

            contestData.setLastModified(new Date());
            if (contestDto.getRecordId() == null) {
                contestData.setRecordId(String.valueOf(contestData.getId().getTimestamp()));
            }
            contestRepository.save(contestData);
            contestList.add(contestData);
            contestWsDto.setMessage("Contest was updated successfully");
            contestWsDto.setBaseUrl(ADMIN_CONTEST);
        }
        contestWsDto.setContestDtos(modelMapper.map(contestList, List.class));
        return contestWsDto;
    }

    @Override
    public void deleteByRecordId(String recordId) {

        contestRepository.deleteByRecordId(recordId);
    }

    @Override
    public void updateByRecordId(String recordId) {
        Contest contest = contestRepository.findByRecordId(recordId);
        if (contest != null) {

            contestRepository.save(contest);
        }
    }

}
