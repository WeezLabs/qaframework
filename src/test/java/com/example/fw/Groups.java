package com.example.fw;

import com.example.tests.GroupObject;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by DISTILLERY on 16.08.17.
 */
public class Groups {

    private Set<GroupObject> storedGroups = null;

    public Groups(Set<GroupObject> groups) {
        storedGroups = new HashSet<GroupObject>(groups);
    }

    @Override
    public int hashCode() {
        return storedGroups != null ? storedGroups.hashCode() : 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Groups)) return false;

        Groups groups = (Groups) o;

        return !(storedGroups != null ? !storedGroups.equals(groups.storedGroups) : groups.storedGroups != null);

    }


}
