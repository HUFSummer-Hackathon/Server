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
    public Long findByUuid(String u_uid)
    {
        Long u_id = userRepository.findIdByUuid(u_uid);
        return u_id;
    }

    //Nickname duplicate check search api
    public List<User> findOneByNickName(String userNickname){ return userRepository.findOneByNickName(userNickname);}

    @Transactional
    public String findOneByEmailPasswordToken(String userEmail,String userPassword,String userToken)
    {
        User user = userRepository.findOneByEmailPassword( userEmail,userPassword);
        Long u_id = userRepository.findIdByUuid(userToken);
        if(user.isEmpty())
        {
            if (user.getId() == u_id)
            {
                return "okall";
            } else {
                userRepository.tokenUpdate(user.getU_nickname());
                return user.getU_uid();
            }
        }
        else
        {
            return "emailpassworderror";
        }
    }

}
