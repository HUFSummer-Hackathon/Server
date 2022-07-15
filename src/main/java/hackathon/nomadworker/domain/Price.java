package hackathon.nomadworker.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
public class Price {

    @Id
    @GeneratedValue
    @Column(name = "pr_id")
    private long id;

    @OneToOne(mappedBy = "price", fetch= LAZY)
    private Place place;

    private Integer rent_price;

}
