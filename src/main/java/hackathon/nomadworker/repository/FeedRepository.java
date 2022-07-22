package hackathon.nomadworker.repository;

import hackathon.nomadworker.domain.Feed;
import hackathon.nomadworker.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class FeedRepository {

    @Autowired
    private final EntityManager em;

    //모든 피드 조회
    public List<Feed> findALL() {
        return em.createQuery("select f from Feed f", Feed.class)
                .setMaxResults(10)
                .getResultList();
    }

    public User feedUserTotal(String u_uid) {
        return em.createQuery("select u from User u " +
                "join fetch u.feedList fl " +
                "where u.u_uid = :u_uid", User.class)
                .setParameter("u_uid", u_uid)
                .getSingleResult();
    }

}