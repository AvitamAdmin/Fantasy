package com.avitam.fantasy11.web.controllers.admin.address;

import com.avitam.fantasy11.api.dto.AddressDto;
import com.avitam.fantasy11.api.dto.AddressWsDto;
import com.avitam.fantasy11.api.service.AddressService;
import com.avitam.fantasy11.model.Address;
import com.avitam.fantasy11.repository.AddressRepository;
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
@RequestMapping("/admin/address")
public class AddressController extends BaseController {
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private ModelMapper modelMapper;


    @Autowired
    private AddressService addressService;

    public static final String ADMIN_ADDRESS = "/admin/address";

    @PostMapping
    @ResponseBody
    public AddressWsDto getAllAddress(@RequestBody AddressWsDto addressWsDto) {
        Pageable pageable = getPageable(addressWsDto.getPage(), addressWsDto.getSizePerPage(), addressWsDto.getSortDirection(), addressWsDto.getSortField());
        AddressDto addressDto = CollectionUtils.isNotEmpty(addressWsDto.getAddressDtoList()) ? addressWsDto.getAddressDtoList().get(0) : null;
        Address address = addressDto != null ? modelMapper.map(addressDto, Address.class) : null;
        Page<Address> page = isSearchActive(address) != null ? addressRepository.findAll(Example.of(address), pageable) : addressRepository.findAll(pageable);
        addressWsDto.setAddressDtoList(modelMapper.map(page.getContent(), List.class));
        addressWsDto.setTotalPages(page.getTotalPages());
        addressWsDto.setTotalRecords(page.getTotalElements());
        addressWsDto.setBaseUrl(ADMIN_ADDRESS);
        return addressWsDto;
    }

    @GetMapping("/get")
    @ResponseBody
    public AddressWsDto getActiveAddressList() {
        AddressWsDto addressWsDto = new AddressWsDto();
        addressWsDto.setAddressDtoList(modelMapper.map(addressRepository.findByStatusOrderByIdentifier(true), List.class));
        addressWsDto.setBaseUrl(ADMIN_ADDRESS);
        return addressWsDto;
    }

    @PostMapping("/getedit")
    @ResponseBody
    public AddressWsDto editAddress(@RequestBody AddressWsDto addressWsDto) {

        Address address = addressRepository.findByRecordId(addressWsDto.getAddressDtoList().get(0).getRecordId());
        addressWsDto.setAddressDtoList(List.of(modelMapper.map(address, AddressDto.class)));
        addressWsDto.setBaseUrl(ADMIN_ADDRESS);
        return addressWsDto;
    }

    @PostMapping("/edit")
    @ResponseBody
    public AddressWsDto handleEdit(@RequestBody AddressWsDto request) {
        return addressService.handleEdit(request);
    }

    @PostMapping("/delete")
    @ResponseBody
    public AddressWsDto deleteAddress(@RequestBody AddressWsDto addressWsDto) {
        for (AddressDto addressDto : addressWsDto.getAddressDtoList()) {
            addressRepository.deleteByRecordId(addressDto.getRecordId());
        }
        addressWsDto.setMessage("Data deleted successfully");
        addressWsDto.setBaseUrl(ADMIN_ADDRESS);
        return addressWsDto;
    }
}
