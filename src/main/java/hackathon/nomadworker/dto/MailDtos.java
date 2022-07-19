package hackathon.nomadworker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

public class MailDtos
{
    @Data
    @AllArgsConstructor
    public static class MailResult<T>
    {
        private int count;
        private T data;
    }
    @Data
    public static class MailPostRequest
    {
        private String address;
    }

    @Data
    @AllArgsConstructor
    public static class MailPostResponse<T>
    {

        private String message;
        private int status;
        private T data;

    }


}
