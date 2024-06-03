package org.example.post;

import lombok.Getter;
import lombok.Setter;
import org.example.user.User;

@Getter
@Setter
public abstract class Post {
    private String title;
    private String content;
    private User author;
    private boolean pinned;

    public Post(String title, String content, User author) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.pinned = false;
    }

}
