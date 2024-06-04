package org.example.post;

import org.example.user.User;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public abstract class Post {
    private String title;
    private String content;
    private final User author;
    private boolean pinned;
    private int likes;
    private final Set<String> comments;

    protected Post(String title, String content, User author) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.pinned = false;
        this.likes = 0;
        this.comments = new HashSet<>();
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getAuthor() {
        return author;
    }


    public boolean isPinned() {
        return pinned;
    }

    public void setPinned(boolean pinned) {
        this.pinned = pinned;
    }

    public int getLikes() {
        return likes;
    }

    public Set<String> getComments() {
        return Collections.unmodifiableSet(comments);
    }
}
