package hackathon.nomadworker.domain.service;


import hackathon.nomadworker.domain.model.UserLike;
import hackathon.nomadworker.domain.repository.UserLikeRepository;
import hackathon.nomadworker.domain.repository.UserLikeRepository2;
import hackathon.nomadworker.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserLikeService
{
    private final UserLikeRepository userLikeRepository;
    private final UserRepository userRepository;
    private final UserLikeRepository2 userLikeRepository2;
    @Transactional
    public void newUser_Like(UserLike userLike) {
        userLikeRepository.save(userLike);
    }


    @Transactional
    public void deleteByUserFac(Long u_id, Long f_id){
        UserLike userLike = userLikeRepository.checkUserLike(u_id, f_id);
        userLikeRepository.delete(userLike);
    }

    @Transactional
    public List<UserLike> findUserLikesByFeedId(Long f_id){return userLikeRepository.findByFeedId(f_id);}

    public boolean checkUserFeedLike(String u_uid, Long f_id)
    {
        Long u_id = userRepository.findIdByUuid(u_uid);
        UserLike userLike = userLikeRepository.checkUserLike(u_id, f_id);
        if(userLike != null){
            return true;
        }
        else
            return false;
    }

}
