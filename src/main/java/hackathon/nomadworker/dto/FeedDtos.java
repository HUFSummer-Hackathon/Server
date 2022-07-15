package hackathon.nomadworker.dto;

import hackathon.nomadworker.domain.Feed;
import lombok.AllArgsConstructor;
import lombok.Data;

public class FeedDtos {

    @Data
    @AllArgsConstructor
    public static class Result<T> {
        private int count;
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

}

