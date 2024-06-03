package org.example.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.example.post.Post;
import org.example.user.User;

@Getter
@Setter
@AllArgsConstructor
public class Like {
    private User user;
    private Post post;

    @Override
    public String toString() {
        return "Like{" +
                "user=" + user +
                ", post=" + post +
                '}';
    }
}
