package com.avitam.fantasy11.web.controllers;

import com.avitam.fantasy11.core.model.TestResult;
import com.avitam.fantasy11.core.model.TestResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("/chart")
public class ChartReportController {

    @Autowired
    private TestResultRepository testResultRepository;

    @GetMapping
    public String getCharts(Model model) {
        return "chart";
    }

    @RequestMapping(value = "/testdata", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Integer> testData(@RequestParam("key") String key, Model model) {
        Map<String, Map<String, Integer>> chart = new HashMap<>();
        List<TestResult> allData = testResultRepository.findAll();
        Map<String, Integer> statusMap = new HashMap<>();
        Map<String, Integer> testNameMap = new HashMap<>();
        for (TestResult testResult : allData) {
            if (key.equalsIgnoreCase("byStatus")) {
                if (statusMap.containsKey(testResult.getStatus())) {
                    statusMap.put(testResult.getStatus(), statusMap.get(testResult.getStatus()) + 1);
                } else {
                    statusMap.put(testResult.getStatus(), 1);
                }
                chart.put("byStatus", statusMap);
            } else {
                if (testNameMap.containsKey(testResult.getTestName())) {
                    testNameMap.put(testResult.getTestName(), testNameMap.get(testResult.getTestName()) + 1);
                } else {
                    testNameMap.put(testResult.getTestName(), 1);
                }
                chart.put("byTestName", testNameMap);
            }
        }
        return chart.get(key);
    }
}