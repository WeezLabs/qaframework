package com.example.tests;

import org.testng.annotations.DataProvider;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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

        List<GroupObject> groups = new GroupDataGenerator().generateRandomGroupList(5);

        List<Object[]> list = new ArrayList<Object[]>();
        for (GroupObject group : groups) {

            Object arr[] = {group};
            list.add(arr);

            //list.add(new Object[](group));
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
        try {
            new GroupDataGenerator().generateDataToFile(file, count);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void generateDataToFile(String fileName, int count) throws IOException {

        List<GroupObject> groups = generateRandomGroupList(count);
        File file = new File(fileName);
        if (file.exists()) {
            System.out.println("File exists");
            return;
        }

        FileWriter writer = new FileWriter(file);
        for (GroupObject group : groups) {
            writer.write("" + group + "\n");
            writer.flush();
        }
        writer.close();

    }


    private List<GroupObject> generateRandomGroupList(int count) {
        List<GroupObject> list = new ArrayList<GroupObject>();
        for (int i = 0; i < count; i++) {
            GroupObject group = new GroupObject()
                    .setName("name" + rnd.nextInt())
                    .setHeader("header" + rnd.nextInt())
                    .setId("id" + rnd.nextInt());
            list.add(group);
            //Object arr[] = {group};
            //list.add(arr);
        }
        return list;
    }

}
