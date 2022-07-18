package hackathon.nomadworker.service;


import hackathon.nomadworker.domain.User;
import hackathon.nomadworker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Transactional(readOnly=true)
@Service
@RequiredArgsConstructor
public class UserService
{
    private final UserRepository userRepository;

    @Transactional
    public void userPost(String u_uid, String u_email, String u_password, String u_nickname, String u_image, float u_latitude, float u_longitude)
    {
        userRepository.post(u_uid,u_email,u_password,u_nickname,u_image,
                            u_latitude, u_longitude) ;
    }


    // User Repository 의 findAll 정의 (쿼리문이 적혀 있음 )
    public List<User> findUsers()
    {
        return userRepository.findAll();
    }

    // userReposiory 의 find one 의 쿼리문의 따라 날라간다.
    public User findOne(Long userid)
    {
        return userRepository.findOne(userid);
    }

    /// update method for api put,
    @Transactional  User updateImage(Long u_id,String u_image)
    {
        return userRepository.imageUpdate(u_id, u_image);
    }

    @Transactional  User updateCordinate(Long u_id,float u_latitude,float u_longitude)
    {
        return userRepository.cordinateUpdate(u_id,u_latitude,u_longitude);
    }

    @Transactional
    public void deleteOne(Long u_id) {
        userRepository.delete(u_id);
    }

    // u_id =   table column
    public Long findByUuid(String u_uid)
    {
        Long u_id = userRepository.findIdByUuid(u_uid);
        return u_id;
    }

    //Nickname uplicate check search api
    public List<User> findOneByNickName(String userNickname){ return userRepository.findOneByNickName(userNickname);}


}
