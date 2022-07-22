package hackathon.nomadworker.domain;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.locationtech.jts.geom.Point;
import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;


@Entity
@Getter
@Setter
public class Place {

        @Id @GeneratedValue
        @Column(name = "p_id")
        private long id;

        private String p_cate;

        private String p_name;

        private String p_weekt;

        private String p_weekndt;

        private String p_addr;

        private String p_image;

        private String p_storeType;

        private float p_latitude;
        private float p_longitude;

        private Integer rent_price;

        private Point p_gpoint;

        @OneToMany(mappedBy = "place", cascade = CascadeType.ALL)
        private List<Feed> feedList = new ArrayList<>();

<<<<<<< HEAD
        @JsonIgnore
        @OneToOne(fetch = LAZY, cascade = CascadeType.ALL)
        @JoinColumn(name = "m_id")
        private Menu menu;
=======

        @OneToMany
        private List<Menu> menu = new ArrayList<>();
>>>>>>> f1c2e517cbdbaf41bd07a1191847b15400b0cdac


        public void addFeed(Feed feed){
                this.feedList.add(feed);
                feed.setPlace(this);
        }



}