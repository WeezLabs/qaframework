package com.example.tests;

import com.example.fw.ApplicationManager;
import org.testng.annotations.*;


public class TestBase {

    public ApplicationManager app;

    @BeforeClass
    public void setUp() throws Exception {
        app = ApplicationManager.getInstance();
//        app = new ApplicationManager();
    }

    @AfterTest
    public void tearDown() throws Exception {
        ApplicationManager.getInstance().stop();
//        app.stop();
    }

}

