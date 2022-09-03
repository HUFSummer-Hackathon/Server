package hackathon.nomadworker.dto;


import hackathon.nomadworker.domain.User;
import hackathon.nomadworker.domain.User_Place;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

public class UserDtos
{

    @Data
    @AllArgsConstructor
    public static class UserResult<T>
    {
        private int count;
        private T data;
    }

    @Data
    @AllArgsConstructor
    public static class UserResultResponse<T>
    {
        private String message;
        private int status;
        private T data;
    }


    @Data
    public static class UserPostResponse
    {
        private Long userid;
        private String userUrl;
        private String nickname;
        private String token;
        private float latitude;
        private float longitude;

        public UserPostResponse(Long userid, String userUrl, String nickname, String token, float latitude, float longitude) {
            this.userid = userid;
            this.userUrl = userUrl;
            this.nickname = nickname;
            this.token = token;
            this.latitude = latitude;
            this.longitude = longitude;
        }
    }


    @Data @AllArgsConstructor
    public static class UserResponse
    {
        private String message;
        private int status;
    }



    @Data
    @AllArgsConstructor
    public static class OneResult<T>
    {
        private T data;
    }

    @Data
    public static class UserDto
    {   private long u_id;
        private String u_uid;
        private String u_email;
        private String u_password;
        private String u_nickname;
        private String u_image;
        private float u_latitude;
        private float u_longitude;
        public UserDto(User user)
        {
            this.u_id = user.getId();
            this.u_uid = user.getU_uid();
            this.u_email = user.getU_email();
            this.u_password= user.getU_password();
            this.u_nickname = user.getU_nickname();
            this.u_image = user.getU_image();
            this.u_latitude = user.getU_latitude();
            this.u_longitude = user.getU_longitude();
        }
    }

    @Data
    public static class OneUserResponse
    {
        private String u_uid;
        private String u_email;
        private String u_password;
        private String u_nickname;
        private String u_image;
        private float u_latitude;
        private float u_longitude;
        public OneUserResponse(User user)
        {
            this.u_uid = user.getU_uid();
            this.u_email = user.getU_email();
            this.u_password= user.getU_password();
            this.u_nickname = user.getU_nickname();
            this.u_image = user.getU_image();
            this.u_latitude = user.getU_latitude();
            this.u_longitude = user.getU_longitude();
        }
    }


    @Data
    @AllArgsConstructor
    public static class GetUserResponse
    {
        private String id;
    }


    @Data
    public static class UserImagePutRequest
    {
        @NotEmpty private String image;

    }

    @Data @AllArgsConstructor
    public static class UserCoordinatePutRequest
    {

        private float latitude;
        private float longitude;
    }

    @Data @AllArgsConstructor
    public static class UserCoordinatePutResponse
    {
        private String message;
        private int status;
    }

    @Data
    public static class UserPostRequest
    {
        private String u_email;
        private String u_password;
        private String u_nickname;
    }

    @Data
    public static class UserSignInRequest
    {

        private String u_email;
        private String u_password;

    }


    @Data
    public static class UserinfoResponse
    {
        private String u_nickname;
        private String u_image;
        public UserinfoResponse(String url, String u_nickname)
        {
            this.u_nickname = u_nickname;
            this.u_image = url;
        }
    }

    @Data
    @AllArgsConstructor
    public static class UserSignInResponse<T>
    {
        private String message;
        private int status;
        private T data;
    }

    @Data
    public static class UserDeleteResponse
    {
        private String message;
        private String status;

        public UserDeleteResponse(String status)
        {
            this.message = message;
            this.status = status;
        }
    }

    @Data
    public static class NicknameSearchGetResponse
    {
        private String message;
        private int status;

        public NicknameSearchGetResponse(String message, int status)
        {
            this.message = message;
            this.status = status;
        }
    }

    @Data @NoArgsConstructor
    public  static class PlaceSubPostRequest
    {
        private Long u_id;
        private Long p_id;
        private Boolean u_p_scrab ;

    }
    @Data
    public static class PlaceSubGetResponse
    {
        private long u_p_id;
        private long p_id;
        private String place_name;
        private String place_address;
        private String place_weektime;
        private String place_weekendtime;
        private float placeGrade;
        private String p_image;

//        private Boolean u_p_scrab;
        public PlaceSubGetResponse(User_Place s)
        {
            this.u_p_id = s.getId();
            this.p_id = s.getPlace().getId();
            this.place_name = s.getPlace().getP_name();
            this.place_address = s.getPlace().getP_addr();
            this.place_weektime = s.getPlace().getP_weekt();
            this.place_weekendtime = s.getPlace().getP_weekndt();
            this.placeGrade = s.getPlace().getP_grade();
            this.p_image = s.getPlace().getP_image();
//            this.u_p_scrab = true;
        }
    }
}









