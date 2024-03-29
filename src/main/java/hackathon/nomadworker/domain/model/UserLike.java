package hackathon.nomadworker.domain.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
public class UserLike
{

    @Id
    @GeneratedValue
    @Column(name="like_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name="u_id")
    private User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name="f_id")
    private Feed feed;

}
