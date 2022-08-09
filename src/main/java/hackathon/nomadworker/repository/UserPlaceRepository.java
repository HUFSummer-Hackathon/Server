package hackathon.nomadworker.repository;

import hackathon.nomadworker.domain.Place;
import hackathon.nomadworker.domain.User;
import hackathon.nomadworker.domain.User_Like;
import hackathon.nomadworker.domain.User_Place;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserPlaceRepository
{

    @Autowired
    private final EntityManager em;

    public void save(User user ,Place place)
    {
        User_Place userPlace = new User_Place();
        userPlace.setUser(user);
        userPlace.setPlace(place);
        em.persist(userPlace);
    }

    public void delete(Long u_p_id){
        User_Place userPlace = em.find(User_Place.class,u_p_id);
        em.remove(userPlace);
    }

    public List<User_Place>findPlacesByUserId(Long u_id)
    {
        return em.createQuery("select s from User_Place s" +
                        " join fetch s.user u" +
                        " join fetch s.place p" +
                        " where s.user.id = :u_id "+" order by s.id desc ", User_Place.class)
                .setParameter("u_id", u_id)
                .getResultList();
    }


}
