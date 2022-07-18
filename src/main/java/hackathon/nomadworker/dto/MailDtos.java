package hackathon.nomadworker.dto;

import lombok.Data;

public class MailDtos
{
    @Data
    public static class MailPostRequest
    {
        private String address;
    }

    @Data
    public static class MailPostResponse
    {
        private String status;
        private String msg;

        private String code;

        public MailPostResponse(String status ,String msg,String code)
        {
            this.msg = msg;
            this.status = status;
            this.code = code;

        }
    }


}
