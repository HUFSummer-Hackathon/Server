package hackathon.nomadworker.service;

import hackathon.nomadworker.api.ExceptionController;
import hackathon.nomadworker.domain.Feed;
import hackathon.nomadworker.domain.Place;
import hackathon.nomadworker.domain.User;
import hackathon.nomadworker.repository.FeedRepository;
import hackathon.nomadworker.repository.PlaceRepository;
import hackathon.nomadworker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly=true)
@Service
@RequiredArgsConstructor
public class FeedService {

    @Autowired
    private final FeedRepository feedRepository;
    private final PlaceRepository placeRepository;

    @Transactional
    public void feedPost(String u_uid, String feed_content, Long p_id, String imageUrl, String time)
    {
        User user = feedRepository.findOnebyTokenFeed(u_uid);
        Place place = placeRepository.getPlacesById(p_id);
        feedRepository.post(user, feed_content, place, imageUrl, time);
    }

    //모든 Feed 조회
    public List<Feed> feedAll(){
        return feedRepository.findALL();
    }

    public User feedUserTotal(String u_uid)
    {
        User feedUserTotal = feedRepository.feedUserTotal(u_uid);
        return feedUserTotal;
    }

    public Feed feedUserOne(String u_uid, Long f_id)
    {
        Long u_id = feedRepository.findOnebyTokenFeed(u_uid).getId();
        Feed oneFeed = feedRepository.feedUserOne(u_id, f_id);
        return oneFeed;
    }

    public Feed findOne(Long f_id)
    {
        return feedRepository.findOne(f_id);
    }
    @Transactional
    public Feed feedUserLikeUpdate(Long f_id,int cnt)
    {
        return feedRepository.feedUserLikeUpdate(f_id,cnt);
    }



}
