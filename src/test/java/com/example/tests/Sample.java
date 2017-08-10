package com.example.tests;

import java.util.Random;

/**
 * Created by DISTILLERY on 10.08.17.
 */
public class Sample {
    public static void main(String[] args) {

        Random rnd = new Random();
        String x = "" + rnd.nextInt(1);

        if ("0" == x) {
            System.out.println("Text");
        }
    }
}
