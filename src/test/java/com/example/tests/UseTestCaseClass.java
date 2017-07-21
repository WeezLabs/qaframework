package com.example.tests;

public class UseTestCaseClass extends TestBase

{

    private final String labelTextParagraph;
    private final String labelTestSelector;

    public UseTestCaseClass(String labelTextParagraph, String labelTextParagraphSelector) {
        this.labelTextParagraph = labelTextParagraph;
        this.labelTestSelector = labelTextParagraphSelector;
    }

    public String getLabelTextParagraph() {
        return labelTextParagraph;
    }

    public String getLabelTestSelector() {
        return labelTestSelector;
    }
}
