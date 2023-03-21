package hackathon.nomadworker.domain.repository;

import hackathon.nomadworker.domain.model.UserLike;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserLikeRepository {

    @Autowired
    private final EntityManager em;

//    User_Like userLike
    public void save(UserLike userLike) {
        em.persist(userLike);
    }

    public void delete(UserLike userLike){
        em.remove(userLike);
    }

    public List<UserLike> findByFeedId(Long f_id){
        return em.createQuery("select l from UserLike l" +
                        " join fetch l.user u" +
                        " join fetch l.feed f" +
                        " where l.feed.id = :f_id ", UserLike.class)
                .setParameter("f_id", f_id)
                .getResultList();
    }

//    /**
//     * @param u_id
//     * @param f_id
//     * @return User가 누른 좋아요를 누른 특정 피드를 반환
//     */
//    public User_Like findByUserLike(Long u_id, Long f_id){
//        return em.createQuery("select l from User_Like l " +
//                        "where l.user.id =: u_id " +
//                        "and l.feed.id =: f_id ", User_Like.class)
//                .setParameter("u_id", u_id)
//                .setParameter("f_id", f_id)
//                .getSingleResult();
//    }
    /**
     * @param u_id
     * @param f_id
     * @return User가 누른 좋아요를 누른 특정 피드를 반환
     */
    public UserLike checkUserLike(Long u_id, Long f_id){
        try {
                return em.createQuery("select l from UserLike l " +
                                "where l.user.id =: u_id " +
                                "and l.feed.id =: f_id ", UserLike.class)
                        .setParameter("u_id", u_id)
                        .setParameter("f_id", f_id)
                        .getSingleResult();
        }catch (NoResultException nre)
        {
            return null;
        }
    }


}
