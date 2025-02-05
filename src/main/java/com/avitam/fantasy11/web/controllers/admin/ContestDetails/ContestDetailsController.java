package com.avitam.fantasy11.web.controllers.admin.ContestDetails;

import com.avitam.fantasy11.api.dto.ContestDetailsDto;
import com.avitam.fantasy11.api.dto.ContestDetailsWsDto;
import com.avitam.fantasy11.api.dto.SearchDto;
import com.avitam.fantasy11.api.service.ContestDetailsService;
import com.avitam.fantasy11.model.Contest;
import com.avitam.fantasy11.model.ContestDetails;
import com.avitam.fantasy11.repository.ContestDetailsRepository;
import com.avitam.fantasy11.web.controllers.BaseController;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/admin/contestDetails")
public class ContestDetailsController extends BaseController {
    @Autowired
    private ContestDetailsService contestDetailsService;

    @Autowired
    private ContestDetailsRepository contestDetailsRepository;

    @Autowired
    private ModelMapper modelMapper;

    private static final String ADMIN_CONTESTDETAILS = "/admin/contestDetails";

    @GetMapping("/get")
    @ResponseBody
    public ContestDetailsWsDto getContestDetails() {
        ContestDetailsWsDto contestDetailsWsDto = new ContestDetailsWsDto();
        contestDetailsWsDto.setContestDetailsDtoList(modelMapper.map(contestDetailsRepository.findByStatusOrderByIdentifier(true), List.class));
        contestDetailsWsDto.setBaseUrl(ADMIN_CONTESTDETAILS);
        return contestDetailsWsDto;
    }

    @PostMapping("/edit")
    @ResponseBody
    public ContestDetailsWsDto handelEdit(@RequestBody ContestDetailsWsDto request) {
        return contestDetailsService.handelEdit(request);
    }


    @PostMapping("/getEdit")
    @ResponseBody
    public ContestDetailsWsDto editContestDetails(@RequestBody ContestDetailsWsDto request) {
        ContestDetailsWsDto contestDetailsWsDto = new ContestDetailsWsDto();
        contestDetailsWsDto.setBaseUrl(ADMIN_CONTESTDETAILS);
        ContestDetails contestDetails= contestDetailsRepository.findByRecordId(request.getContestDetailsDtoList().get(0).getRecordId());
        contestDetailsWsDto.setContestDetailsDtoList(List.of(modelMapper.map(contestDetails, ContestDetailsDto.class)));
        return contestDetailsWsDto;

    }

    @PostMapping("/delete")
    @ResponseBody
    public ContestDetailsWsDto deleteContestDetails(@RequestBody ContestDetailsWsDto contestDetailsWsDto) {
        for (ContestDetailsDto contestDetailsDto : contestDetailsWsDto.getContestDetailsDtoList()) {
            contestDetailsRepository.findByRecordId(contestDetailsDto.getRecordId());
        }
        contestDetailsWsDto.setBaseUrl(ADMIN_CONTESTDETAILS);
        contestDetailsWsDto.setMessage("Data Deleted Successfully");
        return contestDetailsWsDto;
    }

    @GetMapping("/getAdvancedSearch")
    @ResponseBody
    public List<SearchDto> getSearchAttributes() {
        return getGroupedParentAndChildAttributes(new ContestDetails());
    }

}