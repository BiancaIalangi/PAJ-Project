package org.example.domain;

import lombok.Getter;
import lombok.Setter;
import org.example.post.CoPPost;
import org.example.user.AdminUser;
import org.example.user.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
public class CommunityOfPractice {
    private String name;
    private AdminUser creator;
    private List<User> followers;
    private List<CoPPost> posts;

    public CommunityOfPractice(String name, AdminUser creator) {
        this.name = name;
        this.creator = creator;
        this.followers = new ArrayList<>();
        this.posts = new ArrayList<>();
    }

    public boolean isUserInCoP(User member) {
        return followers.stream().anyMatch(member::equals);
    }

    public void enrollUser(User user) {
        followers.add(user);
    }

    @Override
    public String toString() {
        return "CommunityOfPractice{" +
                "name='" + name + '\'' +
                ", creator=" + creator +
                ", followers=" + followers +
                '}';
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + name.hashCode();
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
        CommunityOfPractice other = (CommunityOfPractice) obj;
        return Objects.equals(name, other.name);
    }
}
