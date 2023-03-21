package hackathon.nomadworker.domain.service;

import hackathon.nomadworker.domain.model.Feed;
import hackathon.nomadworker.domain.model.Place;
import hackathon.nomadworker.domain.model.User;
import hackathon.nomadworker.domain.repository.FeedRepository;
import hackathon.nomadworker.domain.repository.PlaceRepository;
import hackathon.nomadworker.domain.repository.UserLikeRepository2;
import hackathon.nomadworker.domain.repository.UserRepository;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly=true)
@Service
@RequiredArgsConstructor
public class FeedService {

    @Autowired
    private final FeedRepository feedRepository;
    private final PlaceRepository placeRepository;
    private final UserRepository userRepository;
    private final UserLikeRepository2 userLikeRepository2;

    @Async
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void feedPost(String u_uid, String feed_content, Long p_id, String imageUrl, String time)
    {
        User user = feedRepository.findOnebyTokenFeed(u_uid);
        Place place = placeRepository.getPlacesById(p_id);
        feedRepository.post(user, feed_content, place, imageUrl, time);
    }

    //모든 Feed 조회
    public List<Feed> feedAll()
    {
        return feedRepository.findALL();
    }

    public User feedUserTotal(Long u_id)
    {
        User feedUserTotal = feedRepository.feedUserTotal(u_id);
        if(feedUserTotal == null)
        {
            feedUserTotal = userRepository.findOne(u_id);
        }
        return feedUserTotal;
    }

    public Feed feedUserOne(String u_uid, Long f_id)
    {
        Long u_id = feedRepository.findOnebyTokenFeed(u_uid).getId();
        Feed oneFeed = feedRepository.feedUserOne(u_id, f_id);
        return oneFeed;
    }

    /**
     *
     * @param f_id
     * @return Feed 의 객체를 반환
     */
    public Feed findOne(Long f_id)
    {
        return feedRepository.findOne(f_id);
    }

    @Transactional
    public Feed feedUserLikeUpdate(Long f_id,int cnt) {
        return feedRepository.feedUserLikeUpdate(f_id,cnt);
    }

    /**
     * @param f_id : Long feed id
     *  return : none
     */
    @Transactional
    public  void deleteByFid(Long f_id){
        feedRepository.delete(f_id);
    }

}
