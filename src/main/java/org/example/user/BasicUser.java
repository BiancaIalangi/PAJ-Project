package org.example.user;

import org.example.domain.CommunityOfPractice;
import org.example.domain.Group;
import org.example.exceptions.UnityvilleException;
import org.example.service.CoPService;
import org.example.service.GroupService;

public class BasicUser extends AbstractUser {
    public BasicUser(String name, String title, String username, String password, String email) {
        super(name, title, username, password, email);
    }

    @Override
    public boolean exitGroup(GroupService groupService, String groupName) throws UnityvilleException {
        Group group = groupService.findByName(groupName);

        return group.exitUser(this);
    }

    @Override
    public boolean unfollowCoP(CoPService coPService, String copName) throws UnityvilleException {
        CommunityOfPractice cop = coPService.findByName(copName);

        return cop.removeUser(this);
    }
}
