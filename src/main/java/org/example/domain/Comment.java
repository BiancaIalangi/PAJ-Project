package org.example.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.example.post.Post;

@Getter
@Setter
@AllArgsConstructor
public class Comment {
    private int commentId;
    private String content;
    private String author;
    private Post post;

    @Override
    public String toString() {
        return "Comment{" +
                "commentId=" + commentId +
                ", content='" + content + '\'' +
                ", author='" + author + '\'' +
                ", post=" + post +
                '}';
    }
}
