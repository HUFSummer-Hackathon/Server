package hackathon.nomadworker.repository;

import hackathon.nomadworker.domain.User_Like;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserLikeRepository2 extends JpaRepository<User_Like, Long> {

    boolean existsUser_LikeByUserAndFeed(Long u_id, Long f_id);

}
