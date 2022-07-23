package hackathon.nomadworker.dto;

import hackathon.nomadworker.domain.Feed;
import hackathon.nomadworker.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

public class FeedDtos {


    @Data
    public static class PostResponse
    {
        private String message;
        private int status;

        public PostResponse(String message, int status) {
            this.message = message;
            this.status = status;
        }
    }

    @Data
    @AllArgsConstructor
    public static class Result<T> {
        private String message;
        private int status;
        private T data;
    }

    @Data
    public static class FeedPostRequest
    {
        private String feed_content;
        private Long p_id;
    }
    @Data
    public static class FeeLikeRequest
    {
        private Long f_id;
    }


    @Data
    @AllArgsConstructor
    public static class FeedDto {
        private String u_name;
        private String u_profile;
        private Long p_id;
        private Long f_id;
        private String f_image;
        private String f_content;
        private int f_like;
        private String p_name;

        public FeedDto(Feed f){
            this.u_name = f.getUser().getU_nickname();
            this.u_profile = f.getUser().getU_image();
            this.p_id = f.getPlace().getId();
            this.f_id =  f.getId();
            this.f_image = f.getF_image();
            this.f_content = f.getF_content();
            this.f_like = f.getF_like();
            this.p_name = f.getPlace().getP_name();
        }
    }

    @Data
    @AllArgsConstructor
    public static class FeedUserTotalDto {
        private String u_nickname;
        private String u_image;
        private ArrayList feedList;

        public FeedUserTotalDto(User u, ArrayList a){
            this.u_nickname = u.getU_nickname();
            this.u_image = u.getU_image();
            this.feedList = a;
        }

    }

    @Data
    @AllArgsConstructor
    public static class FeedList {

        private String f_image;
        private Long f_id;

        public FeedList(Feed f){
            this.f_image = f.getF_image();
            this.f_id = f.getId();
        }
    }

    @Data
    @AllArgsConstructor
    public static class FeedOneDto {
        private String f_image;
        private String f_content;
        private int f_like_count;
        private boolean like_status;
        private String f_time;
        private String u_image;
        private String u_nickname;

        public FeedOneDto(User u, Feed f, boolean s){
            this.f_image = f.getF_image();
            this.f_content = f.getF_content();
            this.f_like_count = f.getF_like();
            this.like_status = s;
            this.f_time = f.getF_time();
            this.u_image = u.getU_image();
            this.u_nickname = u.getU_nickname();
        }
    }


    @Data
    public static class userLikePostDeleteResponse
    {
        Boolean like_status;
        Long like_count;

        public userLikePostDeleteResponse(Long cnt, Boolean status) {
            this.like_count = cnt;
            this.like_status = status;
        }

    }

}

