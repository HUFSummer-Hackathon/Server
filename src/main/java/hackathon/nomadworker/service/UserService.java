package hackathon.nomadworker.service;


import hackathon.nomadworker.domain.User;
import hackathon.nomadworker.external.UploadService;
import hackathon.nomadworker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;



@Transactional(readOnly=true)
@Service
@RequiredArgsConstructor
public class UserService
{
    private final UserRepository userRepository;
//    private final FileUploadService fileUploadService;
    private final UploadService s3Service;

    @Transactional
    public void userPost(String u_uid, String u_email, String u_password, String u_nickname)
    {
        userRepository.post(u_uid,u_email,u_password,u_nickname) ;
    }

    public User userImagePost(String u_uid, String imageUrl)
    {
        Long u_id = userRepository.findOnebyToken(u_uid).getId();
        return userRepository.imageUpdate(u_id,imageUrl);
    }

    //==Sign In==//
    public User SignIn(String u_email, String u_password) throws Exception {
        User result = null;

        User user = userRepository.signIn(u_email, u_password);
        try
        {

            if (user.getU_email().equals(u_email) == true && user.getU_password().equals(u_password) == true) {
                user.setU_latitude((float) 38.11);
                user.setU_longitude((float) 128.111);
            } else if (user.getU_email().equals(u_email) == false || user.getU_password().equals(u_password) == false) {
                user.setU_nickname(null);
                user.setU_uid(null);
                user.setU_latitude((float) 0.0);
                user.setU_longitude((float) 0.0);
            }
            result = user;

        }catch(EmptyResultDataAccessException e) {
            user.setU_nickname(null);
            user.setU_uid(null);
            user.setU_latitude((float) 0.0);
            user.setU_longitude((float) 0.0);
            result = user;
        }finally {
            return result;
        }
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

    public User findOnebyToken(String u_uid)
    {
        return userRepository.findOnebyToken(u_uid);
    }

    // s3 에 올라간 imageurl 을 받는다.
    @Transactional
    public User userImageUpdate(String u_uid, String imageUrl)
    {
        Long u_id = userRepository.findOnebyToken(u_uid).getId();
        String u_image = userRepository.findOnebyToken(u_uid).getU_image();
        if(u_image != null){ //s3 data 삭제
            ///FileUploadService.fileUploadService.
            String result =  s3Service.deleteObject(u_image); // 기존 이미지 삭제

            return userRepository.imageUpdate(u_id,imageUrl);

        }else {
            return userRepository.imageUpdate(u_id,imageUrl);
        }
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
    public List<User> findOneByNickName(String userNickname)
    {
        return userRepository.findOneByNickName(userNickname);
    }

    public List<User> findOneByEmailPassword(String userEmail,String userPassword)
    {
        return userRepository.findOneByEmailPassword(userEmail,userPassword);
    }

    @Transactional
    public User updateCoordinate(String u_uid,float u_latitude, float u_longitude)
    {
        Long u_id = findByUuid(u_uid);
        return userRepository.coordinateUpdate(u_id, u_latitude, u_longitude);
    }



}
