package com.example.tests;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;

import org.testng.annotations.*;

import java.util.*;

public class GroupCreationTests extends TestBase {

    @Test
    public void testValidGroupCanBeCreated() throws Exception {

//        app.getGroupHelper().goToStart();

        app.getGroupHelper().useTestCase(new UseTestCaseClass("RAW\n INGREDIENTS", "div.project-description-item-block > div.title-text.project-description-item-block__title"));
        app.getGroupHelper().useTestCase(new UseTestCaseClass("TRANSFORMATION", "div.project-description-item-block__transformation-block > div.title-text.project-description-item-block__title"));
        app.getGroupHelper().useTestCase(new UseTestCaseClass("HIGHLIGHTS", "div.project-description-item-block__highlights-block > div.title-text.project-description-item-block__title"));

    }


/*
    @Test(dataProvider = "randomGroups", dataProviderClass = GroupDataGenerator.class)

    public void testGroupObject(GroupObject validGroup) throws Exception {

        GroupObject validGroupTwo = new GroupObject().setId("321");

        Set<GroupObject> oldList = app.getGroupHelper().getGroups();

//        app.getGroupHelper().openPortnowLink("wrongEmail");

        Set<GroupObject> newList = app.getGroupHelper().getGroups();
        newList.add(validGroupTwo);
//        assertThat(newList, equalTo(oldList.withAdded(validGroup)));
        verifyGroupAdded(oldList, validGroup, newList);

    }

    private void verifyGroupAdded (Set<GroupObject> oldList, GroupObject validGroup, Set<GroupObject> newList) {
//        Assert.assertEquals(newList.size(), oldList.size() + 1);
        assertThat(newList.size(), equalTo(oldList.size()+1));
        oldList.add(validGroup);
        assertThat(newList, equalTo(oldList));
    }
*/
}


