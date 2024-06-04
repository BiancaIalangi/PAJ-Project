package org.example.domain;

import org.example.post.GroupPost;
import org.example.user.AdminUser;
import org.example.user.User;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Group {
    private String name;
    private final AdminUser admin;
    private final Set<User> members;
    private final Set<GroupPost> posts;

    public Group(String name, AdminUser admin) {
        this.name = name;
        this.admin = admin;
        this.members = new HashSet<>();
        this.posts = new HashSet<>();
    }

    @Override
    public String toString() {
        return "Group{" +
                "name='" + name + '\'' +
                ", admin=" + admin +
                ", members=" + members +
                '}';
    }

    public boolean isUserInGroup(User member) {
        return members.stream().anyMatch(member::equals);
    }

    public void enrollUser(User user) {
        members.add(user);
    }

    public boolean exitUser(User user) {
        return members.remove(user);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AdminUser getAdmin() {
        return admin;
    }


    public Set<User> getMembers() {
        return Collections.unmodifiableSet(members);
    }


    public Set<GroupPost> getPosts() {
        return Collections.unmodifiableSet(posts);
    }

}
