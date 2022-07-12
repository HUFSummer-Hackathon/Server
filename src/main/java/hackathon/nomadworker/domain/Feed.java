package hackathon.nomadworker.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class Feed {

    @Id
    @GeneratedValue
    @Column(name="f_id")
    private Long id;

    private String f_image;
    private String f_content;
    private int f_like;
    private String f_time;
}
