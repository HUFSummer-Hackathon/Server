package hackathon.nomadworker.domain.repository;

import hackathon.nomadworker.domain.model.Place;
import hackathon.nomadworker.domain.model.User;
import hackathon.nomadworker.domain.model.Feed;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

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
    public void delete(Long id){
        Feed feed = em.find(Feed.class,id);
        em.remove(feed);
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
                .getResultList();
    }

    public Feed findOne(Long id)
    {
        return em.find(Feed.class,id);
    }

    public User feedUserTotal(Long u_id) {
        try {
        return em.createQuery("select u from User u " +
                "join fetch u.feedList fl " +
                "where u.id = :u_id", User.class)
                .setParameter("u_id", u_id)
                .getSingleResult();
        }catch(NoResultException nre)
        {
            return null;
        }
    }


    public User findOnebyTokenFeed(String u_uid)
    {   try {
        return em.createQuery("select distinct u from User u" +
                    " where u.u_uid = :u_uid ", User.class)
                .setParameter("u_uid", u_uid)
                .getSingleResult();
        }catch(NoResultException nre)
        {
        return null;
        }
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