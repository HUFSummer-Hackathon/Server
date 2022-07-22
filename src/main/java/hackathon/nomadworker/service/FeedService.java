package hackathon.nomadworker.service;

import hackathon.nomadworker.domain.Feed;
import hackathon.nomadworker.domain.User;
import hackathon.nomadworker.repository.FeedRepository;
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

    //모든 Feed 조회
    public List<Feed> feedAll(){
        return feedRepository.findALL();
    }

    public User feedUserTotal(String u_uid)
    {
        User feedUserTotal = feedRepository.feedUserTotal(u_uid);
        return feedUserTotal;
    }
}
