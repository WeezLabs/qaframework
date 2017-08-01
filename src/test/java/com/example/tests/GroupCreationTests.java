package com.example.tests;

import org.testng.annotations.*;

import java.util.Set;

public class GroupCreationTests extends TestBase {

    @Test
    public void testValidGroupCanBeCreated() throws Exception {

//        app.getGroupHelper().goToStart();

        app.getGroupHelper().useTestCase(new UseTestCaseClass("RAW\n INGREDIENTS", "div.project-description-item-block > div.title-text.project-description-item-block__title"));
        app.getGroupHelper().useTestCase(new UseTestCaseClass("TRANSFORMATION", "div.project-description-item-block__transformation-block > div.title-text.project-description-item-block__title"));
        app.getGroupHelper().useTestCase(new UseTestCaseClass("HIGHLIGHTS", "div.project-description-item-block__highlights-block > div.title-text.project-description-item-block__title"));

    }

    @Test
    public void testThirdLesson() throws Exception {


        UseTestCaseClass newUseTestCaseClass = new UseTestCaseClass("RAW\n INGREDIENTS", "div.project-description-item-block > div.title-text.project-description-item-block__title");

        Set<UseTestCaseClass> oldList = app.getGroupHelper().getGroups();

        app.getGroupHelper().openPortnowLink("wrongEmail");

        Set<UseTestCaseClass> newList = app.getGroupHelper().getGroups();

        verifyGroupAdded(oldList, newUseTestCaseClass, newList);

    }

    private void verifyGroupAdded (Set<UseTestCaseClass> oldList, UseTestCaseClass newUseTestCaseClass, Set<UseTestCaseClass> newList) {

    }

}


