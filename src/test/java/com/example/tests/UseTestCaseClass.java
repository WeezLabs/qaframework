package com.example.tests;

public class UseTestCaseClass {
    private final String labelTestCase;
    private final String labelTestSelector;

    public UseTestCaseClass(String labelTestCase, String labelTestSelector) {
        this.labelTestCase = labelTestCase;
        this.labelTestSelector = labelTestSelector;
    }

    public String getLabelTestCase() {
        return labelTestCase;
    }

    public String getLabelTestSelector() {
        return labelTestSelector;
    }
}
