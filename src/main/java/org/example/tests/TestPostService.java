package org.example.tests;

import org.example.domain.CommunityOfPractice;
import org.example.domain.Group;
import org.example.factory.UserFactory;
import org.example.post.CoPPost;
import org.example.post.GroupPost;
import org.example.post.Post;
import org.example.service.PostService;
import org.example.user.AdminUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TestPostService {
    private PostService postService;
    private AdminUser adminUser;
    private Group group;
    private CommunityOfPractice cop;

    @BeforeEach
    void setUp() {
        postService = new PostService();
        adminUser = (AdminUser) UserFactory.createNewUser("MANAGER", "Tom Lee");
        group = new Group("Group1", adminUser);
        cop = new CommunityOfPractice("CoP1", adminUser);
    }

    @Test
    void testCreateGroupPost() {
        Post post = postService.createPost(adminUser, "Group Post", "This is a group post", group);
        assertTrue(post instanceof GroupPost);
        assertEquals("Group Post", post.getTitle());
        assertEquals("This is a group post", post.getContent());
        assertEquals(adminUser, post.getAuthor());
        assertEquals(group, ((GroupPost) post).getGroup());
    }

    @Test
    void testCreateCoPPost() {
        Post post = postService.createPost(adminUser, "CoP Post", "This is a CoP post", cop);
        assertTrue(post instanceof CoPPost);
        assertEquals("CoP Post", post.getTitle());
        assertEquals("This is a CoP post", post.getContent());
        assertEquals(adminUser, post.getAuthor());
        assertEquals(cop, ((CoPPost) post).getCommunityOfPractice());
    }

    @Test
    void testCreatePostInvalidGroupOrCoP() {
        assertThrows(IllegalArgumentException.class,
                () -> postService.createPost(adminUser, "Invalid Post", "This is an invalid post", new Object()));
    }

    @Test
    void testGetAllPosts() {
        postService.createPost(adminUser, "Group Post", "This is a group post", group);
        postService.createPost(adminUser, "CoP Post", "This is a CoP post", cop);
        List<Post> posts = postService.getAllPosts();
        assertEquals(2, posts.size());
    }

    @Test
    void testGetPostsByGroup() {
        postService.createPost(adminUser, "Group Post", "This is a group post", group);
        List<Post> posts = postService.getPostsByGroup(group);
        assertEquals(1, posts.size());
        assertTrue(posts.get(0) instanceof GroupPost);
    }

    @Test
    void testGetPostsByCoP() {
        postService.createPost(adminUser, "CoP Post", "This is a CoP post", cop);
        List<Post> posts = postService.getPostsByCoP(cop);
        assertEquals(1, posts.size());
        assertTrue(posts.get(0) instanceof CoPPost);
    }

    @Test
    void testGetPinnedPostsFromCoP() {
        CoPPost copPost = new CoPPost("Pinned CoP Post", "This is a pinned CoP post", adminUser, cop, "", "", "");
        copPost.setPinned(true);
        postService.createPost(adminUser, "CoP Post", "This is a CoP post", cop);
        postService.getAllPosts().add(copPost);
        List<Post> posts = postService.getPinnedPostsFromCoP(cop);
        assertEquals(1, posts.size());
        assertTrue(posts.get(0).isPinned());
    }

    @Test
    void testFindPostByTitle() {
        postService.createPost(adminUser, "Unique Title", "This post has a unique title", group);
        Post post = postService.findPostByTitle("Unique Title");
        assertNotNull(post);
        assertEquals("Unique Title", post.getTitle());
    }
}
