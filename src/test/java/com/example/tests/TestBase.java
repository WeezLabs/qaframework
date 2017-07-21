package com.example.tests;

import com.example.fw.ApplicationManager;
import org.testng.annotations.*;


public class TestBase {

    public ApplicationManager app;

    @BeforeClass
    public void setUp() throws Exception {
        app = new ApplicationManager();
    }

    @AfterClass
    public void tearDown() throws Exception {
        app.stop();
    }

}

