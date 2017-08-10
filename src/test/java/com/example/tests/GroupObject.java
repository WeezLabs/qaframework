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
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());//id.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;

        if (!(o instanceof GroupObject)) return false;

        GroupObject that = (GroupObject) o;

        if (name == null) {
            if (that.name != null)
                return false;
        } else if (!name.equals(that.name)) return false;

        if (id != null && that.id != null) {
            return id.equals(that.id);
        }

    return true;

    }



    @Override
    public String toString() {
        return "Group {" + name + id + '}';
    }
/*    public GroupObject(ApplicationManager manager) {
        super();
    }*/
}
