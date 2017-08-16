package com.example.tests;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by DISTILLERY on 10.08.17.
 */
public class Sample {
    public static void main(String[] args) {

/*        Random rnd = new Random();
        String x = "" + rnd.nextInt(1);

        if ("0".equals(x)) {
            System.out.println("Text");
        }*/

        String title = "Select (test test test)";
        Pattern p = Pattern.compile(".*\\((.*)\\)");
        Matcher m = p.matcher(title);
        if (m.matches()) {
            System.out.println(m.group(1));
        }

    }
}
