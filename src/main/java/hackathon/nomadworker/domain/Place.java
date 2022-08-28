package hackathon.nomadworker.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.locationtech.jts.geom.Point;
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

        private String p_cate;

        private String p_name;

        private String p_weekt;

        private String p_weekndt;

        private float p_grade;

        private int p_count;

        @Column(name = "p_addr")
        private String p_addr;

        private String p_image;

        private String p_storeType;

        private String rent_price;

        private float p_latitude;

        private float p_longitude;

        private Point p_gpoint;

        @JsonIgnore
        @OneToMany(mappedBy = "place", cascade = CascadeType.ALL)
        private List<Feed> feedList = new ArrayList<>();

        @OneToMany(mappedBy = "place", cascade = CascadeType.ALL)
        private List<User_Place> user_placeList = new ArrayList<>();
        public void addFeed(Feed feed)
        {
                this.feedList.add(feed);
                feed.setPlace(this);
        }
}