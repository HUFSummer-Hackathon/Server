package hackathon.nomadworker.domain;

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

    private String u_nickname;

    private String u_image;

    private float u_latitude;

    private float u_longitude;

    @OneToMany(mappedBy ="user", cascade = CascadeType.ALL)
    private List<User_Like> userLikeList = new ArrayList<>();
}
