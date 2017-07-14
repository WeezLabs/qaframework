package com.example.tests;

import com.example.fw.ApplicationManager;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;


public class TestBase {

    protected ApplicationManager app;

    @BeforeClass
    public void setUp() throws Exception {
        app = new ApplicationManager();
    }

    @AfterClass
    public void tearDown() throws Exception {
        app.stop();
    }
}

