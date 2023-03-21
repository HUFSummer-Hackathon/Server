package hackathon.nomadworker.domain.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter @Setter
public class UserReply {

    @Id
    @GeneratedValue
    @Column(name="r_id")
    private Long id;

    private String r_content;

    private LocalDateTime r_date;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name="u_id")
    private User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name="f_id")
    private Feed feed;

}
