package hackathon.nomadworker.repository;


import hackathon.nomadworker.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import java.util.List;


@Repository
@RequiredArgsConstructor
public class UserRepository
{
    private final EntityManager em;

    public void post(String u_uid, String u_email, String u_password, String u_nickname, String u_image, float u_latitude, float u_longitude)
    {
        User user = new User();
        user.setU_uid(u_uid);
        user.setU_email(u_email);
        user.setU_password(u_password);
        user.setU_nickname(u_nickname);
        user.setU_image(u_image);
        user.setU_latitude(u_latitude);
        user.setU_longitude(u_longitude);

        em.persist(user);
    }

    public User findOne(Long id)
    {
        return em.find(User.class,id);
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
    public User cordinateUpdate(Long u_id,float u_latitude ,float u_longitude)
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


}
