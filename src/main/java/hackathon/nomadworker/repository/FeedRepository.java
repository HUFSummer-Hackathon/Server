package hackathon.nomadworker.repository;

import hackathon.nomadworker.domain.Feed;
import hackathon.nomadworker.domain.Place;
import hackathon.nomadworker.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class FeedRepository {

    @Autowired
    private final EntityManager em;

    public void post(User user, String feed_content, Place place, String imageUrl, String time)
    {
        Feed feed = new Feed();
        feed.setUser(user);
        feed.setF_content(feed_content);
        feed.setPlace(place);
        feed.setF_image(imageUrl);
        feed.setF_like(0);
        feed.setF_time(time);
        feed.setF_like(0);
        em.persist(feed);
    }
    public Feed feedUserLikeUpdate(Long id,int cnt)
        {
            Feed feed = em.find(Feed.class,id);
            feed.setF_like(cnt);

            return feed;
        }

    //모든 피드 조회
    public List<Feed> findALL() {
        return em.createQuery("select f from Feed f " +
                        "join fetch f.user u " +
                        "join fetch f.place p", Feed.class)
                .setMaxResults(10)
                .getResultList();
    }

    public Feed findOne(Long id)
    {
        return em.find(Feed.class,id);
    }



    public User feedUserTotal(String u_uid) {
        return em.createQuery("select u from User u " +
                "join fetch u.feedList fl " +
                "where u.u_uid = :u_uid", User.class)
                .setParameter("u_uid", u_uid)
                .getSingleResult();
    }


    public User findOnebyTokenFeed(String u_uid)
    {
        return em.createQuery("select distinct u from User u" +
                    " where u.u_uid = :u_uid ", User.class)
                .setParameter("u_uid", u_uid)
                .getSingleResult();
    }

    public Feed feedUserOne(Long u_id, Long f_id)
    {
        try {
            return em.createQuery("select f from Feed f " +
                            "where f.id =: f_id " +
                            "and f.user.id =: u_id", Feed.class)
                    .setParameter("f_id", f_id)
                    .setParameter("u_id", u_id)
                    .getSingleResult();
        }catch(NoResultException nre){
            return null;
        }
    }

}