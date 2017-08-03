package com.example.tests;

import org.testng.annotations.*;

import java.util.Set;

public class GroupCreationTests extends TestBase {

/*    @Test
    public void testValidGroupCanBeCreated() throws Exception {

//        app.getGroupHelper().goToStart();

        app.getGroupHelper().useTestCase(new UseTestCaseClass("RAW\n INGREDIENTS", "div.project-description-item-block > div.title-text.project-description-item-block__title"));
        app.getGroupHelper().useTestCase(new UseTestCaseClass("TRANSFORMATION", "div.project-description-item-block__transformation-block > div.title-text.project-description-item-block__title"));
        app.getGroupHelper().useTestCase(new UseTestCaseClass("HIGHLIGHTS", "div.project-description-item-block__highlights-block > div.title-text.project-description-item-block__title"));

    }*/

    @Test
    public void testGroupObject() throws Exception {


        GroupObject validGroup = new GroupObject()
                .setName("321").setHeader("312").setFooter("123");

        Set<GroupObject> oldList = app.getGroupHelper().getGroups();

        app.getGroupHelper().openPortnowLink("wrongEmail");

        Set<GroupObject> newList = app.getGroupHelper().getGroups();

        verifyGroupAdded(oldList, validGroup, newList);

    }

    private void verifyGroupAdded (Set<GroupObject> oldList, GroupObject validGroup, Set<GroupObject> newList) {

    }

}


