package hackathon.nomadworker.domain.service;


import hackathon.nomadworker.domain.model.User;
import hackathon.nomadworker.infra.UploadService;
import hackathon.nomadworker.domain.repository.UserRepository;
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
    public User SignIn(String u_email, String u_password) throws Exception
    {
        User user = userRepository.signIn(u_email, u_password);
        return user;
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
