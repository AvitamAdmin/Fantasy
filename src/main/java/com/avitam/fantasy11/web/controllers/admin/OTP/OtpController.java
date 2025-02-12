package com.avitam.fantasy11.web.controllers.admin.OTP;

import com.avitam.fantasy11.api.dto.OtpDto;
import com.avitam.fantasy11.api.dto.OtpWsDto;
import com.avitam.fantasy11.api.dto.SearchDto;
import com.avitam.fantasy11.api.service.OtpService;
import com.avitam.fantasy11.model.OTP;
import com.avitam.fantasy11.repository.OtpRepository;
import com.avitam.fantasy11.web.controllers.BaseController;
import org.apache.commons.collections4.CollectionUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/otp")
public class OtpController extends BaseController {
    public static final String ADMIN_OTP = "/admin/otp";
    @Autowired
    private OtpRepository otpRepository;
    @Autowired
    private OtpService otpService;
    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    @ResponseBody
    public OtpWsDto getAllOtp(OtpWsDto otpWsDto) {
        Pageable pageable = getPageable(otpWsDto.getPage(), otpWsDto.getSizePerPage(), otpWsDto.getSortDirection(), otpWsDto.getSortField());
        OtpDto otpDto = CollectionUtils.isNotEmpty(otpWsDto.getOtpDtoList()) ? otpWsDto.getOtpDtoList().get(0) : new OtpDto();
        OTP otp = modelMapper.map(otpDto, OTP.class);
        Page<OTP> page = isSearchActive(otp) != null ? otpRepository.findAll(Example.of(otp), pageable) : otpRepository.findAll(pageable);
        otpWsDto.setOtpDtoList(modelMapper.map(page.getContent(), List.class));
        otpWsDto.setTotalPages(page.getTotalPages());
        otpWsDto.setTotalRecords(page.getTotalElements());
        otpWsDto.setBaseUrl(ADMIN_OTP);
        return otpWsDto;
    }

    @GetMapping("/get")
    @ResponseBody
    public OtpWsDto getActiveOtp() {
        OtpWsDto otpWsDto = new OtpWsDto();
        otpWsDto.setOtpDtoList(modelMapper.map(otpRepository.findByStatusOrderByIdentifier(true), List.class));
        otpWsDto.setBaseUrl(ADMIN_OTP);
        return otpWsDto;
    }

    @PostMapping("/getEdit")
    @ResponseBody
    public OtpWsDto editOtp(@RequestBody OtpWsDto request) {
        OtpWsDto otpWsDto = new OtpWsDto();
        OTP otp = otpRepository.findByRecordId(request.getOtpDtoList().get(0).getRecordId());
        otpWsDto.setBaseUrl(ADMIN_OTP);
        if (otp != null) {
            otpWsDto.setOtpDtoList(List.of(modelMapper.map(otp, OtpDto.class)));
        }
        return otpWsDto;
    }

    @PostMapping("/edit")
    @ResponseBody
    public OtpWsDto handleEdit(@RequestBody OtpWsDto otpWsDto) {
        return otpService.handelEdit(otpWsDto);
    }

    @PostMapping("/delete")
    @ResponseBody
    public OtpWsDto deleteByOtp(@RequestBody OtpWsDto request) {
        for (OtpDto otpDto : request.getOtpDtoList()) {
            otpRepository.deleteByRecordId(otpDto.getRecordId());
        }
        request.setBaseUrl(ADMIN_OTP);
        request.setMessage("Data deleted successfully");
        return request;
    }

    @GetMapping("/getAdvancedSearch")
    @ResponseBody

    public List<SearchDto> getSearchAttributes() {
        return getGroupedParentAndChildAttributes(new OTP());
    }

}
