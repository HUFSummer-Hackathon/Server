package hackathon.nomadworker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

public class authDtos {

    //==Response DTO==//
    @Data
    @AllArgsConstructor
    public static class TokenResponse<T> {

        private String code;
        private String msg;
        private T data;

    }

    //==Response DTO==//
    @Data
    @AllArgsConstructor
    public static class TokenResponseNoData<T> {

        private String code;
        private String msg;

    }

    //==Response DTO==//
    @Data
    @AllArgsConstructor
    public static class TokenDataResponse {
        private String token;
        private String subject;
        private String issued_time;
        private String expired_time;
    }
}
