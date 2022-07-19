package hackathon.nomadworker.repository;

import hackathon.nomadworker.domain.Place;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PlaceRepository {
    private final EntityManager em;

    public Place findOne(Long id) {
        return em.find(Place.class, id);
    }

    public List<Place> findAll() {
        return em.createQuery("select p from Place p", Place.class)
                .getResultList();
    }

    public List<Place> findOneByNickName(String palceName) {
        String jpql = "select p from Place p where p.p_name like :palceName";
        TypedQuery<Place> query = em.createQuery(jpql, Place.class).setParameter("palceName", palceName).setMaxResults(1000);
        return query.getResultList();
    }

}

