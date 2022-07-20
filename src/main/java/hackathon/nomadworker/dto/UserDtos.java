package hackathon.nomadworker.dto;


import hackathon.nomadworker.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;

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


    @Data @AllArgsConstructor
    public static class UserPostResponse
    {
        private String nickname;
        private String token;
        private float latitude;
        private float longitude;

    }




    @Data
    @AllArgsConstructor
    public static class OneResult<T>
    {
        private T data;
    }

    @Data
    public static class UserDto
    {
        private String u_uid;
        private String u_email;
        private String u_password;
        private String u_nickname;
        private String u_image;
        private float u_latitude;
        private float u_longitude;
        public UserDto(User user)
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

    @Data
    public static class UserCoordinatePutRequest
    {
        @NotEmpty
        private float latitude;
        @NotEmpty
        private float longitude;
    }


    public static class UserPutResponse extends OneUserResponse
    {
        private String status;
        public UserPutResponse(User user, String status)
        {
            super(user);
            this.status = status;
        }
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
    @AllArgsConstructor
    public static class UserSignInResponse<T>
    {
        private String msg;
        private int status;
        private T data;
    }

    @Data
    public static class UserDeleteResponse
    {
        private String status;
        public UserDeleteResponse(String status)
        {
            this.status = status;
        }
    }

    @Data
    @AllArgsConstructor
    public static class NicknameSearchGetResponse<T>
    {
        private String Message;
        private int status;
        private T data;

    }

}
