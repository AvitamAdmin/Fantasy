package com.avitam.fantasy11.api.service.impl;

import com.avitam.fantasy11.api.dto.ContestDto;
import com.avitam.fantasy11.api.dto.ContestWsDto;
import com.avitam.fantasy11.api.service.BaseService;
import com.avitam.fantasy11.api.service.ContestService;
import com.avitam.fantasy11.model.Contest;
import com.avitam.fantasy11.repository.ContestRepository;
import com.avitam.fantasy11.repository.EntityConstants;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class ContestServiceImpl implements ContestService {

    @Autowired
    private ContestRepository contestRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private BaseService baseService;

    private static final String ADMIN_CONTEST = "/admin/contest";

    @Override
    public Contest findByRecordId(String recordId) {

        return contestRepository.findByRecordId(recordId);
    }

    @Override
    public ContestWsDto handleEdit(ContestWsDto request) {
        Contest contestData = null;
        List<ContestDto> contestDtos = request.getContestDtos();
        List<Contest> contestList = new ArrayList<>();

        for (ContestDto contestDto1 : contestDtos) {
            if (contestDto1.getRecordId() != null) {
                contestData = contestRepository.findByRecordId(contestDto1.getRecordId());
                modelMapper.map(contestDto1, contestData);
                profitCalculation(contestData);
                contestRepository.save(contestData);
                request.setMessage("Data updated Successfully");
            } else {
                if (baseService.validateIdentifier(EntityConstants.CONTEST, contestDto1.getIdentifier()) != null) {
                    request.setSuccess(false);
                    request.setMessage("Identifier already present");
                    return request;
                }
                contestData = modelMapper.map(contestDto1, Contest.class);

                baseService.populateCommonData(contestData);
                contestData.setStatus(true);
                profitCalculation(contestData);
                contestRepository.save(contestData);
                if (contestData.getRecordId() == null) {
                    contestData.setRecordId(String.valueOf(contestData.getId().getTimestamp()));
                }
                contestRepository.save(contestData);
                request.setMessage("Contest added Successfully");
            }
            contestList.add(contestData);
        }
        request.setBaseUrl(ADMIN_CONTEST);
        request.setContestDtos(modelMapper.map(contestList, List.class));
        return request;
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

//    @Override
//    public double getContestWinningAmount(double entryFee, int slotFilled, double profitPercentage) {
//        return 0;
//    }


//        @Override
//        public double getContestWinningAmount(double entryFee, int slotFilled, double profitPercentage) {
//            double totalAmount = entryFee * slotFilled;
//            double profit = totalAmount * profitPercentage;
//            double winningsAmount = totalAmount - profit;
//            contestRepository.save(contest);
//            return winningsAmount;
//        }

    private void profitCalculation(Contest contestDto1) {


        contestDto1.setTotalAmount(contestDto1.getTotalAmount());
            contestDto1.setTotalAmount(contestDto1.getEntryFee() * contestDto1.getNoOfMembers());

        contestDto1.setProfit(contestDto1.getProfit());
        //contestDto1.setProfitPercentage(contestDto1.get);
        contestDto1.setProfit(contestDto1.getTotalAmount() * contestDto1.getProfitPercentage() / 100);

        contestDto1.setWinningsAmount(contestDto1.getWinningsAmount());
        contestDto1.setWinningsAmount(contestDto1.getTotalAmount() - contestDto1.getProfit());
    }
}



