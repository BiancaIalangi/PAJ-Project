package org.example;

import org.example.domain.CommunityOfPractice;
import org.example.exceptions.UnityvilleException;
import org.example.factory.UserFactory;
import org.example.service.CoPService;
import org.example.user.AdminUser;
import org.example.user.BasicUser;

public class Application {

    public static void main(String[] args) throws UnityvilleException {
        CoPService copService = new CoPService();
//
        // Create some users
        AdminUser adminUser = (AdminUser) UserFactory.createNewUser("manager", "First User");
        BasicUser user1 = (BasicUser) UserFactory.createNewUser("account", "Second User");
        BasicUser user2 = (BasicUser) UserFactory.createNewUser("programmer", "Third User");

        CommunityOfPractice cop1 = adminUser.createCoP(copService, "CoPOne");
        user1.followCoP(copService, "CoPOne");
        user2.followCoP(copService, "CoPOne");
        System.out.println(cop1);


//        user1.unfollowCoP(copService, "CoPOne");
//        System.out.println(cop1);
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
