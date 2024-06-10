package tests;

import org.example.domain.CommunityOfPractice;
import org.example.domain.Group;
import org.example.exceptions.UnityvilleException;
import org.example.factory.UserFactory;
import org.example.post.CoPPost;
import org.example.post.GroupPost;
import org.example.post.Post;
import org.example.service.CoPService;
import org.example.service.GroupService;
import org.example.service.PostService;
import org.example.user.AdminUser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestAdminUser {
    private final AdminUser adminUser = (AdminUser) UserFactory.createNewUser(UserFactory.MANAGER, "First User");
    private static final String POST_TITLE = "Post1";
    private static final String COP_NAME = "CoP1";
    private static final String GROUP_NAME = "Group1";
    private static final String INITIAL_CONTENT = "Here is the content";
    private static final String ALTERED_CONTENT = "New content here";

    @Test
    void testCreateGroup() throws UnityvilleException {
        GroupService groupService = new GroupService();
        Group group = adminUser.createGroup(groupService, GROUP_NAME);

        assertEquals(GROUP_NAME, group.getName());
        assertTrue(group.getMembers().isEmpty());
    }

    @Test
    void testCreateCoP() {
        CoPService coPService = new CoPService();
        CommunityOfPractice communityOfPractice = adminUser.createCoP(coPService, COP_NAME);

        assertEquals(COP_NAME, communityOfPractice.getName());
        assertTrue(communityOfPractice.getFollowers().isEmpty());
    }

    @Test
    void testCreateGroupPost() throws UnityvilleException {
        GroupService groupService = new GroupService();
        Group group = adminUser.createGroup(groupService, GROUP_NAME);

        PostService postService = new PostService();
        Post post = adminUser.createGroupPost(postService, POST_TITLE, INITIAL_CONTENT, groupService, GROUP_NAME);
        assertTrue(post instanceof GroupPost);
        assertEquals(POST_TITLE, post.getTitle());
        assertEquals(INITIAL_CONTENT, post.getContent());
        assertEquals(adminUser, post.getAuthor());
        assertEquals(group, ((GroupPost) post).getGroup());
    }

    @Test
    void testCreateCoPPost() throws UnityvilleException {
        CoPService coPService = new CoPService();
        CommunityOfPractice communityOfPractice = adminUser.createCoP(coPService, COP_NAME);

        PostService postService = new PostService();
        Post post = adminUser.createCoPPost(postService, POST_TITLE, INITIAL_CONTENT, coPService, COP_NAME);
        assertTrue(post instanceof CoPPost);
        assertEquals(POST_TITLE, post.getTitle());
        assertEquals(INITIAL_CONTENT, post.getContent());
        assertEquals(adminUser, post.getAuthor());
        assertEquals(communityOfPractice, ((CoPPost) post).getCommunityOfPractice());
    }

    @Test
    void testPinPost() throws UnityvilleException {
        CoPService coPService = new CoPService();
        adminUser.createCoP(coPService, COP_NAME);

        PostService postService = new PostService();
        String postContent1 = INITIAL_CONTENT + " from post1";
        Post post1 = adminUser.createCoPPost(postService, POST_TITLE, postContent1, coPService, COP_NAME);
        String postContent2 = INITIAL_CONTENT + " from post2";
        String postTitle2 = "Post2";
        adminUser.createCoPPost(postService, postTitle2, postContent2, coPService, COP_NAME);

        assertTrue(coPService.getPinnedPostsFromCoP(COP_NAME, postService).isEmpty());
        adminUser.pinPost(postService, POST_TITLE);
        assertEquals(1, coPService.getPinnedPostsFromCoP(COP_NAME, postService).size());
        assertEquals(post1, coPService.getPinnedPostsFromCoP(COP_NAME, postService).get(0));
    }

    @Test
    void testEditCoPPostWithBestPractice() throws UnityvilleException {
        CoPService coPService = new CoPService();
        adminUser.createCoP(coPService, COP_NAME);

        PostService postService = new PostService();
        CoPPost post = (CoPPost) adminUser.createCoPPost(postService, POST_TITLE, INITIAL_CONTENT, coPService, COP_NAME);

        assertTrue(post.getBestPractices().isEmpty());
        String advice = "Advice1";
        adminUser.editCoPPost(postService, POST_TITLE, null, advice);
        assertEquals(advice, post.getBestPractices());
    }

    @Test
    void testEditCoPPostWithNewContentAndBestPractice() throws UnityvilleException {
        CoPService coPService = new CoPService();
        adminUser.createCoP(coPService, COP_NAME);

        PostService postService = new PostService();
        CoPPost post = (CoPPost) adminUser.createCoPPost(postService, POST_TITLE, INITIAL_CONTENT, coPService, COP_NAME);

        assertTrue(post.getBestPractices().isEmpty());
        assertEquals(INITIAL_CONTENT, post.getContent());
        String advice = "Advice1";
        adminUser.editCoPPost(postService, POST_TITLE, ALTERED_CONTENT, advice);
        assertEquals(advice, post.getBestPractices());
        assertEquals(ALTERED_CONTENT, post.getContent());
    }

    @Test
    void testFailEditCoPPost() throws UnityvilleException {
        GroupService groupService = new GroupService();
        adminUser.createGroup(groupService, GROUP_NAME);

        PostService postService = new PostService();
        GroupPost post = (GroupPost) adminUser.createGroupPost(postService, POST_TITLE, INITIAL_CONTENT, groupService, GROUP_NAME);

        adminUser.editCoPPost(postService, POST_TITLE, ALTERED_CONTENT, null);
        assertEquals(INITIAL_CONTENT, post.getContent());
    }

    @Test
    void testEditGroupPostNewContent() throws UnityvilleException {
        GroupService groupService = new GroupService();
        adminUser.createGroup(groupService, GROUP_NAME);

        PostService postService = new PostService();
        GroupPost post = (GroupPost) adminUser.createGroupPost(postService, POST_TITLE, INITIAL_CONTENT, groupService, GROUP_NAME);

        assertEquals(INITIAL_CONTENT, post.getContent());
        adminUser.editGroupPost(postService, POST_TITLE, ALTERED_CONTENT);
        assertEquals(ALTERED_CONTENT, post.getContent());
    }

    @Test
    void testEditGroupPostNoContent() throws UnityvilleException {
        GroupService groupService = new GroupService();
        adminUser.createGroup(groupService, GROUP_NAME);

        PostService postService = new PostService();
        GroupPost post = (GroupPost) adminUser.createGroupPost(postService, POST_TITLE, INITIAL_CONTENT, groupService, GROUP_NAME);

        assertEquals(INITIAL_CONTENT, post.getContent());
        adminUser.editGroupPost(postService, POST_TITLE, null);
        assertEquals(INITIAL_CONTENT, post.getContent());
    }

    @Test
    void testDeleteCoPPost() throws UnityvilleException {
        CoPService coPService = new CoPService();
        adminUser.createCoP(coPService, COP_NAME);

        PostService postService = new PostService();
        CoPPost post = (CoPPost) adminUser.createCoPPost(postService, POST_TITLE, INITIAL_CONTENT, coPService, COP_NAME);

        assertEquals(1, postService.getAllPosts().size());
        CoPPost deletePost = adminUser.deleteCoPPost(postService, POST_TITLE);
        assertTrue(postService.getAllPosts().isEmpty());
        assertEquals(post, deletePost);
    }

    @Test
    void testDeleteNoCoPPost() throws UnityvilleException {
        CoPService coPService = new CoPService();
        adminUser.createCoP(coPService, COP_NAME);

        PostService postService = new PostService();
        adminUser.createCoPPost(postService, POST_TITLE, INITIAL_CONTENT, coPService, COP_NAME);

        CoPPost deletePost = adminUser.deleteCoPPost(postService, POST_TITLE + "e");
        assertEquals(1, postService.getAllPosts().size());
        assertNull(deletePost);
    }

    @Test
    void testDeleteGroupPost() throws UnityvilleException {
        GroupService groupService = new GroupService();
        adminUser.createGroup(groupService, GROUP_NAME);

        PostService postService = new PostService();
        GroupPost post = (GroupPost) adminUser.createGroupPost(postService, POST_TITLE, INITIAL_CONTENT, groupService, GROUP_NAME);

        assertEquals(1, postService.getAllPosts().size());
        GroupPost deletePost = adminUser.deleteGroupPost(postService, POST_TITLE);
        assertTrue(postService.getAllPosts().isEmpty());
        assertEquals(post, deletePost);
    }

    @Test
    void testDeleteNoGroupPost() throws UnityvilleException {
        GroupService groupService = new GroupService();
        adminUser.createGroup(groupService, GROUP_NAME);

        PostService postService = new PostService();
        adminUser.createGroupPost(postService, POST_TITLE, INITIAL_CONTENT, groupService, GROUP_NAME);

        GroupPost deletePost = adminUser.deleteGroupPost(postService, POST_TITLE + "w");
        assertEquals(1, postService.getAllPosts().size());
        assertNull(deletePost);
    }

    @Test
    void testFailedExitGroup() throws UnityvilleException {
        GroupService groupService = new GroupService();
        adminUser.createGroup(groupService, GROUP_NAME);

        boolean response = adminUser.exitGroup(groupService, GROUP_NAME);
        assertFalse(response);
    }

    @Test
    void testExitGroup() throws UnityvilleException {
        AdminUser secondAdminUser = (AdminUser) UserFactory.createNewUser(UserFactory.MANAGER, "Second Admin");

        GroupService groupService = new GroupService();
        Group group = adminUser.createGroup(groupService, GROUP_NAME);

        assertTrue(group.getMembers().isEmpty());
        secondAdminUser.enrollGroup(groupService, GROUP_NAME);
        assertEquals(1, group.getMembers().size());

        boolean response = secondAdminUser.exitGroup(groupService, GROUP_NAME);
        assertTrue(group.getMembers().isEmpty());
        assertTrue(response);
    }

    @Test
    void testFailedUnfollowCoP() throws UnityvilleException {
        CoPService coPService = new CoPService();
        adminUser.createCoP(coPService, COP_NAME);

        boolean response = adminUser.unfollowCoP(coPService, COP_NAME);
        assertFalse(response);
    }

    @Test
    void testUnfollowCoP() throws UnityvilleException {
        AdminUser secondAdminUser = (AdminUser) UserFactory.createNewUser(UserFactory.MANAGER, "Second Admin");
        CoPService coPService = new CoPService();
        CommunityOfPractice communityOfPractice = adminUser.createCoP(coPService, COP_NAME);

        assertTrue(communityOfPractice.getFollowers().isEmpty());
        secondAdminUser.followCoP(coPService, COP_NAME);
        assertEquals(1, communityOfPractice.getFollowers().size());

        boolean response = secondAdminUser.unfollowCoP(coPService, COP_NAME);
        assertTrue(communityOfPractice.getFollowers().isEmpty());
        assertTrue(response);
    }
}
