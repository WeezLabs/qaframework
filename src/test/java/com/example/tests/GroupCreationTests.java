package com.example.tests;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;

import org.testng.Assert;
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
                .setName("321").setId("312");
        GroupObject validGroupTwo = new GroupObject()
                .setName("321").setId("");

        Set<GroupObject> oldList = app.getGroupHelper().getGroups();

//        app.getGroupHelper().openPortnowLink("wrongEmail");

        Set<GroupObject> newList = app.getGroupHelper().getGroups();
        newList.add(validGroupTwo);

        verifyGroupAdded(oldList, validGroup, newList);

    }

    private void verifyGroupAdded (Set<GroupObject> oldList, GroupObject validGroup, Set<GroupObject> newList) {
//        Assert.assertEquals(newList.size(), oldList.size() + 1);
        assertThat(newList.size(), equalTo(oldList.size()+1));
        oldList.add(validGroup);
        assertThat(newList, equalTo(oldList));
    }

}


