package com.example.tests;

import org.testng.annotations.Test;

public class CheckEmailTest extends TestBase {

    @Test
    public void testValidButtonTransaction() throws Exception {

        app.getGroupHelper().goToStart();

        app.getGroupHelper().tapOnButton();

    }
}


