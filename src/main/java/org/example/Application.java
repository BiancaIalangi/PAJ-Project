package org.example;

import org.example.domain.*;
import org.example.post.CoPPost;
import org.example.post.GroupPost;
import org.example.post.Post;
import org.example.user.AdminUser;
import org.example.user.BasicUser;
import org.example.user.User;
import org.example.service.CoPService;

import java.util.ArrayList;
import java.util.List;

public class Application {

    public static void main(String[] args) {
//        CoPService copService = new CoPService();
//
//        // Create some users
//        AdminUser admin = new AdminUser("admin1", "password", "admin1@example.com");
//        BasicUser user1 = new BasicUser("user1", "password", "user1@example.com");
//        BasicUser user2 = new BasicUser("user2", "password", "user2@example.com");
//
//        // Create a CoP
//        List<User> users = new ArrayList<>();
//        users.add(admin);
//        users.add(user1);
//        users.add(user2);
//
//        List<Post> posts = new ArrayList<>();
//        CommunityOfPractice cop = new CommunityOfPractice("CoP1", admin, users);
//        copService.createCoP(cop);

//        // Create posts and add to CoP
//        Post post1 = new CoPPost("Post1", "Content1", cop, admin, false);
//        Post post2 = new GroupPost("Post2", "Content2", cop, user1, true);
//        Post post3 = new Post("Post3", "Content3", cop, user2, true);
//
//        posts.add(post1);
//        posts.add(post2);
//        posts.add(post3);
//
//        // Get all CoPs
//        List<CommunityOfPractice> allCoPs = copService.getAllCoPs();
//        System.out.println("All CoPs: " + allCoPs);
//
//        // Get pinned posts from a specific CoP
//        List<Post> pinnedPosts = copService.getPinnedPostsFromCoP(cop, posts);
//        System.out.println("Pinned posts in CoP1: " + pinnedPosts);
    }
}
