package com.example.tests;

import java.util.concurrent.TimeUnit;




import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.*;

public class GroupCreationTests extends TestBase {

    @Test
    public void testValidGroupCanBeCreated() throws Exception {

//        TestBase myObject = new TestBase ();
        goToStart();
//        TestBase myTestBase = new TestBase();
//        myTestBase.goToStart();

        useTestCase(new UseTestCaseClass("RAW\n INGREDIENTS", "div.project-description-item-block > div.title-text.project-description-item-block__title"));
        useTestCase(new UseTestCaseClass("TRANSFORMATION", "div.project-description-item-block__transformation-block > div.title-text.project-description-item-block__title"));
        useTestCase(new UseTestCaseClass("HIGHLIGHTS", "div.project-description-item-block__highlights-block > div.title-text.project-description-item-block__title"));

    }



}


