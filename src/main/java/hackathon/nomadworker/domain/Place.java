package hackathon.nomadworker.domain;

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

        @OneToMany(mappedBy = "place",cascade = CascadeType.ALL)
        private List<Feed> feedList = new ArrayList<>();


        @OneToOne(fetch = LAZY, cascade = CascadeType.ALL)
        @JoinColumn(name = "m_id")
        private Menu menu;

        // 연관관계 메서드 //

        public void setMenu(Menu menu){
                this.menu = menu;
                menu.setPlace(this);
        }

        public void addFeed(Feed feed){
                this.feedList.add(feed);
                feed.setPlace(this);
        }

}