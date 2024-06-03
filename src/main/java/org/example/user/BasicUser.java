package org.example.user;

public class BasicUser extends AbstractUser {
    public BasicUser(String name, String title, String username, String password, String email) {
        super(name, title, username, password, email);
    }

    @Override
    public void createPost(String topic, String content) {
        System.out.println(getUsername() + " created a post in topic: " + topic + " with content: " + content);
    }

}
