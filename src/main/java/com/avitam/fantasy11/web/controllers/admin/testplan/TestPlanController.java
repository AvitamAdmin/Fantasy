package com.avitam.fantasy11.web.controllers.admin.testplan;

import com.avitam.fantasy11.core.model.TestPlan;
import com.avitam.fantasy11.core.model.TestPlanRepository;
import com.avitam.fantasy11.core.service.CoreService;
import com.avitam.fantasy11.form.TestPlanForm;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/testplan")
public class TestPlanController {
    @Autowired
    private TestPlanRepository testPlanRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CoreService coreService;


    @GetMapping
    public String getAll(Model model) {
        List<TestPlan> testPlan = testPlanRepository.findAll();
        model.addAttribute("models", testPlan);
        return "testplan/testplans";
    }

    @GetMapping("/edit")
    public String edit(@RequestParam("id") Long id, Model model) {
        TestPlanForm testPlanForm = null;
        Optional<TestPlan> environmentOptional = testPlanRepository.findById(id);
        if (environmentOptional.isPresent()) {
            TestPlan testplan = environmentOptional.get();
            testPlanForm = modelMapper.map(testplan, TestPlanForm.class);
            model.addAttribute("editForm", testPlanForm);
        }

        return "testplan/edit";
    }

    @PostMapping("/edit")
    public String handleEdit(@ModelAttribute("editForm") TestPlanForm testplanForm, Model model, BindingResult result) {

        if (result.hasErrors()) {
            model.addAttribute("message", result);
            model.addAttribute("editForm", testplanForm);
            return "testplan/edit";
        }
        testplanForm.setLastModified(new Date());
        if (testplanForm.getId() == null) {
            testplanForm.setCreationTime(new Date());
            testplanForm.setCreator(coreService.getCurrentUser().getUsername());
        }
        TestPlan testplan = modelMapper.map(testplanForm, TestPlan.class);
        testPlanRepository.save(testplan);
        model.addAttribute("editForm", testplanForm);
        return "redirect:/admin/testplan";
    }

    @GetMapping("/add")
    public String add(Model model) {
        TestPlanForm form = new TestPlanForm();
        form.setCreationTime(new Date());
        form.setLastModified(new Date());
        form.setStatus(true);
        form.setCreator(coreService.getCurrentUser().getUsername());
        model.addAttribute("editForm", form);
        return "testplan/edit";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("id") String ids, Model model) {
        for (String id : ids.split(",")) {
            testPlanRepository.deleteById(Long.valueOf(id));
        }
        return "redirect:/admin/testplan";
    }
}
