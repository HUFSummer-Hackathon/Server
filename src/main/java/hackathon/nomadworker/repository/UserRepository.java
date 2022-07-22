package hackathon.nomadworker.repository;


import hackathon.nomadworker.domain.User;
import hackathon.nomadworker.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;


@Repository
@RequiredArgsConstructor
public class UserRepository
{
    @Autowired
    private final EntityManager em;

    public void post(String u_uid, String u_email, String u_password, String u_nickname)
    {
        User user = new User();
        user.setU_uid(u_uid);
        user.setU_email(u_email);
        user.setU_password(u_password);
        user.setU_nickname(u_nickname);
        user.setU_latitude((float) 38.11);
        user.setU_longitude((float) 128.111);
        em.persist(user);
    }

/*
    public User signIn(String u_email, String u_password)
    {
        User user = null;
        TypedQuery<User> emailQuery  = em.createQuery("select distinct u from User u" + " where u.u_email = :u_email ", User.class)
                .setParameter("u_email", u_email);
        user = emailQuery.getSingleResult();
        if (user == null){
            TypedQuery<User> pwQuery  = em.createQuery("select distinct u from User u" + " where u.u_password = :u_password ", User.class)
                    .setParameter("u_password", u_password);
            return pwQuery.getSingleResult();
        }

        return emailQuery.getSingleResult();
    }
*/
    public User signIn(String u_email, String u_password) throws Exception
    {
        User user = null;
        try{
            TypedQuery<User> emailQuery  = em.createQuery("select distinct u from User u" + " where u.u_email = :u_email ", User.class)
                    .setParameter("u_email", u_email);
            return emailQuery.getSingleResult();
        } catch(NoResultException n) {
            return user;
        }
    }
    public User findOne(Long id)
    {
        return em.find(User.class,id);
    }

    public User findOnebyToken(String u_uid)
    {
        return em.createQuery("select distinct u from User u" +
                        " where u.u_uid = :u_uid ", User.class)
                .setParameter("u_uid", u_uid)
                .getSingleResult();
    }

    // 회원 전체 조회
    public List<User> findAll()
    {
        return em.createQuery("select u from User u",User.class)
                .getResultList();
    }

    // 유저 이미지 좌표 업데이트
    public User imageUpdate(Long u_id,String u_image)
    {
        User user = em.find(User.class,u_id);
        user.setU_image(u_image);
        em.merge(user);
        return user;
    }
    public User tokenUpdate(String u_nickname)  {
        User user = em.find(User.class,u_nickname);
        user.setU_uid(AuthService.createToken(u_nickname));
        em.merge(user);
        return user;
    }

    public User coordinateUpdate(Long u_id,float u_latitude ,float u_longitude)
    {
        User user = em.find(User.class,u_id);
        user.setU_latitude(u_latitude);
        user.setU_longitude(u_longitude);
        em.merge(user);
        return user;
    }

    public void delete(Long u_id)
    {
        User user = em.find(User.class, u_id);
        em.remove(user);
    }

    public Long findIdByUuid(String u_uid)
    {
        User user = em.createQuery("select distinct u from User u" +
                        " where u.u_uid = :u_uid ", User.class)
                .setParameter("u_uid", u_uid)
                .getSingleResult();

        return user.getId();
    }

    //For search v1
    public List<User> findOneByNickName(String userNickname) {
        String jpql = "select u from User u where u.u_nickname like :userNickname";
        TypedQuery<User> query = em.createQuery(jpql, User.class).setParameter("userNickname", userNickname ).setMaxResults(1000);
        return query.getResultList();
    }

    public List<User> findOneByEmail(String userEmail) {
        String jpql = "select u from User u where u.u_email like :userEmail";
        TypedQuery<User> query = em.createQuery(jpql, User.class).setParameter("userEmail", userEmail ).setMaxResults(1000);
        return query.getResultList();
    }

    public List<User> findOneByEmailPassword(String userEmail,String userPassword)
    {
        String jpql = "select u from User u where u.u_email like :userEmail and u.u_password like :userPassword";
        TypedQuery<User>query = em.createQuery(jpql, User.class).setParameter("userEmail", userEmail ).setParameter("userPassword", userPassword ).setMaxResults(1000);
        return query.getResultList();
    }



}
