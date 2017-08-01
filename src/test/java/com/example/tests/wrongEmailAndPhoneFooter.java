package com.example.tests;

import org.testng.annotations.Test;


public class wrongEmailAndPhoneFooter extends TestBase{

        @Test
        public void testWrongEmailPhone() throws Exception {

            app.getGroupHelper().fillAllFieldsWrongEmail("asfda");

        }

}
