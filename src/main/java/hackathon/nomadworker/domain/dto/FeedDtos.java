package hackathon.nomadworker.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import hackathon.nomadworker.domain.model.UserReply;
import hackathon.nomadworker.domain.model.Feed;
import hackathon.nomadworker.domain.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;


import java.time.LocalDateTime;
import java.util.ArrayList;

public class FeedDtos {

    //==Response DTO==//

    @Data
    public static class FeedResultResponseNoData
    {
        private String message;
        private int status;

        public FeedResultResponseNoData(String message, int status){
            this.message = message;
            this.status = status;
        }
    }

    @Data
    @AllArgsConstructor
    public static class FeedResultResponse<T>
    {
        private String message;
        private int status;
        private T data;
    }


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
    public static class FeedPostRequest
    {
        private String feed_content;
        private Long p_id;
    }
    @Data
    public static class FeedLikeRequest
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
        private int f_like_count;
        private boolean like_status;
        private String p_name;
        private int r_count ;
        public FeedDto(Feed f, boolean like_status,int r_count){
            this.u_name = f.getUser().getU_nickname();
            this.u_profile = f.getUser().getU_image();
            this.p_id = f.getPlace().getId();
            this.f_id =  f.getId();
            this.f_image = f.getF_image();
            this.f_content = f.getF_content();
            this.f_like_count = f.getF_like();
            this.like_status = like_status;
            this.p_name = f.getPlace().getP_name();
            this.r_count=r_count;
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
    @NoArgsConstructor
    public static class Feedpostrequest
    {
        String feed_content;
        String p_id;
    }

    @Data
    @AllArgsConstructor
    public static class FeedOneDto
    {
        private String f_image;
        private String f_content;
        private int f_like_count;
        private boolean like_status;
        private String f_time;
        private String p_name;
        private String u_image;
        private String u_nickname;
        private int r_count ;
        public FeedOneDto(Feed f, boolean s,int r_count)
        {
            this.f_image = f.getF_image();
            this.f_content = f.getF_content();
            this.f_like_count = f.getF_like();
            this.like_status = s;
            this.f_time = f.getF_time();
            this.p_name=f.getPlace().getP_name();
            this.u_image = f.getUser().getU_image();
            this.u_nickname = f.getUser().getU_nickname();
            this.r_count = r_count ;
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
    @Data
    @NoArgsConstructor
    public static class NewReplyRequestDto{
        private Long f_id ;
        private Long u_id ;
        private String r_content ;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime r_date;
    }
    @Data
    @NoArgsConstructor
    public static class DeleteReplyRequestDto{
        private Long r_id ;

    }

    @Data
    public  static class GetReplyResponseDto<T>
    {

        private String f_content;
        private String u_nickname ;
        private String u_image;
        private String p_name;
        private T reply;

        public GetReplyResponseDto(Feed feed, T reply)
        {
            this.f_content = feed.getF_content();
            this.u_nickname = feed.getUser().getU_nickname();
            this.u_image = feed.getUser().getU_image();
            this.p_name = feed.getPlace().getP_name();
            this.reply = reply;
        }
    }
    @Data
    public static class NewReply
    {
        private long r_id;
        private String r_content;
//        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
//        private LocalDateTime r_date ;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        String r_date ;
         private long u_id;
        private String u_nickname;
        private String u_image;
        public NewReply(UserReply reply)
        {
            this.r_id = reply.getId();
            this.r_content = reply.getR_content();
            this.r_date = reply.getR_date().toString();
            this.u_id = reply.getUser().getId();
            this.u_nickname = reply.getUser().getU_nickname();
            this.u_image = reply.getUser().getU_image();
        }
    }
    @Data
    public  static class PostReplyResponseDto
    {
        private NewReply reply;
        public PostReplyResponseDto(UserReply reply)
        {
            this.reply = new NewReply(reply);
        }
    }




    @Data
    public  static class ReplyResponseDto
    {
        long r_id;
        String r_content;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        String r_date ;
        long u_id;
        String u_nickname ;
        String u_image;

        public ReplyResponseDto(UserReply r)
        {
            this.r_id = r.getId();
            this.r_content = r.getR_content();
            this.r_date = r.getR_date().toString();
            this.u_id = r.getUser().getId();
            this.u_nickname = r.getUser().getU_nickname();
            this.u_image = r.getUser().getU_image() ;
        }

    }



    // Test dto for android
    //
    @Data @NoArgsConstructor
    public  static class PostReplydateTimeRequest
    {   @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime regisDate;
    }

    @Data @AllArgsConstructor
    public  static class PostReplydateTimeResponse
    {
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime regisDate;
    }

}

