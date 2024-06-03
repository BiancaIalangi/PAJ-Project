package org.example.post;

import lombok.Getter;
import lombok.Setter;
import org.example.domain.CommunityOfPractice;
import org.example.user.User;

@Getter
@Setter
public class CoPPost extends Post {
    private CommunityOfPractice communityOfPractice;
    private String bestPractices;
    private String caseStudies;
    private String events;

    public CoPPost(String title, String content, User author, CommunityOfPractice communityOfPractice, String bestPractices, String caseStudies, String events) {
        super(title, content, author);
        this.communityOfPractice = communityOfPractice;
        this.bestPractices = bestPractices;
        this.caseStudies = caseStudies;
        this.events = events;
    }

}
