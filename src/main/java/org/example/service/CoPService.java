package org.example.service;

import org.example.domain.CommunityOfPractice;
import org.example.exceptions.UnityvilleException;
import org.example.post.CoPPost;
import org.example.post.Post;
import org.example.user.AdminUser;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CoPService {
    private Set<CommunityOfPractice> communityOfPractices = new HashSet<>();

    public Set<CommunityOfPractice> getCommunityOfPractices() {
        return Collections.unmodifiableSet(communityOfPractices);
    }

    private CommunityOfPractice findCoPByName(String coPName) throws UnityvilleException {
        return communityOfPractices.stream()
                .filter(coP -> coP.getName().equals(coPName))
                .findFirst()
                .orElseThrow(() -> new UnityvilleException("Community of practice with name " + coPName + " does not exists."));
    }

    public List<Post> getPinnedPostsFromCoP(String coPName, PostService postService) throws UnityvilleException {
        CommunityOfPractice cop = findCoPByName(coPName);

        List<CoPPost> groupPosts = postService.getAllPosts().stream()
                .filter(CoPPost.class::isInstance)
                .map(CoPPost.class::cast)
                .toList();

        return groupPosts
                .stream()
                .filter(post -> post.getCommunityOfPractice().equals(cop) && post.isPinned())
                .map(Post.class::cast)
                .toList();
    }

    public CommunityOfPractice createCoP(String name, AdminUser user) {
        CommunityOfPractice cop = new CommunityOfPractice(name, user);
        communityOfPractices.add(cop);
        return cop;
    }

    public CommunityOfPractice findByName(String name) throws UnityvilleException {
        for (CommunityOfPractice cop : communityOfPractices) {
            if (cop.getName().equals(name)) {
                return cop;
            }
        }

        throw new UnityvilleException("Community of Practice with name " + name + " does not exists");
    }

}
