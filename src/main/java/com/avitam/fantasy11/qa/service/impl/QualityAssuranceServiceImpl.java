package com.avitam.fantasy11.qa.service.impl;

import com.avitam.fantasy11.core.SpringContext;
import com.avitam.fantasy11.core.model.*;
import com.avitam.fantasy11.form.TestStepInfo;
import com.avitam.fantasy11.qa.framework.ExtentManager;
import com.avitam.fantasy11.qa.service.QualityAssuranceService;
import com.avitam.fantasy11.qa.utils.TestDataUtils;
import com.avitam.fantasy11.qa.utils.TestDataUtils.Field;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.testng.TestNG;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class QualityAssuranceServiceImpl implements QualityAssuranceService {

    public static final String THREAD_DEFAULT_VALUE = "2";
    public static final String TEST_PROFILE = "testProfile";
    public static final String PERSONAS = "personas";
    public static final String ENVIRONMENTS = "environments";
    public static final String TESTRUN_MAX_THREAD_COUNT = "qa.testrun.max.thread.count";
    public static final String SHORTCUTS = "shortcuts";
    public static final String REPORT_FILE_NAME = "reportFileName";
    private static final String DATE_FORMAT = "yyyy-MM-dd_HH-mm-ss";
    private static final String EXTENT_HTML = "_Extent.html";
    private static final String DELIM = " \n\r\t,.;";
    private static final String TEST_PLAN = "testPlan";
    @Autowired
    private ExtentManager extentManager;

    @Autowired
    private EnvironmentRepository environmentRepository;


    @Autowired
    private PersonaRepository personaRepository;

    @Autowired
    private TestProfileRepository testProfileRepository;

    @Autowired
    private TestPlanRepository testPlanRepository;

    @Autowired
    private Environment env;

    @Autowired
    private ModelMapper modelMapper;


    @Autowired
    private TestResultRepository testResultRepository;

    private static void populateParamData(Map<String, String> testMapData, CommonQaFields dataObj, String prefix) {
        for (ParamInput paramMap : dataObj.getParamInput()) {
            testMapData.put(prefix + paramMap.getParamKey(), paramMap.getParamValue());
        }
    }

    @Override
    public HashMap<String, Collection> getQaProfileMap() {
        HashMap<String, Collection> dataMap = new HashMap();
        dataMap.put(PERSONAS, personaRepository.findAll());
        dataMap.put(ENVIRONMENTS, environmentRepository.findAll());
        return dataMap;
    }

    private HashMap<String, CommonQaRepository> getQaProfileRepoMap() {
        HashMap<String, CommonQaRepository> dataMap = new HashMap();
        dataMap.put(PERSONAS, personaRepository);
        dataMap.put(ENVIRONMENTS, environmentRepository);
        return dataMap;
    }

    @Override
    public void populateTestData(Map<String, String[]> parameterMap) {

        Map<String, String> testMapData = new HashMap<>();
        populateTestData(parameterMap, testMapData);

        String currentTime = new SimpleDateFormat(DATE_FORMAT).format(Calendar.getInstance().getTime());
        String reportName = currentTime + EXTENT_HTML;
        testMapData.put(REPORT_FILE_NAME, reportName);

        extentManager.startNewReport(reportName);
        TestPlan testPlan = testPlanRepository.findById(Long.valueOf(testMapData.get(TEST_PLAN))).get();
        String testPlanClassName = TestDataUtils.TEST_CLASSES_MAP.get(testPlan.getIdentifier());
        List<TestStepInfo> testStepInfos = modelMapper.map(testPlan.getTestSteps(), new TypeToken<List<TestStepInfo>>() {}.getType());


        TestNG testNG = SpringContext.getBean(TestNG.class, testPlanClassName, testMapData, testStepInfos);
        testNG.run();

        extentManager.flush();
    }

    private void populateTestData(Map<String, String[]> parameterMap, Map<String, String> testMapData) {
        for (Map.Entry<String, String[]> entries : parameterMap.entrySet()) {
            testMapData.put(entries.getKey(), StringUtils.join(entries.getValue(), TestDataUtils.COMMA));
        }

        Map<String, CommonQaRepository> dataRepoMap = getQaProfileRepoMap();
        TestProfile testProfile = testProfileRepository.findById(Long.valueOf(testMapData.get(TEST_PROFILE))).get();
        testMapData.put(Field.MAX_THREAD_COUNT.toString(), env.getProperty(TESTRUN_MAX_THREAD_COUNT, THREAD_DEFAULT_VALUE));
        populateSkuData(testMapData);

        for (ParamInput paramInput : testProfile.getParamInput()) {
            populateTestData(testMapData, dataRepoMap, paramInput);
        }
        com.avitam.fantasy11.core.model.Environment environment = environmentRepository.findByIdentifier(String.valueOf(testMapData.get(ENVIRONMENTS)));
        testMapData.put(Field.HYBRIS_ENVIRONMENT.toString(), environment.getHybrisUrlFormula());
        testMapData.put(Field.AEM_ENVIRONMENT.toString(), environment.getAemUrlFormula());
    }

    @Override
    public void saveTestResult(final Map<String, Object> testData, final String orderNumber, final String sku) {
        String fileName = String.valueOf(testData.get(REPORT_FILE_NAME));
        TestPlan testPlan = testPlanRepository.findById(Long.valueOf(String.valueOf(testData.get(TEST_PLAN)))).get();
        TestResult testResult = new TestResult();
        testResult.setReportFilePath("/reports/" + fileName);
        testResult.setStatus("Success");
        testResult.setTimeStamp(fileName.split(EXTENT_HTML)[0]);
        testResult.setProductUrls(testData.get(Field.HYBRIS_ENVIRONMENT.toString()).toString().replaceAll("\\{sku}", sku));
        testResult.setOrderNumber(orderNumber);
        testResult.setTestName(testPlan.getIdentifier());
        testResultRepository.save(testResult);
        cleanupGarbageData();
    }

    private void cleanupGarbageData() {
        List<TestResult> testResultList = testResultRepository.findAll().stream().filter(checkout -> StringUtils.isEmpty(checkout.getStatus())).collect(Collectors.toList());
        testResultRepository.deleteAll(testResultList);
    }

    private void populateSkuData(Map<String, String> testMapData) {
        String skus = testMapData.get(Field.SKUS.toString());
        testMapData.put(Field.TEST_COUNT.toString(), String.valueOf(testMapData.get(Field.SKUS.toString()).split(TestDataUtils.COMMA).length));
    }

    private void populateTestData(Map<String, String> testMapData, Map<String, CommonQaRepository> dataRepoMap, ParamInput paramInput) {
        CommonQaRepository commonQaRepository = dataRepoMap.get(paramInput.getParamKey());
        CommonQaFields dataObj = commonQaRepository.findByIdentifier(paramInput.getParamValue());
        if (CollectionUtils.isEmpty(dataObj.getParamInput())) {
            testMapData.put(paramInput.getParamKey(), dataObj.getIdentifier());
        } else {
            populateParamData(testMapData, dataObj, "");
        }

    }
}
