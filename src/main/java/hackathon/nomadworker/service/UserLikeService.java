package hackathon.nomadworker.service;


import hackathon.nomadworker.domain.User_Like;
import hackathon.nomadworker.repository.UserLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserLikeService
{
    private final UserLikeRepository userLikeRepository;
    @Transactional
    public void newUser_Like(User_Like userLike) {
        userLikeRepository.save(userLike);
    }


    @Transactional
    public void deleteByUserFac(Long u_id, Long f_id){
        User_Like userLike = userLikeRepository.findByUserLike(u_id, f_id);
        userLikeRepository.delete(userLike);
    }

    @Transactional
    public List<User_Like> findUserLikesByFeedId(Long id){return userLikeRepository.findByFacId(id);}


}
