package org.example.user;

import org.example.exceptions.UnityvilleException;
import org.example.service.CoPService;
import org.example.service.GroupService;
import org.example.service.PostService;

public interface User {
    void enrollGroup(GroupService groupService, String groupName) throws UnityvilleException;

    void followCoP(CoPService coPService, String copName) throws UnityvilleException;

    void likePost(PostService postService, String title) throws UnityvilleException;

    void unlikePost(PostService postService, String title) throws UnityvilleException;

    void commentPost(PostService postService, String title, String comment) throws UnityvilleException;
}
