package tests;

import org.example.domain.CommunityOfPractice;
import org.example.domain.Group;
import org.example.exceptions.UnityvilleException;
import org.example.factory.UserFactory;
import org.example.post.Post;
import org.example.service.CoPService;
import org.example.service.GroupService;
import org.example.service.PostService;
import org.example.user.AdminUser;
import org.example.user.BasicUser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestBasicUser {
    private static final String GROUP_NAME = "Group1";
    private final AdminUser adminUser = (AdminUser) UserFactory.createNewUser(UserFactory.MANAGER, "First User");
    private final BasicUser user1 = (BasicUser) UserFactory.createNewUser("programmer", "Second User");

    @Test
    void testEnrollGroup() throws UnityvilleException {
        BasicUser user2 = (BasicUser) UserFactory.createNewUser("hr", "Third User");

        GroupService groupService = new GroupService();
        Group group = groupService.createGroup(GROUP_NAME, adminUser);

        user1.enrollGroup(groupService, GROUP_NAME);
        user2.enrollGroup(groupService, GROUP_NAME);

        assertEquals(2, group.getMembers().size());
        assertTrue(group.getMembers().contains(user1));
        assertTrue(group.getMembers().contains(user2));
    }

    @Test
    void testExceptionEnrollGroup() throws UnityvilleException {
        String groupName = GROUP_NAME;

        GroupService groupService = new GroupService();
        groupService.createGroup(groupName, adminUser);

        user1.enrollGroup(groupService, groupName);
        UnityvilleException exception = assertThrows(
                UnityvilleException.class,
                () -> user1.enrollGroup(groupService, groupName)
        );

        assertEquals("Member already enrolled in group " + groupName, exception.getMessage());
    }

    @Test
    void testFollowCoP() throws UnityvilleException {
        String coPName1 = "CoP1";
        String coPName2 = "CoP2";
        BasicUser user2 = (BasicUser) UserFactory.createNewUser("hr", "Third User");
        AdminUser adminUser1 = (AdminUser) UserFactory.createNewUser("manager", "Fourth User");

        CoPService coPService = new CoPService();
        CommunityOfPractice cop1 = coPService.createCoP(coPName1, adminUser);
        CommunityOfPractice cop2 = coPService.createCoP(coPName2, adminUser);

        user1.followCoP(coPService, coPName1);
        user2.followCoP(coPService, coPName1);
        adminUser1.followCoP(coPService, coPName1);

        assertEquals(3, cop1.getFollowers().size());
        assertTrue(cop2.getFollowers().isEmpty());

        assertTrue(cop1.getFollowers().contains(user1));
        assertTrue(cop1.getFollowers().contains(user2));
        assertTrue(cop1.getFollowers().contains(adminUser1));
    }

    @Test
    void testExceptionFollowCop() throws UnityvilleException {
        String coPName = "CoP";

        CoPService coPService = new CoPService();
        coPService.createCoP(coPName, adminUser);

        user1.followCoP(coPService, coPName);
        UnityvilleException exception = assertThrows(
                UnityvilleException.class,
                () -> user1.followCoP(coPService, coPName)
        );

        assertEquals("Member already enrolled in community of practice " + coPName, exception.getMessage());
    }

    @Test
    void testFailedExitGroup() throws UnityvilleException {
        GroupService groupService = new GroupService();
        adminUser.createGroup(groupService, GROUP_NAME);

        boolean response = user1.exitGroup(groupService, GROUP_NAME);
        assertFalse(response);
    }

    @Test
    void testExitGroup() throws UnityvilleException {
        GroupService groupService = new GroupService();
        Group group = adminUser.createGroup(groupService, GROUP_NAME);

        assertTrue(group.getMembers().isEmpty());
        user1.enrollGroup(groupService, GROUP_NAME);
        assertEquals(1, group.getMembers().size());

        boolean response = user1.exitGroup(groupService, GROUP_NAME);
        assertTrue(group.getMembers().isEmpty());
        assertTrue(response);
    }

    @Test
    void testFailedUnfollowCoP() throws UnityvilleException {
        CoPService coPService = new CoPService();
        adminUser.createCoP(coPService, "CoP1");

        boolean response = user1.unfollowCoP(coPService, "CoP1");
        assertFalse(response);
    }

    @Test
    void testUnfollowCoP() throws UnityvilleException {
        CoPService coPService = new CoPService();
        CommunityOfPractice communityOfPractice = adminUser.createCoP(coPService, "CoP1");

        assertTrue(communityOfPractice.getFollowers().isEmpty());
        user1.followCoP(coPService, "CoP1");
        assertEquals(1, communityOfPractice.getFollowers().size());

        boolean response = user1.unfollowCoP(coPService, "CoP1");
        assertTrue(communityOfPractice.getFollowers().isEmpty());
        assertTrue(response);
    }

    @Test
    void testLikePost() throws UnityvilleException {
        GroupService groupService = new GroupService();
        adminUser.createGroup(groupService, GROUP_NAME);

        PostService postService = new PostService();
        Post post = adminUser.createGroupPost(postService, "title", "content", groupService, GROUP_NAME);

        user1.likePost(postService, "title");
        assertEquals(1, post.getLikes());
        assertTrue(user1.getLikes().contains(post));
    }

    @Test
    void testErrorWhenLikePost() throws UnityvilleException {
        GroupService groupService = new GroupService();
        adminUser.createGroup(groupService, GROUP_NAME);

        PostService postService = new PostService();
        adminUser.createGroupPost(postService, "title", "content", groupService, GROUP_NAME);

        UnityvilleException exception = assertThrows(
                UnityvilleException.class,
                () -> user1.likePost(postService, "titlee")
        );
        assertEquals("Post with title titlee does not exists", exception.getMessage());
    }

    @Test
    void testUnlikePost() throws UnityvilleException {
        GroupService groupService = new GroupService();
        adminUser.createGroup(groupService, GROUP_NAME);

        PostService postService = new PostService();
        Post post = adminUser.createGroupPost(postService, "title", "content", groupService, GROUP_NAME);

        user1.likePost(postService, "title");
        assertEquals(1, post.getLikes());
        assertTrue(user1.getLikes().contains(post));
        user1.unlikePost(postService, "title");
        assertEquals(0, post.getLikes());
        assertTrue(user1.getLikes().isEmpty());
    }

    @Test
    void testErrorWhenUnlikePost() throws UnityvilleException {
        GroupService groupService = new GroupService();
        adminUser.createGroup(groupService, GROUP_NAME);

        PostService postService = new PostService();
        adminUser.createGroupPost(postService, "title", "content", groupService, GROUP_NAME);

        UnityvilleException exception = assertThrows(
                UnityvilleException.class,
                () -> user1.unlikePost(postService, "titlee")
        );
        assertEquals("Post with title titlee does not exists", exception.getMessage());
    }

    @Test
    void testCommentPost() throws UnityvilleException {
        GroupService groupService = new GroupService();
        adminUser.createGroup(groupService, GROUP_NAME);

        PostService postService = new PostService();
        Post post = adminUser.createGroupPost(postService, "title", "content", groupService, GROUP_NAME);
        user1.commentPost(postService, "title", "this is a comment");

        assertEquals(1, post.getComments().size());
        assertEquals("this is a comment", post.getComments().toArray()[0]);
        assertEquals("this is a comment", user1.getComments().get(post).toArray()[0]);
    }

    @Test
    void testErrorWhenCommentPost() throws UnityvilleException {
        GroupService groupService = new GroupService();
        adminUser.createGroup(groupService, GROUP_NAME);

        PostService postService = new PostService();
        adminUser.createGroupPost(postService, "title", "content", groupService, GROUP_NAME);

        UnityvilleException exception = assertThrows(
                UnityvilleException.class,
                () -> user1.commentPost(postService, "titlee", "this is a comment")
        );
        assertEquals("Post with title titlee does not exists", exception.getMessage());
    }
}
