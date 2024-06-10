package tests;

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
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
        Set<Post> posts = postService.getAllPosts();
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
        Post post = postService.createPost(adminUser, "CoP Post", "This is a CoP post", cop);
        post.setPinned(true);

        List<Post> posts = postService.getPinnedPostsFromCoP(cop);
        assertEquals(1, posts.size());
        assertTrue(posts.get(0).isPinned());
    }

    @Test
    void testFindPostByTitle() throws InterruptedException {
        final Object lock = new Object();
        Post post = postService.createPost(adminUser, "Unique Title", "This post has a unique title", group);
        synchronized (lock) {
            while (post == null) {
                lock.wait();
            }
        }
        Post postByTitle = postService.findPostByTitle("Unique Title");
        assertNotNull(postByTitle);
        assertEquals("Unique Title", postByTitle.getTitle());
    }

    @Test
    void testConcurrentPostCreation() throws InterruptedException {
        int numberOfThreads = 7;
        ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);
        CountDownLatch latch = new CountDownLatch(numberOfThreads);

        for (int i = 0; i < numberOfThreads; i++) {
            int index = i;
            executorService.submit(() -> {
                postService.createPost(adminUser, "Post " + index, "Content " + index, cop);
                latch.countDown();
            });
        }

        latch.await();
        executorService.shutdown();

        assertEquals(numberOfThreads, postService.getAllPosts().size());
    }
}
