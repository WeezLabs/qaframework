package com.example.tests;

import org.testng.annotations.DataProvider;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * Created by DISTILLERY on 16.08.17.
 */
public class GroupDataGenerator {

    static Random rnd = new Random();

    @DataProvider(name = "randomGroups")
    public static Iterator<Object[]> generateRandomGroups() {
        List<Object[]> list = new ArrayList<Object[]>();
        for (int i = 0; i < 5; i++) {
            GroupObject group = new GroupObject()
                    .setName("name" + rnd.nextInt())
                    .setHeader("header" + rnd.nextInt())
                    .setId("id" + rnd.nextInt());

            Object arr[] = {group};
            list.add(arr);
        }
        return list.iterator();
    }


    @DataProvider(name = "groupsFromFile")
    public static Iterator<Object[]> loadGroupsFromFile() {
        List<Object[]> list = new ArrayList<Object[]>();
        return list.iterator();
    }

    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Specify two paramentrs");
            return;
        }
        String file = args[0];
        int count = Integer.parseInt(args[1]);
        new GroupDataGenerator().generateDataToFile(file, count);
    }

    private void generateDataToFile(String file, int count) {

    }

}
