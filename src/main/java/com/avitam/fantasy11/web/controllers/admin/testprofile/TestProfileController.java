package com.avitam.fantasy11.web.controllers.admin.testprofile;

import com.avitam.fantasy11.core.model.*;
import com.avitam.fantasy11.core.service.CoreService;
import com.avitam.fantasy11.form.TestProfileForm;
import com.avitam.fantasy11.qa.service.QualityAssuranceService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin/testprofile")
public class TestProfileController {
    @Autowired
    private TestProfileRepository testProfileRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CoreService coreService;

    @Autowired
    private TestPlanRepository testPlanRepository;
    @Autowired
    private EnvironmentRepository environmentRepository;
    @Autowired
    private PersonaRepository personaRepository;
    @Autowired
    private QualityAssuranceService qualityAssuranceService;

    @GetMapping
    public String getAll(Model model) {
        List<TestProfile> testProfiles = testProfileRepository.findAll();
        model.addAttribute("models", testProfiles);
        return "testprofile/testprofiles";
    }

    @GetMapping("/edit")
    public String edit(@RequestParam("id") Long id, Model model) {
        model.addAttribute("existingParamsCount", 0);
        TestProfileForm testProfileForm = null;
        Optional<TestProfile> testProfileOptional = testProfileRepository.findById(id);
        if (testProfileOptional.isPresent()) {
            TestProfile testProfile = testProfileOptional.get();
            testProfileForm = modelMapper.map(testProfile, TestProfileForm.class);
            model.addAttribute("editForm", testProfileForm);
            if (CollectionUtils.isNotEmpty(testProfile.getParamInput())) {
                model.addAttribute("existingParamsCount", testProfile.getParamInput().size());
            }
        }
        populateTestProfileData(model);
        return "testprofile/edit";
    }

    @RequestMapping(value = "/getValuesByKey/{keyId}", method = RequestMethod.GET)
    public @ResponseBody
    Collection<Object> getData(@PathVariable("keyId") String keyId) {
        return qualityAssuranceService.getQaProfileMap().get(keyId);
    }

    private void populateTestProfileData(Model model) {
        HashMap dataMap = qualityAssuranceService.getQaProfileMap();
        model.addAttribute("dataMap", dataMap);
        model.addAttribute("testPlans", testPlanRepository.findAll());
    }

    @PostMapping("/edit")
    public String handleEdit(@ModelAttribute("editForm") TestProfileForm testProfileForm, Model model, BindingResult result) {
        populateTestProfileData(model);
        if (result.hasErrors()) {
            model.addAttribute("message", result);
            model.addAttribute("editForm", testProfileForm);
            return "testprofile/edit";
        }
        testProfileForm.setLastModified(new Date());
        if (testProfileForm.getId() == null) {
            testProfileForm.setCreationTime(new Date());
            testProfileForm.setCreator(coreService.getCurrentUser().getUsername());
        }
        TestProfile testProfile = modelMapper.map(testProfileForm, TestProfile.class);
        if (CollectionUtils.isNotEmpty(testProfile.getParamInput())) {
            List<ParamInput> params = testProfile.getParamInput().stream().filter(paramInput -> StringUtils.isNotEmpty(paramInput.getParamKey())).collect(Collectors.toList());
            params.sort(Comparator.comparing(inputParam -> inputParam.getParamKey()));
            testProfile.setParamInput(params);
        }
        testProfileRepository.save(testProfile);
        model.addAttribute("editForm", testProfileForm);
        return "redirect:/admin/testprofile";
    }

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("existingParamsCount", 0);
        TestProfileForm form = new TestProfileForm();
        form.setCreationTime(new Date());
        form.setLastModified(new Date());
        form.setStatus(true);
        form.setCreator(coreService.getCurrentUser().getUsername());
        model.addAttribute("editForm", form);
        populateTestProfileData(model);
        return "testprofile/edit";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("id") String ids, Model model) {
        for (String id : ids.split(",")) {
            testProfileRepository.deleteById(Long.valueOf(id));
        }
        return "redirect:/admin/testprofile";
    }
}
