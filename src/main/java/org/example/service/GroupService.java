package org.example.service;

import org.example.domain.Group;
import org.example.exceptions.UnityvilleException;
import org.example.user.AdminUser;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class GroupService {
    private Set<Group> groups = new HashSet<>();

    public Set<Group> getAllGroups() {
        return Collections.unmodifiableSet(groups);
    }

    public Group findByName(String name) throws UnityvilleException {
        for (Group group : groups) {
            if (group.getName().equals(name)) {
                return group;
            }
        }

        throw new UnityvilleException("Group with name " + name + " does not exists");
    }

    public Group createGroup(String nameGroup, AdminUser admin) throws UnityvilleException {
        for (Group group : groups) {
            if (group.getName().equals(nameGroup)) {
                throw new UnityvilleException("Group with name " + nameGroup + " already exists");
            }
        }

        Group group = new Group(nameGroup, admin);
        groups.add(group);

        return group;
    }
}
