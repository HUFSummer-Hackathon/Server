package hackathon.nomadworker.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @OneToMany(mappedBy ="user", cascade = CascadeType.ALL)
    private List<User_Like> userLikeList = new ArrayList<>();

    public void addLike(User_Like userLike) {
        this.userLikeList.add(userLike);
        userLike.setFeed(this);
    }

}