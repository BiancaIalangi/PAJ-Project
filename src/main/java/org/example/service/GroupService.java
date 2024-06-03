package org.example.service;

import org.example.domain.Group;
import org.example.exceptions.UnityvilleException;
import org.example.user.AdminUser;

import java.util.ArrayList;
import java.util.List;

public class GroupService {
    private List<Group> groups = new ArrayList<>();

    public Group findByName(String name) throws UnityvilleException {
        for (Group group : groups) {
            if (group.getName().equals(name)) {
                return group;
            }
        }

        throw new UnityvilleException("Group with name + " + name + "does not exists");
    }

    public void createGroup(String nameGroup, AdminUser admin) throws UnityvilleException {
        for (Group group : groups) {
            if (group.getName().equals(nameGroup)) {
                throw new UnityvilleException("Group with name + " + nameGroup + "already exists");
            }
        }

        groups.add(new Group(nameGroup, admin));
    }
}
