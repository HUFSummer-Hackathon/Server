package hackathon.nomadworker.domain.repository;
import hackathon.nomadworker.domain.model.Place;
import hackathon.nomadworker.domain.model.User;
import hackathon.nomadworker.domain.model.UserPlace;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserPlaceRepository
{

    @Autowired
    private final EntityManager em;

    public void save(User user , Place place)
    {
        UserPlace userPlace = new UserPlace();
        userPlace.setUser(user);
        userPlace.setPlace(place);
        em.persist(userPlace);
    }

    public void delete(Long u_p_id){
        UserPlace userPlace = em.find(UserPlace.class,u_p_id);
        em.remove(userPlace);
    }
    public void deleteUidPid(Long u_id,Long p_id)
    {
        String jpql ="select s from UserPlace s " + "where s.user.id =: u_id " + "and s.place.id =: p_id ";
        Query query = em.createQuery(jpql, UserPlace.class)
                .setParameter("u_id", u_id)
                .setParameter("p_id", p_id);
        em.remove(query.getSingleResult());
    }

    public List<UserPlace>findPlacesByUserId(Long u_id)
    {
        return em.createQuery("select s from UserPlace s" +
                        " join fetch s.user u" +
                        " join fetch s.place p" +
                        " where s.user.id = :u_id "+" order by s.id desc ", UserPlace.class)
                .setParameter("u_id", u_id)
                .getResultList();
    }

    /**
     *
     * @param u_id
     * @param p_id
     * @return Boolean if exist return true  , Query is search fo id
     */
    public Boolean findUserPlaceByFidUid(Long u_id , Long p_id)
    {
        try {
            String jpql ="select s.id from UserPlace s " + "where s.user.id =: u_id " + "and s.place.id =: p_id ";
            Query query = em.createQuery(jpql, Long.class)
                    .setParameter("u_id", u_id)
                    .setParameter("p_id", p_id);
            query.getSingleResult();
            return true;
        }catch (NoResultException nre)
        {
            return false;
        }
    }
}

