package hackathon.nomadworker.domain.repository;

import hackathon.nomadworker.domain.model.UserLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserLikeRepository2 extends JpaRepository<UserLike, Long> {

    boolean existsUser_LikeByUserAndFeed(Long u_id, Long f_id);

}
