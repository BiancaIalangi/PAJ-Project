package org.example.user;

import lombok.Getter;
import lombok.Setter;
import org.example.domain.CommunityOfPractice;
import org.example.domain.Group;
import org.example.exceptions.UnityvilleException;
import org.example.post.CoPPost;
import org.example.post.GroupPost;
import org.example.post.Post;
import org.example.service.CoPService;
import org.example.service.GroupService;
import org.example.service.PostService;

@Getter
@Setter
public class AdminUser extends AbstractUser {
    public AdminUser(String name, String title, String username, String password, String email) {
        super(name, title, username, password, email);
    }

    @Override
    public boolean exitGroup(GroupService groupService, String groupName) throws UnityvilleException {
        Group group = groupService.findByName(groupName);

        if (group.getAdmin().equals(this)) {
            System.out.println("Admin can't exit this group");
            return false;
        }

        return group.exitUser(this);
    }

    @Override
    public boolean unfollowCoP(CoPService coPService, String copName) throws UnityvilleException {
        CommunityOfPractice cop = coPService.findByName(copName);

        if (cop.getCreator().equals(this)) {
            System.out.println("Admin can't exit this CoP");
            return false;
        }

        return cop.removeUser(this);
    }

    public CoPPost deleteCoPPost(PostService postService, String title) {
        for (Post post : postService.getAllPosts()) {
            if (post instanceof CoPPost coPPost && coPPost.getTitle().equals(title)) {
                postService.removePost(coPPost);
                return coPPost;
            }
        }
        return null;
    }

    public GroupPost deleteGroupPost(PostService postService, String title) {
        for (Post post : postService.getAllPosts()) {
            if (post instanceof GroupPost groupPost && groupPost.getTitle().equals(title)) {
                postService.removePost(groupPost);
                return groupPost;
            }
        }
        return null;
    }

    public void editCoPPost(PostService postService, String title, String newContent, String bestPractices) {
        for (Post post : postService.getAllPosts()) {
            if (post instanceof CoPPost coPPost && coPPost.getTitle().equals(title)) {
                if (newContent != null) {
                    coPPost.setContent(newContent);
                }
                if (bestPractices != null) {
                    coPPost.setBestPractices(bestPractices);
                }
            }
        }
    }

    public void editGroupPost(PostService postService, String title, String newContent) {
        for (Post post : postService.getAllPosts()) {
            if (post instanceof GroupPost groupPost && groupPost.getTitle().equals(title) && (newContent != null)) {
                groupPost.setContent(newContent);

            }
        }
    }

    public void pinPost(PostService postService, String title) {
        postService.getAllPosts().stream()
                .filter(post -> post.getTitle().equals(title))
                .forEach(post -> post.setPinned(true));
    }


    public Group createGroup(GroupService groupService, String groupName) throws UnityvilleException {
        return groupService.createGroup(groupName, this);
    }

    public Post createGroupPost(PostService postService, String title, String content,
                                GroupService groupService, String groupName) throws UnityvilleException {
        Group group = groupService.findByName(groupName);

        return postService.createPost(this, title, content, group);
    }

    public CommunityOfPractice createCoP(CoPService coPService, String nameCoP) {
        return coPService.createCoP(nameCoP, this);
    }

    public Post createCoPPost(PostService postService, String title, String content, CoPService coPService, String coPName) throws UnityvilleException {
        CommunityOfPractice cop = coPService.findByName(coPName);

        return postService.createPost(this, title, content, cop);
    }
}
