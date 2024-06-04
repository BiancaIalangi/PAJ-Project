package org.example.tests;

import org.example.domain.CommunityOfPractice;
import org.example.exceptions.UnityvilleException;
import org.example.factory.UserFactory;
import org.example.post.CoPPost;
import org.example.post.Post;
import org.example.service.CoPService;
import org.example.service.PostService;
import org.example.user.AdminUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TestCoPService {
    private CoPService copService;
    private PostService postService;
    private AdminUser adminUser;

    @BeforeEach
    void setUp() {
        copService = new CoPService();
        postService = new PostService();
        adminUser = (AdminUser) UserFactory.createNewUser("MANAGER", "admin@example.com");
    }

    @Test
    void testCreateCoP() {
        CommunityOfPractice cop = copService.createCoP("CoP1", adminUser);
        assertNotNull(cop);
        assertEquals("CoP1", cop.getName());
        assertEquals(adminUser, cop.getCreator());
    }


    @Test
    void testGetPinnedPostsFromCoP() throws UnityvilleException {
        CommunityOfPractice cop = copService.createCoP("CoP1", adminUser);
        CoPPost pinnedPost = new CoPPost("Pinned Post", "This is a pinned post", adminUser, cop, "", "", "");
        pinnedPost.setPinned(true);
        postService.getAllPosts().add(pinnedPost);

        List<Post> pinnedPosts = copService.getPinnedPostsFromCoP("CoP1", postService);
        assertEquals(1, pinnedPosts.size());
        assertEquals("Pinned Post", pinnedPosts.get(0).getTitle());
    }

    @Test
    void testGetPinnedPostsFromCoPNoPinnedPosts() throws UnityvilleException {
        CommunityOfPractice cop = copService.createCoP("CoP1", adminUser);
        CoPPost post = new CoPPost("Post", "This is a post", adminUser, cop, "", "", "");
        postService.getAllPosts().add(post);

        List<Post> pinnedPosts = copService.getPinnedPostsFromCoP("CoP1", postService);
        assertTrue(pinnedPosts.isEmpty());
    }

    @Test
    void testGetPinnedPostsFromNonExistentCoP() {
        UnityvilleException exception = assertThrows(UnityvilleException.class, () -> {
            copService.getPinnedPostsFromCoP("NonExistentCoP", postService);
        });
        assertEquals("Community of practice with name NonExistentCoP does not exists.", exception.getMessage());
    }

    @Test
    void testFindByName() throws UnityvilleException {
        CommunityOfPractice cop = copService.createCoP("CoP1", adminUser);
        CommunityOfPractice foundCoP = copService.findByName("CoP1");
        assertNotNull(foundCoP);
        assertEquals("CoP1", foundCoP.getName());

        UnityvilleException exception = assertThrows(UnityvilleException.class, () -> {
            copService.findByName("NonExistentCoP");
        });
        assertEquals("Community of Practice with name NonExistentCoP does not exists", exception.getMessage());
    }


}
