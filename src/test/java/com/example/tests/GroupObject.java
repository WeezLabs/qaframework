package com.example.tests;

import com.example.fw.ApplicationManager;

public class GroupObject extends TestBase {

    public String name;
    public String header;
    public String footer;
    public String id;


    public GroupObject setName(String name) {
        this.name = name;
        return this;
    }

    public GroupObject setHeader(String header) {
        this.header = header;
        return this;
    }

    public GroupObject setFooter(String footer) {
        this.footer = footer;
        return this;
    }

    public GroupObject setId(String id) {
        this.id = id;
        return this;
    }

    @Override
    public String toString() {
        return "Group {" + name + id + '}';
    }

/*    public GroupObject(ApplicationManager manager) {
        super();
    }*/
}
