package hackathon.nomadworker.domain.service;



import hackathon.nomadworker.domain.model.Feed;
import hackathon.nomadworker.domain.model.User;
import hackathon.nomadworker.domain.model.UserReply;
import hackathon.nomadworker.domain.repository.FeedRepository;
import hackathon.nomadworker.domain.repository.UserReplyRepository;
import hackathon.nomadworker.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserReplyService {
    private final UserReplyRepository userReplyRepository;
    private final UserRepository userRepository;
    private final FeedRepository feedRepository;

    @Transactional
    public UserReply newReply(String reply_content, long u_id, long f_id, LocalDateTime localDateTime)
    {   User user = userRepository.findOne(u_id);
        Feed feed = feedRepository.findOne(f_id);
        return userReplyRepository.post(reply_content, user , feed,localDateTime);
    }


    @Transactional
    public void deleteByReplyId(Long r_id){userReplyRepository.delete(r_id);}

    @Transactional(readOnly = true)
    public List<UserReply> findRepliesByFeedId(Long f_id)
    {
        return userReplyRepository.findAllByFeedid(f_id);
    }

    @Transactional(readOnly = true)
    public int findRepliesCountByFeedId(Long f_id)
    {
        return userReplyRepository.findAllByFeedid(f_id).size();
    }
    @Transactional(readOnly = true)
    public UserReply findOneByReplyId(Long r_id)
    {
       return  userReplyRepository.findOne(r_id);
    }



}
