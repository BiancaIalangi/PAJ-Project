package org.example.user;

public interface User {
    void viewNews();

    void likeNews(int newsId);

    void commentOnNews(int newsId, String comment);

}
