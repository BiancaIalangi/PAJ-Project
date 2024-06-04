package org.example.post;

import lombok.Getter;
import lombok.Setter;
import org.example.user.User;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public abstract class Post {
    private String title;
    private String content;
    private User author;
    private boolean pinned;
    private int likes;
    private List<String> comments;

    public Post(String title, String content, User author) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.pinned = false;
        this.likes = 0;
        this.comments = new ArrayList<>();
    }

    public void receiveLike() {
        likes++;
    }

    public void receiveUnlike() {
        likes--;
    }

    public void addComment(String comment) {
        comments.add(comment);
    }
}
