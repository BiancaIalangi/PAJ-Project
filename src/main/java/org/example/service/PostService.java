package org.example.service;

import org.example.domain.CommunityOfPractice;
import org.example.domain.Group;
import org.example.post.CoPPost;
import org.example.post.GroupPost;
import org.example.post.Post;
import org.example.user.AbstractUser;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PostService {
    private final List<Post> posts = new ArrayList<>();

    public void createPost(AbstractUser user, String title, String content, Object groupOrCoP) {
        if (groupOrCoP instanceof Group group) {
            createGroupPost(user, title, content, group);
        } else if (groupOrCoP instanceof CommunityOfPractice cop) {
            createCoPPost(user, title, content, cop);
        } else {
            throw new IllegalArgumentException("Invalid group or CoP");
        }
    }

    private GroupPost createGroupPost(AbstractUser user, String title, String content, Group group) {
        GroupPost post = new GroupPost(title, content, user, group);
        posts.add(post);
        System.out.println(user.getUsername() + " created a post in group: " + group.getName());
        return post;
    }

    private CoPPost createCoPPost(AbstractUser user, String title, String content, CommunityOfPractice cop) {
        CoPPost post = new CoPPost(title, content, user, cop, "", "", "");
        posts.add(post);
        System.out.println(user.getUsername() + " created a post in CoP: " + cop.getName());
        return post;
    }

    public List<Post> getAllPosts() {
        return posts;
    }

    public List<Post> getPostsByGroup(Group group) {
        return posts.stream()
                .filter(post -> post instanceof GroupPost
                        && ((GroupPost) post).getGroup().equals(group))
                .collect(Collectors.toList());
    }

    public List<Post> getPostsByCoP(CommunityOfPractice cop) {
        return posts.stream()
                .filter(post -> post instanceof CoPPost
                        && ((CoPPost) post).getCommunityOfPractice().equals(cop))
                .collect(Collectors.toList());
    }

    public List<Post> getPinnedPostsFromCoP(CommunityOfPractice cop) {
        return posts.stream()
                .filter(post -> post instanceof CoPPost
                        && ((CoPPost) post).getCommunityOfPractice().equals(cop)
                        && post.isPinned())
                .collect(Collectors.toList());
    }
}
