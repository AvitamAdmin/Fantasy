package com.avitam.fantasy11.web.controllers.admin.address;

import com.avitam.fantasy11.core.service.CoreService;
import com.avitam.fantasy11.form.AddressForm;
import com.avitam.fantasy11.form.InterfaceForm;
import com.avitam.fantasy11.model.*;
import com.avitam.fantasy11.validation.AddressFormValidator;
import com.avitam.fantasy11.validation.InterfaceFormValidator;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin/address")
public class AddressController {
    public static final int TOOL_KIT_NODE = 3;
    public static final int SCHEDULER_NODE = 2;
    public static final String TOOLKITS = "Toolkits";
    public static final String SCHEDULING = "Scheduling";

    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private NodeRepository nodeRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CoreService coreService;

    @GetMapping
    public String getAllModels(Model model) {
        //model.addAttribute("models", addressRepository.findAll().stream().filter(address -> address.getUserId() != null).collect(Collectors.toList()));
        model.addAttribute("models", addressRepository.findAll());
        return "address/addresses";
    }

    @GetMapping("/edit")
    public String editAddress(@RequestParam("id") ObjectId id, Model model) {
        AddressForm addressForm = null;
        Optional<Address> addressOptional = addressRepository.findById(id);
        if (addressOptional.isPresent()) {
            Address address = addressOptional.get();
            addressForm = modelMapper.map(address, AddressForm.class);
            model.addAttribute("editForm", addressForm);
        }
        //model.addAttribute("nodes", addressRepository.findAll().stream().filter(node -> node.getParentNode() == null).collect(Collectors.toList()));
        return "address/edit";
    }

    @PostMapping("/edit")
    public String handleEdit(@ModelAttribute("editForm") AddressForm addressForm, Model model, BindingResult result) {
        new AddressFormValidator().validate(addressForm, result);
        if (result.hasErrors()) {
            model.addAttribute("message", result);
            return "address/edit";
        }
        addressForm.setLastModified(new Date());
        if (addressForm.getId() == null) {
            addressForm.setCreationTime(new Date());
            addressForm.setCreator(coreService.getCurrentUser().getEmailId());
            addressForm.setMobileNumber(coreService.getCurrentUser().getMobileNumber());
        }
        Address address = modelMapper.map(addressForm, Address.class);

        Optional<Address> addressOptional = addressRepository.findById(addressForm.getId());
        if(addressOptional.isPresent()){
            address.setId(addressOptional.get().getId());
        }

       /* if (StringUtils.isNotEmpty(interfaceForm.getParentNodeId())) {
            node.setParentNode(nodeRepository.getByIds(Long.valueOf(interfaceForm.getParentNodeId())));
        }*/

        addressRepository.save(address);
        model.addAttribute("editForm", addressForm);
        return "redirect:/admin/address";
    }

    @GetMapping("/add")
    public String addAddress(Model model) {
        AddressForm form = new AddressForm();
        form.setCreationTime(new Date());
        form.setLastModified(new Date());
        form.setStatus(true);
        form.setCreator(coreService.getCurrentUser().getEmailId());
        form.setMobileNumber(coreService.getCurrentUser().getMobileNumber());
        model.addAttribute("editForm", form);
       // model.addAttribute("nodes", addressRepository.findAll().stream().filter(node -> node.getUserId() == null).collect(Collectors.toList()));
        return "address/edit";
    }

    @GetMapping("/delete")
    public String deleteAddress(@RequestParam("id") ObjectId id, Model model) {
        /*for (String id : ids.split(",")) {
            addressRepository.deleteById(Integer.valueOf(id));
        }*/
        addressRepository.deleteById(id);
        return "redirect:/admin/address";
    }
}
