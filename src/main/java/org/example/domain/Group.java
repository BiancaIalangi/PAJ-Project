package org.example.domain;

import lombok.Getter;
import lombok.Setter;
import org.example.user.AdminUser;
import org.example.user.User;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Group {
    private String name;
    private AdminUser admin;
    private List<User> members;

    public Group(String name, AdminUser admin) {
        this.name = name;
        this.admin = admin;
        this.members = new ArrayList<>();
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
}
