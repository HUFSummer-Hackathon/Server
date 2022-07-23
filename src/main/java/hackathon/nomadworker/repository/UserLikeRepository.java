package hackathon.nomadworker.repository;

import hackathon.nomadworker.domain.User_Like;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserLikeRepository {

    @Autowired
    private final EntityManager em;

//    User_Like userLike
    public void save(User_Like userLike) {
        em.persist(userLike);
    }

    public void delete(User_Like userLike){
        em.remove(userLike);
    }

    public List<User_Like> findByFacId(Long id){
        return em.createQuery("select l from User_Like l" +
                        " join fetch l.user u" +
                        " join fetch l.feed f" +
                        " where l.feed.id = :id ", User_Like.class)
                .setParameter("id", id)
                .getResultList();
    }

    /**
     * @param u_id
     * @param f_id
     * @return User가 누른 좋아요를 누른 특정 피드를 반환
     */
    public User_Like findByUserLike(Long u_id, Long f_id){
        return em.createQuery("select l from User_Like l " +
                        "where l.user.id =: u_id " +
                        "and l.feed.id =: f_id ", User_Like.class)
                .setParameter("u_id", u_id)
                .setParameter("f_id", f_id)
                .getSingleResult();
    }







}
