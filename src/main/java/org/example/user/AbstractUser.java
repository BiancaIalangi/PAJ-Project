package org.example.user;

import lombok.Getter;
import lombok.Setter;
import org.example.domain.CommunityOfPractice;
import org.example.domain.Employee;
import org.example.domain.Group;
import org.example.exceptions.UnityvilleException;
import org.example.post.Post;
import org.example.service.CoPService;
import org.example.service.GroupService;
import org.example.service.PostService;

import java.util.*;

@Getter
@Setter
public abstract class AbstractUser extends Employee implements User {
    private String username;
    private String password;
    private String email;
    private List<Post> likes;
    private Map<Post, List<String>> comments;

    protected AbstractUser(String name, String title, String username, String password, String email) {
        super(name, title);
        this.username = username;
        this.password = password;
        this.email = email;
        this.likes = new ArrayList<>();
        this.comments = new HashMap<>();
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

    @Override
    public void enrollGroup(GroupService groupService, String groupName) throws UnityvilleException {
        Group group = groupService.findByName(groupName);

        if (group.isUserInGroup(this)) {
            throw new UnityvilleException("Member already enrolled in group " + groupName);
        }

        group.enrollUser(this);
    }

    public abstract boolean exitGroup(GroupService groupService, String groupName) throws UnityvilleException;

    @Override
    public void followCoP(CoPService coPService, String copName) throws UnityvilleException {
        CommunityOfPractice cop = coPService.findByName(copName);

        if (cop.isUserInCoP(this)) {
            throw new UnityvilleException("Member already enrolled in community of practice " + copName);
        }

        cop.enrollUser(this);
    }

    public abstract boolean unfollowCoP(CoPService coPService, String copName) throws UnityvilleException;

    @Override
    public void likePost(PostService postService, String title) throws UnityvilleException {
        Post post = postService.findPostByTitle(title);
        if (post == null) {
            throw new UnityvilleException("Post with title " + title + " does not exists");
        }
        post.receiveLike();

        likes.add(post);
    }

    @Override
    public void unlikePost(PostService postService, String title) throws UnityvilleException {
        Post post = postService.findPostByTitle(title);
        if (post == null) {
            throw new UnityvilleException("Post with title " + title + " does not exists");
        }

        post.receiveUnlike();
        likes.remove(post);
    }

    @Override
    public void commentPost(PostService postService, String title, String comment) throws UnityvilleException {
        Post post = postService.findPostByTitle(title);
        if (post == null) {
            throw new UnityvilleException("Post with title " + title + " does not exists");
        }

        post.addComment(comment);

        comments.computeIfAbsent(post, k -> new ArrayList<>()).add(comment);
    }
}
