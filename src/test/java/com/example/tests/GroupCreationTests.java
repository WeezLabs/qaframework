package com.example.tests;

import org.testng.annotations.*;

public class GroupCreationTests extends TestBase {

   @Test
   public void testValidGroupCanBeCreated() throws Exception {

        app.getGroupHelper().goToStart();

        app.getGroupHelper().useTestCase(new UseTestCaseClass("RAW\n INGREDIENTS", "div.project-description-item-block > div.title-text.project-description-item-block__title"));
        app.getGroupHelper().useTestCase(new UseTestCaseClass("TRANSFORMATION", "div.project-description-item-block__transformation-block > div.title-text.project-description-item-block__title"));
        app.getGroupHelper().useTestCase(new UseTestCaseClass("HIGHLIGHT", "div.project-description-item-block__highlights-block > div.title-text.project-description-item-block__title"));

    }

    @Test
    public void testValidGroupCanBeCreatedAgain() throws Exception {

        app.getGroupHelper().goToStart();

        app.getGroupHelper().useTestCase(new UseTestCaseClass("RAW\n INGREDIENTS", "div.project-description-item-block > div.title-text.project-description-item-block__title"));
        app.getGroupHelper().useTestCase(new UseTestCaseClass("TRANSFORMATION", "div.project-description-item-block__transformation-block > div.title-text.project-description-item-block__title"));
        app.getGroupHelper().useTestCase(new UseTestCaseClass("HIGHLIGHTS", "div.project-description-item-block__highlights-block > div.title-text.project-description-item-block__title"));

    }

}


