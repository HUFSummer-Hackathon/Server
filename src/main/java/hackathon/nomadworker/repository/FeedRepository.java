package hackathon.nomadworker.repository;

import hackathon.nomadworker.domain.Feed;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class FeedRepository {

    @Autowired
    private final EntityManager em;

    //모든 피드 조회
    public List<Feed> findALL() {
        return em.createQuery("select f from Feed f", Feed.class)
                .getResultList();
    }

}
