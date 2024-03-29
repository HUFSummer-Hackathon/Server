package hackathon.nomadworker.domain.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
public class UserPlace {
    @Id
    @GeneratedValue
    @Column(name="u_p_id")
    private Long id;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name="u_id")
    private User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name="p_id")
    private Place place;



}
