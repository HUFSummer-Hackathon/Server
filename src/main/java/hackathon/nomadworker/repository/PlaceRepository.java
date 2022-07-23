package hackathon.nomadworker.repository;

import hackathon.nomadworker.domain.Feed;

import hackathon.nomadworker.domain.Place;
import hackathon.nomadworker.domain.User;
import hackathon.nomadworker.util.Direction;
import hackathon.nomadworker.util.GeometryUtil;
import hackathon.nomadworker.util.Location;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PlaceRepository {

    @Autowired
    private final EntityManager em;

    public Place getPlacesById(Long id) {
        return em.find(Place.class, id);
    }

    public List<Place> findAll() {
        return em.createQuery("select p from Place p", Place.class)
                .getResultList();
    }

    public List<Place> findOneByNickName(String placeName) {
        String jpql = "select p from Place p where p.p_name like :placeName";
        TypedQuery<Place> query = em.createQuery(jpql, Place.class).setParameter("placeName", placeName).setMaxResults(1000);
        return query.getResultList();
    }


    public List<Place> findPlacesByCategory(String place_cat) {
        String jpql = "select p from Place p where p.p_cate like :place_cat";
        TypedQuery<Place> query = em.createQuery(jpql, Place.class).setParameter("place_cat",place_cat).setMaxResults(1000);
        return query.getResultList();
    }


    public List<Place> getNearByCoordinate(Double latitude, Double longitude, Double distance)
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
        Query query = em.createNativeQuery("SELECT * \n"+
                "From place As p \n" +
                "WHERE MBRContains(ST_LINESTRINGFROMTEXT(" + pointFormat + ",p.p_gpoint)",Place.class).setMaxResults(15);

        List<Place> places = query.getResultList();
        System.out.println(places);
        return places;
    }

    public List<Feed> getRecommendPlace()
    {
        List<Feed> feed =  em.createQuery("select f from Feed f " +
                        "join fetch f.place p " +
                        "order by f.f_like desc ", Feed.class).setMaxResults(10).getResultList();
        return feed;
    }



    public List<Place> searchPlace(String p_cate, String p_storeType, String p_name)
    {
        List<Place> place = em.createQuery("select p from Place p " +
                        "where p.p_cate =: p_cate " +
                        "and p.p_storeType =: p_storeType " +
                        "and p.p_name =: p_name ", Place.class)
                .setParameter("p_cate", p_cate)
                .setParameter("p_storeType", p_storeType)
                .setParameter("p_name", p_name)
                .getResultList();

        return place;
    }

    public List<Place> searchOneByName(String p_name) {
        String jpql = "select p from Place p where p.p_name like :p_name";
        TypedQuery<Place> query = em.createQuery(jpql, Place.class).setParameter("p_name", "%" + p_name +  "%").setMaxResults(1000);
        return query.getResultList();
    }
}

