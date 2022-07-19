package hackathon.nomadworker.service;


import hackathon.nomadworker.domain.User;
import hackathon.nomadworker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Transactional(readOnly=true)
@Service
@RequiredArgsConstructor
public class UserService
{
    private final UserRepository userRepository;

    @Transactional
    public void userPost(String u_uid, String u_email, String u_password, String u_nickname)
    {
        userRepository.post(u_uid,u_email,u_password,u_nickname) ;
    }


    // UserRepository 의 findAll 정의 (쿼리문이 적혀 있음 )
    public List<User> findUsers()
    {
        return userRepository.findAll();
    }

    // UserRepository 의 find one 의 쿼리문의 따라 날라간다.
    public User findOne(Long userid)
    {
        return userRepository.findOne(userid);
    }

    /// update method for api put
    @Transactional  User updateImage(Long u_id,String u_image)
    {
        return userRepository.imageUpdate(u_id, u_image);
    }

    @Transactional  User updateCoordinate(Long u_id,float u_latitude,float u_longitude)
    {
        return userRepository.coordinateUpdate(u_id,u_latitude,u_longitude);
    }

    @Transactional
    public void deleteOne(Long u_id) {
        userRepository.delete(u_id);
    }

    //u_id =   table column
    public List<User> findByUuid(String u_uid)
    {
        List<User> users = userRepository.findIdByUuid(u_uid);
        return users;
    }

    //Nickname duplicate check search api
    public List<User> findOneByNickName(String userNickname)
    {
        return userRepository.findOneByNickName(userNickname);
    }

    public List<User> findOneByEmailPassword(String userEmail,String userPassword)
    {
        return userRepository.findOneByEmailPassword(userEmail,userPassword);
    }

}
