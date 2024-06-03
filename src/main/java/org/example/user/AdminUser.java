package org.example.user;

import lombok.Getter;
import lombok.Setter;
import org.example.domain.CommunityOfPractice;
import org.example.domain.Group;
import org.example.exceptions.UnityvilleException;
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
    public void createPost(String topic, String content) {

        System.out.println(getUsername() + " created a post in topic: " + topic + " with content: " + content);
    }

    public void publishNews(String newsContent) {
        System.out.println(getUsername() + " published news: " + newsContent);
    }

    public void deleteNews(int newsId) {
        System.out.println(getUsername() + " deleted news with ID: " + newsId);
    }

    public void editNews(int newsId, String newContent) {
        System.out.println(getUsername() + " edited news with ID: " + newsId + " new content: " + newContent);
    }

    public void pinNews(int newsId) {
        System.out.println(getUsername() + " pinned news with ID: " + newsId);
    }


    public void createGroup(GroupService groupService, String groupName) throws UnityvilleException {
        groupService.createGroup(groupName, this);
    }

    public void createGroupPost(PostService postService, String title, String content,
                                GroupService groupService, String groupName) throws UnityvilleException {
        Group group = groupService.findByName(groupName);

        postService.createPost(this, title, content, group);
    }

    public void createCoP(CoPService coPService, String nameCoP) {
        coPService.createCoP(nameCoP, this);
    }

    public void createCoPPost(PostService postService, String title, String content, CoPService coPService, String coPName) throws UnityvilleException {
        CommunityOfPractice cop = coPService.findByName(coPName);

        postService.createPost(this, title, content, cop);
    }
}
