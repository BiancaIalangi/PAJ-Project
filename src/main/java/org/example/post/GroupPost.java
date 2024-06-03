package org.example.post;

import lombok.Getter;
import lombok.Setter;
import org.example.domain.Group;
import org.example.user.User;

@Getter
@Setter
public class GroupPost extends Post {
    private Group group;

    public GroupPost(String title, String content, User author, Group group) {
        super(title, content, author);
        this.group = group;
    }

}
