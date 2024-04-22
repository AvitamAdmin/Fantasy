package com.avitam.fantasy11.web.controllers.admin.testplanbuilder;

import com.avitam.fantasy11.core.model.TestPlan;
import com.avitam.fantasy11.core.model.TestPlanRepository;
import com.avitam.fantasy11.core.service.CoreService;
import com.avitam.fantasy11.core.service.ReflectionDataService;
import com.avitam.fantasy11.form.TestPlanBuilderForm;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin/testplanbuilder")
public class TestPlanBuilderController {
    @Autowired
    private TestPlanRepository testPlanRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ReflectionDataService reflectionDataService;

    @Autowired
    private CoreService coreService;

    @GetMapping
    public String getAll(Model model) {
        List<TestPlan> testPlan = testPlanRepository.findAll();
        model.addAttribute("models", testPlan);
        return "testplanbuilder/testplanbuilder";
    }

    @GetMapping("/add")
    public String add(Model model) {
        TestPlanBuilderForm form = new TestPlanBuilderForm();
        form.setCreationTime(new Date());
        form.setLastModified(new Date());
        form.setStatus(true);
        form.setCreator(coreService.getCurrentUser().getUsername());
        populateModel(model, form);

        return "testplanbuilder/edit";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("id") String ids, Model model) {
        for (String id : ids.split(",")) {
            testPlanRepository.deleteById(Long.valueOf(id));
        }

        return "redirect:/admin/testplanbuilder"; //TODO address the issue with redirection to an empty page in local environment
    }

    private void populateModel(Model model, TestPlanBuilderForm testPlanBuilderForm){
        model.addAttribute("editForm", testPlanBuilderForm);
        model.addAttribute("existingParamsCount", (testPlanBuilderForm.getTestSteps() != null) ? testPlanBuilderForm.getTestSteps().size() : 0);
        model.addAttribute("dataSet", reflectionDataService.findAllClassesUsingReflectionsLibrary("avitam.fantasy11.qa.pages.abstractpages"));
    }

    @GetMapping("/edit")
    public String edit(@RequestParam("id") Long id, Model model) {
        Optional<TestPlan> testPlanOptional = testPlanRepository.findById(id);
        if (testPlanOptional.isPresent()) {
            TestPlan testPlan = testPlanOptional.get();
            TestPlanBuilderForm testPlanBuilderForm = modelMapper.map(testPlan, TestPlanBuilderForm.class);
            populateModel(model, testPlanBuilderForm);
        }

        return "testplanbuilder/edit";
    }

    @PostMapping("/edit")
    public String handleEdit(@ModelAttribute("editForm") TestPlanBuilderForm form, Model model, BindingResult result) {
        if(CollectionUtils.isNotEmpty(form.getTestSteps())) {
            form.setTestSteps(form.getTestSteps().stream()
                    .filter(action -> !StringUtils.isEmpty(action.getClassName()) && !StringUtils.isEmpty(action.getMethodName()))
                    .collect(Collectors.toList()));
        }
        TestPlan testPlan = modelMapper.map(form, TestPlan.class); //TODO address the issue where saving results in cloning rows in the 'test_plan_action' table instead of updating them.
        testPlanRepository.save(testPlan);

        return "redirect:/admin/testplanbuilder";
    }

    @RequestMapping(value = "/getMethodName/{keyId}", method = RequestMethod.GET)
    public @ResponseBody Collection<String> getData(@PathVariable("keyId") String keyId) {
        return reflectionDataService.getMethodNamesForClass(keyId);
    }

    @RequestMapping(value = "/getFieldName/{keyId}", method = RequestMethod.GET)
    public @ResponseBody Collection<String> getDataForMode(@PathVariable("keyId") String keyId) {
        return reflectionDataService.getEnumNamesForClass(keyId);
    }
}
