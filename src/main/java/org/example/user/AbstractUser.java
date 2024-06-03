package org.example.user;

import lombok.Getter;
import lombok.Setter;
import org.example.domain.CommunityOfPractice;
import org.example.domain.Employee;
import org.example.domain.Group;
import org.example.exceptions.UnityvilleException;
import org.example.service.CoPService;
import org.example.service.GroupService;

import java.util.Objects;

@Getter
@Setter
public abstract class AbstractUser extends Employee implements User {
    private String username;
    private String password;
    private String email;

    protected AbstractUser(String name, String title, String username, String password, String email) {
        super(name, title);
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public abstract void createPost(String topic, String content);

    @Override
    public void viewNews() {
        System.out.println(username + " is viewing news.");
    }

    @Override
    public void likeNews(int newsId) {
        System.out.println(username + " liked news with ID: " + newsId);
    }

    @Override
    public void commentOnNews(int newsId, String comment) {
        System.out.println(username + " commented on news with ID: " + newsId + " Comment: " + comment);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + email.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        AbstractUser other = (AbstractUser) obj;
        return Objects.equals(email, other.email);
    }

    public void enrollGroup(GroupService groupService, String groupName) throws UnityvilleException {
        Group group = groupService.findByName(groupName);

        if (group.isUserInGroup(this)) {
            throw new UnityvilleException("Member already enrolled in group " + groupName);
        }

        group.enrollUser(this);
    }

    public void followCoP(CoPService coPService, String copName) throws UnityvilleException {
        CommunityOfPractice cop = coPService.findByName(copName);

        if (cop.isUserInCoP(this)) {
            throw new UnityvilleException("Member already enrolled in community of practice " + copName);
        }

        cop.enrollUser(this);
    }
}
