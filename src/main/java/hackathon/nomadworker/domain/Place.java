package hackathon.nomadworker.domain;

import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Setter
public class Place {

        @Id @GeneratedValue
        @Column(name = "p_id")
        private long id;

        @OneToMany(mappedBy = "place",cascade = CascadeType.ALL)
        private List<Feed> feeds = new ArrayList<>();


        private String p_cate;

        private String p_name;

        private String p_weekt;

        private String p_weekndt;

        private String p_addr;

        private String p_image;

        private String p_storeType;



}
