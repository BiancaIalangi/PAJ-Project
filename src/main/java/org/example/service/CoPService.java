package org.example.service;

import lombok.Getter;
import lombok.Setter;
import org.example.domain.CommunityOfPractice;
import org.example.exceptions.UnityvilleException;
import org.example.post.CoPPost;
import org.example.post.Post;
import org.example.user.AdminUser;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CoPService {
    private List<CommunityOfPractice> communityOfPractices = new ArrayList<>();

    public List<CommunityOfPractice> getAllCoPs() {
        return communityOfPractices;
    }

    private CommunityOfPractice findCoPByName(String coPName) throws UnityvilleException {
        return communityOfPractices.stream()
                .filter(coP -> coP.getName().equals(coPName))
                .findFirst()
                .orElseThrow(() -> new UnityvilleException("Community of practice with name " + coPName + " does not exists."));
    }

    public List<Post> getPinnedPostsFromCoP(String coPName, List<Post> posts) throws UnityvilleException {
        CommunityOfPractice cop = findCoPByName(coPName);

        List<CoPPost> groupPosts = posts.stream()
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

        throw new UnityvilleException("Community of Practice with name + " + name + "does not exists");
    }

//    public void createCoP()
}
