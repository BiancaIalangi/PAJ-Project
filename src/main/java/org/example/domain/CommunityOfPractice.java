package org.example.domain;

import org.example.post.CoPPost;
import org.example.user.AdminUser;
import org.example.user.User;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class CommunityOfPractice {
    private String name;
    private final AdminUser creator;
    private final Set<User> followers;
    private final Set<CoPPost> posts;

    public CommunityOfPractice(String name, AdminUser creator) {
        this.name = name;
        this.creator = creator;
        this.followers = new HashSet<>();
        this.posts = new HashSet<>();
    }

    public boolean isUserInCoP(User member) {
        return followers.stream().anyMatch(member::equals);
    }

    public void enrollUser(User user) {
        followers.add(user);
    }

    public boolean removeUser(User user) {
        return followers.remove(user);
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AdminUser getCreator() {
        return creator;
    }

    public Set<User> getFollowers() {
        return Collections.unmodifiableSet(followers);
    }

    public Set<CoPPost> getPosts() {
        return Collections.unmodifiableSet(posts);
    }
}
