package hackathon.nomadworker.dto;

import hackathon.nomadworker.domain.Feed;
import hackathon.nomadworker.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

public class FeedDtos {

    @Data
    @AllArgsConstructor
    public static class Result<T> {
        private String message;
        private int status;
        private T data;
    }

    @Data
    @AllArgsConstructor
    public static class FeedDto {
        private Long id;
        private String f_image;
        private String f_content;
        private int f_like;
        private String f_time;

        public FeedDto(Feed f){
            this.id =  f.getId();
            this.f_image = f.getF_image();
            this.f_content = f.getF_content();
            this.f_like = f.getF_like();
            this.f_time = f.getF_time();
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

}

