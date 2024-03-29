package hackathon.nomadworker.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue
    @Column(name="u_id") //pk
    private Long id;

    private String u_uid;

    private String u_email;

    private String u_password;

    @JsonIgnore
    private String u_nickname;

    @JsonIgnore
    private String u_image;

    private float u_latitude;

    private float u_longitude;

    @OneToMany(mappedBy ="user", cascade = CascadeType.ALL)
    private List<UserLike> userLikeList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Feed> feedList = new ArrayList<>();

    @OneToMany(mappedBy ="user", cascade = CascadeType.ALL)
    private List<UserReply> userReplyList = new ArrayList<>();

    @OneToMany(mappedBy ="user", cascade = CascadeType.ALL)
    private List<UserPlace> user_placeList = new ArrayList<>();

    public void addLike(UserLike userLike) {
        this.userLikeList.add(userLike);
        userLike.setUser(this);
    }
}
