package com.avitam.fantasy11.web.controllers.admin.kyc;

import com.avitam.fantasy11.api.dto.KYCDto;
import com.avitam.fantasy11.api.dto.KYCWsDto;
import com.avitam.fantasy11.api.dto.SearchDto;
import com.avitam.fantasy11.api.service.KycService;
import com.avitam.fantasy11.model.KYC;
import com.avitam.fantasy11.repository.KYCRepository;
import com.avitam.fantasy11.web.controllers.BaseController;
import org.apache.commons.collections4.CollectionUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/admin/kyc")
public class KYCController extends BaseController {

    private static final String ADMIN_KYC = "/admin/kyc";
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private KYCRepository kycRepository;
    @Autowired
    private KycService kycService;

    @PostMapping
    public KYCWsDto getAllKYC(@RequestBody KYCWsDto kycWsDto) {
        Pageable pageable = getPageable(kycWsDto.getPage(), kycWsDto.getSizePerPage(), kycWsDto.getSortDirection(), kycWsDto.getSortField());
        KYCDto kycDto = CollectionUtils.isNotEmpty(kycWsDto.getKycDtoList()) ? kycWsDto.getKycDtoList().get(0) : new KYCDto();
        KYC kyc = modelMapper.map(kycDto, KYC.class);
        Page<KYC> page = isSearchActive(kyc) != null ? kycRepository.findAll(Example.of(kyc), pageable) : kycRepository.findAll(pageable);
        kycWsDto.setKycDtoList(modelMapper.map(page.getContent(), List.class));
        kycWsDto.setBaseUrl(ADMIN_KYC);
        kycWsDto.setTotalPages(page.getTotalPages());
        kycWsDto.setTotalRecords(page.getTotalElements());
        return kycWsDto;
    }

    @GetMapping("/get")
    @ResponseBody
    public KYCWsDto getActiveKYC() {
        KYCWsDto kycWsDto = new KYCWsDto();
        kycWsDto.setKycDtoList(modelMapper.map(kycRepository.findByStatusOrderByIdentifier(true), List.class));
        kycWsDto.setBaseUrl(ADMIN_KYC);
        return kycWsDto;
    }

    @PostMapping("/getedit")
    @ResponseBody
    public KYCWsDto editKYC(@RequestBody KYCWsDto request) {
        KYCWsDto kycWsDto = new KYCWsDto();
        kycWsDto.setBaseUrl(ADMIN_KYC);
        KYC kyc = kycRepository.findByRecordId(request.getKycDtoList().get(0).getRecordId());
        request.setKycDtoList(List.of(modelMapper.map(kyc, KYCDto.class)));
        return request;
    }

    @PostMapping(value = "/edit", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public KYCWsDto handleEdit(@RequestParam("logo") MultipartFile logo,
                               @RequestParam("userId") String userId, @RequestParam("panNumber") String panNumber) {
        KYCWsDto request = new KYCWsDto();
        KYCDto kycDto = new KYCDto();
        kycDto.setPanImage(logo);
        kycDto.setPanNumber(panNumber);
        kycDto.setUserId(userId);
        request.setKycDtoList(List.of(kycDto));
        return kycService.handleEdit(request);
    }

    @PostMapping(value = "/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public KYCWsDto handleEdit(@RequestParam("logo") MultipartFile logo,
                               @RequestParam("userId") String userId,
                               @RequestParam("panNumber") String panNumber,
                               @RequestParam("recordId") String recordId) {
        KYCWsDto request = new KYCWsDto();
        KYCDto kycDto = new KYCDto();
        kycDto.setPanImage(logo);
        kycDto.setPanNumber(panNumber);
        kycDto.setUserId(userId);
        kycDto.setRecordId(recordId);
        request.setKycDtoList(List.of(kycDto));
        return kycService.handleEdit(request);
    }

    @PostMapping("/delete")
    @ResponseBody
    public KYCWsDto deleteKyc(@RequestBody KYCWsDto kycWsDto) {

        for (KYCDto id : kycWsDto.getKycDtoList()) {
            kycRepository.deleteByRecordId(id.getRecordId());
        }
        kycWsDto.setMessage("Data deleted Successfully !!");
        kycWsDto.setBaseUrl(ADMIN_KYC);
        return kycWsDto;
    }

    @GetMapping("/getAdvancedSearch")
    @ResponseBody
    public List<SearchDto> getSearchAttributes() {
        return getGroupedParentAndChildAttributes(new KYC());
    }

}