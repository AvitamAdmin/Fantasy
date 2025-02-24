package com.avitam.fantasy11.web.controllers.admin.websiteSetting;

import com.avitam.fantasy11.api.dto.SearchDto;
import com.avitam.fantasy11.api.dto.WebsiteSettingDto;
import com.avitam.fantasy11.api.dto.WebsiteSettingsWsDto;
import com.avitam.fantasy11.api.service.WebsiteSettingService;
import com.avitam.fantasy11.model.WebsiteSetting;
import com.avitam.fantasy11.repository.WebsiteSettingRepository;
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

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/admin/website")
public class WebsiteSettingController extends BaseController {

    private static final String ADMIN_WEBSITESETTING = "/admin/website";
    @Autowired
    private WebsiteSettingRepository websiteSettingRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private WebsiteSettingService websiteSettingService;

    @PostMapping
    public WebsiteSettingsWsDto getAll(@RequestBody WebsiteSettingsWsDto websiteWsDto) {
        Pageable pageable = getPageable(websiteWsDto.getPage(), websiteWsDto.getSizePerPage(), websiteWsDto.getSortDirection(), websiteWsDto.getSortField());
        WebsiteSettingDto websiteDto = CollectionUtils.isNotEmpty(websiteWsDto.getWebsiteSettingDtoList()) ? websiteWsDto.getWebsiteSettingDtoList().get(0) : new WebsiteSettingDto();
        WebsiteSetting website = modelMapper.map(websiteDto, WebsiteSetting.class);
        Page<WebsiteSetting> page = isSearchActive(website) == null ? websiteSettingRepository.findAll(Example.of(website), pageable) : websiteSettingRepository.findAll(pageable);
        websiteWsDto.setWebsiteSettingDtoList(modelMapper.map(page.getContent(), List.class));
        websiteWsDto.setBaseUrl(ADMIN_WEBSITESETTING);
        websiteWsDto.setTotalPages(page.getTotalPages());
        websiteWsDto.setTotalRecords(page.getTotalElements());
        return websiteWsDto;
    }


    @GetMapping("/get")
    public WebsiteSettingsWsDto getActiveWebsite() {
        WebsiteSettingsWsDto websiteWsDto = new WebsiteSettingsWsDto();
        websiteWsDto.setWebsiteSettingDtoList(List.of(modelMapper.map(websiteSettingRepository.findByStatusOrderByIdentifier(true), WebsiteSettingDto.class)));
        websiteWsDto.setBaseUrl(ADMIN_WEBSITESETTING);
        return websiteWsDto;
    }

    @PostMapping("/getedit")
    public WebsiteSettingsWsDto edit(@RequestBody WebsiteSettingsWsDto request) {
        WebsiteSettingsWsDto websiteWsDto = new WebsiteSettingsWsDto();
        websiteWsDto.setBaseUrl(ADMIN_WEBSITESETTING);
        WebsiteSetting website = websiteSettingRepository.findByRecordId(request.getWebsiteSettingDtoList().get(0).getRecordId());
        websiteWsDto.setWebsiteSettingDtoList(modelMapper.map(List.of(website), List.class));
        return websiteWsDto;
    }

    @PostMapping(value = "/edit", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public WebsiteSettingsWsDto handleEdit(@RequestParam("logo") MultipartFile logo,
                                           @RequestParam("favicon") MultipartFile favicon,
                                           @RequestParam("identifier") String identifier,
                                           @RequestParam("sportsApiUrl") String sportsApiUrl,
                                           @RequestParam("sportsApiKey") String sportsApiKey,
                                           @RequestParam("mailId") String mailId,
                                           @RequestParam("mailPassword") String mailPassword,
                                           @RequestParam("smtpHost") String smtpHost,
                                           @RequestParam("smtpPort") String smtpPort,
                                           @RequestParam("otpKey") String otpKey,
                                           @RequestParam("otpProvider") String otpProvider,
                                           @RequestParam("paymentKey") String paymentKey,
                                           @RequestParam("paymentProvider") String paymentProvider) throws IOException {

        WebsiteSettingsWsDto request = new WebsiteSettingsWsDto();
        WebsiteSettingDto websiteDto = new WebsiteSettingDto();
        websiteDto.setLogo(logo);
        websiteDto.setFavicon(favicon);
        websiteDto.setIdentifier(identifier);
        websiteDto.setMailId(mailId);
        websiteDto.setMailPassword(mailPassword);
        websiteDto.setSportsApiUrl(sportsApiUrl);
        websiteDto.setSportsApiKey(sportsApiKey);
        websiteDto.setOtpKey(otpKey);
        websiteDto.setOtpProvider(otpProvider);
        websiteDto.setSmtpPort(smtpPort);
        websiteDto.setSmtpHost(smtpHost);
        websiteDto.setPaymentKey(paymentKey);
        websiteDto.setPaymentProvider(paymentProvider);

        request.setWebsiteSettingDtoList(List.of(websiteDto));
        return websiteSettingService.handleEdit(request);
    }

    @PostMapping(value = "/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public WebsiteSettingsWsDto updateData(@RequestParam("logo") MultipartFile logo,
                                           @RequestParam("favicon") MultipartFile favicon,
                                           @RequestParam("identifier") String identifier,
                                           @RequestParam("sportsApiUrl") String sportsApiUrl,
                                           @RequestParam("sportsApiKey") String sportsApiKey,
                                           @RequestParam("mailId") String mailId,
                                           @RequestParam("mailPassword") String mailPassword,
                                           @RequestParam("smtpHost") String smtpHost,
                                           @RequestParam("smtpPort") String smtpPort,
                                           @RequestParam("otpKey") String otpKey,
                                           @RequestParam("otpProvider") String otpProvider,
                                           @RequestParam("paymentKey") String paymentKey,
                                           @RequestParam("paymentProvider") String paymentProvider,
                                           @RequestParam("recordId") String recordId) throws IOException {

        WebsiteSettingsWsDto request = new WebsiteSettingsWsDto();
        WebsiteSettingDto websiteDto = new WebsiteSettingDto();
        websiteDto.setLogo(logo);
        websiteDto.setFavicon(favicon);
        websiteDto.setIdentifier(identifier);
        websiteDto.setMailId(mailId);
        websiteDto.setMailPassword(mailPassword);
        websiteDto.setSportsApiUrl(sportsApiUrl);
        websiteDto.setSportsApiKey(sportsApiKey);
        websiteDto.setOtpKey(otpKey);
        websiteDto.setOtpProvider(otpProvider);
        websiteDto.setSmtpPort(smtpPort);
        websiteDto.setSmtpHost(smtpHost);
        websiteDto.setPaymentKey(paymentKey);
        websiteDto.setPaymentProvider(paymentProvider);
        websiteDto.setRecordId(recordId);
        request.setWebsiteSettingDtoList(List.of(websiteDto));
        return websiteSettingService.handleEdit(request);
    }


    @PostMapping("/delete")
    public WebsiteSettingsWsDto delete(@RequestBody WebsiteSettingsWsDto request) {
        for (WebsiteSettingDto data : request.getWebsiteSettingDtoList()) {
            websiteSettingRepository.deleteByRecordId(data.getRecordId());
        }
        request.setBaseUrl(ADMIN_WEBSITESETTING);
        request.setMessage("Data Deleted Successfully");
        return request;
    }

    @GetMapping("/getAdvancedSearch")
    @ResponseBody

    public List<SearchDto> getSearchAttributes() {
        return getGroupedParentAndChildAttributes(new WebsiteSetting());
    }

}
