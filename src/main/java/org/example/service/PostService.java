package org.example.service;

import org.example.domain.CommunityOfPractice;
import org.example.domain.Group;
import org.example.post.CoPPost;
import org.example.post.GroupPost;
import org.example.post.Post;
import org.example.user.AdminUser;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class PostService {
    private final Set<Post> posts = new HashSet<>();
    private final ExecutorService executorService = Executors.newFixedThreadPool(7);


    public Post createPost(AdminUser user, String title, String content, Object groupOrCoP) {
        if (groupOrCoP instanceof Group group) {
            return createGroupPost(user, title, content, group);
        } else if (groupOrCoP instanceof CommunityOfPractice cop) {
            return createCoPPost(user, title, content, cop);
        }
        throw new IllegalArgumentException("Invalid group or CoP");
    }

    private GroupPost createGroupPost(AdminUser user, String title, String content, Group group) {
        GroupPost post = new GroupPost(title, content, user, group);
        Future<?> future = executorService.submit(() -> {
            posts.add(post);
            System.out.println("Post titled '" + post.getTitle() + "' has been added to the set.");
        });
        try {
            future.get(); // wait for the task to complete
            return post;
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    private CoPPost createCoPPost(AdminUser user, String title, String content, CommunityOfPractice cop) {
        CoPPost post = new CoPPost(title, content, user, cop, "", "", "");
        Future<?> future = executorService.submit(() -> {
            posts.add(post);
            System.out.println("Post titled '" + post.getTitle() + "' has been added to the set.");
        });
        try {
            future.get(); // wait for the task to complete
            return post;
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public Set<Post> getAllPosts() {
        return Collections.unmodifiableSet(posts);
    }

    public boolean removePost(Post post) {
        return posts.remove(post);
    }

    public List<Post> getPostsByGroup(Group group) {
        return posts.stream()
                .filter(post -> post instanceof GroupPost groupPost
                        && groupPost.getGroup().equals(group))
                .toList();
    }

    public List<Post> getPostsByCoP(CommunityOfPractice cop) {
        return posts.stream()
                .filter(post -> post instanceof CoPPost coPPost
                        && coPPost.getCommunityOfPractice().equals(cop))
                .toList();
    }

    public List<Post> getPinnedPostsFromCoP(CommunityOfPractice cop) {
        return posts.stream()
                .filter(post -> post instanceof CoPPost coPPost
                        && coPPost.getCommunityOfPractice().equals(cop)
                        && post.isPinned())
                .toList();
    }

    public Post findPostByTitle(String title) {
        return getAllPosts()
                .stream()
                .filter(post -> post.getTitle().equals(title))
                .findFirst().orElse(null);
    }
}
