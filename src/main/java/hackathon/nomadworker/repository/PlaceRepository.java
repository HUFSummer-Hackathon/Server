package hackathon.nomadworker.repository;

import hackathon.nomadworker.domain.Place;
import hackathon.nomadworker.util.Direction;
import hackathon.nomadworker.util.GeometryUtil;
import hackathon.nomadworker.util.Location;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
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


    public List<Place> findPlacesByCategory(String place_cat) {
        String jpql = "select p from Place p where p.p_cate like :place_cat";
        TypedQuery<Place> query = em.createQuery(jpql, Place.class).setParameter("place_cat",place_cat).setMaxResults(1000);
        return query.getResultList();
    }


    public List<Place> getNearByRestaurants(Double latitude, Double longitude, Double distance)
    {
        Location northEast = GeometryUtil
                .calculate(latitude, longitude, distance, Direction.NORTHEAST.getBearing());
        Location southWest = GeometryUtil
                .calculate(latitude, longitude, distance, Direction.SOUTHWEST.getBearing());

        double x1 = northEast.getLatitude();
        double y1 = northEast.getLongitude();
        double x2 = southWest.getLatitude();
        double y2 = southWest.getLongitude();

        String pointFormat = String.format("'LINESTRING(%f %f, %f %f)')", x1, y1, x2, y2);
         Query query = em.createNativeQuery("SELECT p.id, p.p_cate,p.p_name,p.p_image,p_addr "
                        + "FROM place AS p "
                        + "WHERE MBRContains(ST_LINESTRINGFROMTEXT(" + pointFormat + ", a.point)", Place.class)
                .setMaxResults(10);

        List<Place> places = query.getResultList();
        return places;
    }




}

