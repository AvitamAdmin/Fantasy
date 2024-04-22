package com.avitam.fantasy11.qa.framework;

import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;


public class AnnotationTransformer implements IAnnotationTransformer {
    private int invocationCount;

    private int maxThreadCount;

    public AnnotationTransformer(int aInvocationCount, int aMaxThreadCount) {
        this.invocationCount = aInvocationCount;
        this.maxThreadCount = aMaxThreadCount;
    }

    @Override
    public void transform(ITestAnnotation annotation, Class testClass,
                          Constructor testConstructor, Method testMethod) {
        annotation.setInvocationCount(invocationCount);
        annotation.setThreadPoolSize(maxThreadCount);
    }
}
