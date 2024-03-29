package hackathon.nomadworker.domain.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import static javax.persistence.FetchType.*;

@Entity
@Getter
@Setter
public class Feed {

    @Id
    @GeneratedValue
    @Column(name="f_id")
    private Long id;

    private String f_image;
    private String f_content;
    private int f_like;
    private String f_time;


    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "p_id")
    private Place place;

    // 사용자 테이블

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "u_id")
    private User user;

    @OneToMany(mappedBy ="feed", cascade = CascadeType.ALL)
    private List<UserLike> userLikeList = new ArrayList<>();

    public void addLike(UserLike userLike) {
        this.userLikeList.add(userLike);
        userLike.setFeed(this);
    }

    @OneToMany(mappedBy ="feed", cascade = CascadeType.ALL)
    private List<UserReply> userReplyList = new ArrayList<>();

}