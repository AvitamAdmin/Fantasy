package com.avitam.fantasy11.web.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.avitam.fantasy11.core.model.*;
import com.avitam.fantasy11.form.ToolkitForm;
import com.avitam.fantasy11.qa.service.QualityAssuranceService;
import jakarta.servlet.http.HttpServletRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.ParseException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/qa")
public class QualityAssuranceController {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    private TestResultRepository testResultRepository;

    @Autowired
    private TestPlanRepository testPlanRepository;

    @Autowired
    private TestProfileRepository testProfileRepository;

    @Autowired
    private QualityAssuranceService qualityAssuranceService;

    @Autowired
    private ProductRepository productRepository;

    @GetMapping(value = "/chart")
    public String getTestCharts(Model model){
        return "qa/qachart";
    }

    @GetMapping("/results")
    public String getReportData(Model model) throws IOException {
        List<TestResult> results = testResultRepository.findAll();
        Collections.sort(results, new Comparator<TestResult>() {
            @Override
            public int compare(TestResult o1, TestResult o2) {
                return Integer.compare(o2.getId().intValue(), o1.getId().intValue());
            }
        });
        model.addAttribute("results", results);
        return "qa/qaresults";
    }

    @GetMapping("/testrun")
    public String getTestData(Model model) throws IOException {
        model.addAttribute("testProfiles", testProfileRepository.findAll());
        model.addAttribute("testPlans", testPlanRepository.findAll());
        model.addAttribute("products", productRepository.findAll());
        model.addAttribute("editForm", new ToolkitForm());
        return "testrun/edit";
    }

    @PostMapping("/testrun")
    public String generateResults(Model model, HttpServletRequest request) throws ParseException, JsonProcessingException {
        qualityAssuranceService.populateTestData(request.getParameterMap());
        return "redirect:/qa/results";
    }

    @RequestMapping(value = "/getTestProfiles/{planId}", method = RequestMethod.GET)
    public @ResponseBody
    List<TestProfile> getTestProfiles(@PathVariable("planId") String planId) {
        return testProfileRepository.findAll().stream().filter(testProfile -> testProfile.getTestPlan().equalsIgnoreCase(planId)).collect(Collectors.toList());
    }
}
