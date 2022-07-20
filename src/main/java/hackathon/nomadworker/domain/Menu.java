package hackathon.nomadworker.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;
@Entity
@Getter
@Setter
public class Menu {

    @Id
    @GeneratedValue
    @Column(name = "m_id")
    private long id;

    private String m_menu;

    private Integer m_price;

    @OneToOne(mappedBy = "menu",cascade = CascadeType.ALL,fetch= LAZY)
    private Place place;
}

