package hackathon.nomadworker.repository;

import hackathon.nomadworker.domain.User;
import hackathon.nomadworker.domain.Feed;
import hackathon.nomadworker.domain.User_Reply;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@RequiredArgsConstructor

public class UserReplyRepository {
    @Autowired
    private final EntityManager em;

    public void post(String reply_content, User user , Feed feed)
    {
        User_Reply userReply = new User_Reply();
        userReply.setUser(user);
        userReply.setFeed(feed);
        userReply.setR_content(reply_content);
        em.persist(userReply);
    }

    public void delete(Long r_id)
    {
        User_Reply userReply = em.find(User_Reply.class, r_id);
        em.remove(userReply);
    }
    public User_Reply findOne(Long id)
    {
        return em.find(User_Reply.class,id);
    }
    /**
     * @param f_id
     * @return   User가 누른 좋아요를 누른 특정 피드의 모든 댓글 반환
     */
    public List<User_Reply> findAllByFeedid(Long f_id)
    {
        String jpql = "select r from User_Reply r "+ "join fetch r.feed f" +" where f.id = :f_id" +" order by r.id desc ";
        TypedQuery<User_Reply> query = em.createQuery(jpql, User_Reply.class).setParameter("f_id",f_id).setMaxResults(100);
        return query.getResultList();
    }


}
