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
        private String address;
        private String code;

        public MailPostResponse(String status ,String address,String code)
        {
            this.status = status;
            this.address = address;
            this.code = code;
            System.out.println("complete post");
        }
    }


}
